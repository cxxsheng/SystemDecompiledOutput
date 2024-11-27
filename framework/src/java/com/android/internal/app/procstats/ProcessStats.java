package com.android.internal.app.procstats;

/* loaded from: classes4.dex */
public final class ProcessStats implements android.os.Parcelable {
    public static final int ADD_PSS_EXTERNAL = 3;
    public static final int ADD_PSS_EXTERNAL_SLOW = 4;
    public static final int ADD_PSS_INTERNAL_ALL_MEM = 1;
    public static final int ADD_PSS_INTERNAL_ALL_POLL = 2;
    public static final int ADD_PSS_INTERNAL_SINGLE = 0;
    public static final int ADJ_COUNT = 8;
    public static final int ADJ_MEM_FACTOR_COUNT = 4;
    public static final int ADJ_MEM_FACTOR_CRITICAL = 3;
    public static final int ADJ_MEM_FACTOR_LOW = 2;
    public static final int ADJ_MEM_FACTOR_MODERATE = 1;
    public static final int ADJ_MEM_FACTOR_NORMAL = 0;
    public static final int ADJ_NOTHING = -1;
    public static final int ADJ_SCREEN_MOD = 4;
    public static final int ADJ_SCREEN_OFF = 0;
    public static final int ADJ_SCREEN_ON = 4;
    static final boolean DEBUG = false;
    static final boolean DEBUG_PARCEL = false;
    public static final int FLAG_COMPLETE = 1;
    public static final int FLAG_SHUTDOWN = 2;
    public static final int FLAG_SYSPROPS = 4;
    private static final long INVERSE_PROC_STATE_WARNING_MIN_INTERVAL_MS = 10000;
    private static final int MAGIC = 1347638356;
    private static final int PARCEL_VERSION = 41;
    public static final int PSS_AVERAGE = 2;
    public static final int PSS_COUNT = 10;
    public static final int PSS_MAXIMUM = 3;
    public static final int PSS_MINIMUM = 1;
    public static final int PSS_RSS_AVERAGE = 8;
    public static final int PSS_RSS_MAXIMUM = 9;
    public static final int PSS_RSS_MINIMUM = 7;
    public static final int PSS_SAMPLE_COUNT = 0;
    public static final int PSS_USS_AVERAGE = 5;
    public static final int PSS_USS_MAXIMUM = 6;
    public static final int PSS_USS_MINIMUM = 4;
    public static final int REPORT_ALL = 31;
    public static final int REPORT_PKG_ASC_STATS = 8;
    public static final int REPORT_PKG_PROC_STATS = 2;
    public static final int REPORT_PKG_STATS = 14;
    public static final int REPORT_PKG_SVC_STATS = 4;
    public static final int REPORT_PROC_STATS = 1;
    public static final int REPORT_UID_STATS = 16;
    public static final java.lang.String SERVICE_NAME = "procstats";
    public static final int STATE_BACKUP = 7;
    public static final int STATE_BOUND_FGS = 4;
    public static final int STATE_BOUND_TOP = 2;
    public static final int STATE_CACHED = 14;
    public static final int STATE_COUNT = 16;
    public static final int STATE_FGS = 3;
    public static final int STATE_FROZEN = 15;
    public static final int STATE_HEAVY_WEIGHT = 11;
    public static final int STATE_HOME = 12;
    public static final int STATE_IMPORTANT_BACKGROUND = 6;
    public static final int STATE_IMPORTANT_FOREGROUND = 5;
    public static final int STATE_LAST_ACTIVITY = 13;
    public static final int STATE_NOTHING = -1;
    public static final int STATE_PERSISTENT = 0;
    public static final int STATE_RECEIVER = 10;
    public static final int STATE_SERVICE = 8;
    public static final int STATE_SERVICE_RESTARTING = 9;
    public static final int STATE_TOP = 1;
    public static final int SYS_MEM_USAGE_CACHED_AVERAGE = 2;
    public static final int SYS_MEM_USAGE_CACHED_MAXIMUM = 3;
    public static final int SYS_MEM_USAGE_CACHED_MINIMUM = 1;
    public static final int SYS_MEM_USAGE_COUNT = 16;
    public static final int SYS_MEM_USAGE_FREE_AVERAGE = 5;
    public static final int SYS_MEM_USAGE_FREE_MAXIMUM = 6;
    public static final int SYS_MEM_USAGE_FREE_MINIMUM = 4;
    public static final int SYS_MEM_USAGE_KERNEL_AVERAGE = 11;
    public static final int SYS_MEM_USAGE_KERNEL_MAXIMUM = 12;
    public static final int SYS_MEM_USAGE_KERNEL_MINIMUM = 10;
    public static final int SYS_MEM_USAGE_NATIVE_AVERAGE = 14;
    public static final int SYS_MEM_USAGE_NATIVE_MAXIMUM = 15;
    public static final int SYS_MEM_USAGE_NATIVE_MINIMUM = 13;
    public static final int SYS_MEM_USAGE_SAMPLE_COUNT = 0;
    public static final int SYS_MEM_USAGE_ZRAM_AVERAGE = 8;
    public static final int SYS_MEM_USAGE_ZRAM_MAXIMUM = 9;
    public static final int SYS_MEM_USAGE_ZRAM_MINIMUM = 7;
    public static final java.lang.String TAG = "ProcessStats";
    android.util.ArrayMap<java.lang.String, java.lang.Integer> mCommonStringToIndex;
    public long mExternalPssCount;
    public long mExternalPssTime;
    public long mExternalSlowPssCount;
    public long mExternalSlowPssTime;
    public int mFlags;
    boolean mHasSwappedOutPss;
    java.util.ArrayList<java.lang.String> mIndexToCommonString;
    public long mInternalAllMemPssCount;
    public long mInternalAllMemPssTime;
    public long mInternalAllPollPssCount;
    public long mInternalAllPollPssTime;
    public long mInternalSinglePssCount;
    public long mInternalSinglePssTime;
    public int mMemFactor;
    public final long[] mMemFactorDurations;
    private long mNextInverseProcStateWarningUptime;
    public int mNumAggregated;
    public final com.android.internal.app.ProcessMap<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> mPackages;
    private final java.util.ArrayList<java.lang.String> mPageTypeLabels;
    private final java.util.ArrayList<java.lang.Integer> mPageTypeNodes;
    private final java.util.ArrayList<int[]> mPageTypeSizes;
    private final java.util.ArrayList<java.lang.String> mPageTypeZones;
    public final com.android.internal.app.ProcessMap<com.android.internal.app.procstats.ProcessState> mProcesses;
    public java.lang.String mReadError;
    boolean mRunning;
    java.lang.String mRuntime;
    private int mSkippedInverseProcStateWarningCount;
    public long mStartTime;
    public final com.android.internal.app.procstats.SysMemUsageTable mSysMemUsage;
    public final long[] mSysMemUsageArgs;
    public final com.android.internal.app.procstats.SparseMappingTable mTableData;
    public long mTimePeriodEndRealtime;
    public long mTimePeriodEndUptime;
    public long mTimePeriodStartClock;
    public java.lang.String mTimePeriodStartClockStr;
    public long mTimePeriodStartRealtime;
    public long mTimePeriodStartUptime;
    public final java.util.ArrayList<com.android.internal.app.procstats.AssociationState.SourceState> mTrackingAssociations;
    public final android.util.SparseArray<com.android.internal.app.procstats.UidState> mUidStates;
    public static long COMMIT_PERIOD = 10800000;
    public static long COMMIT_UPTIME_PERIOD = 3600000;
    public static final int[] ALL_MEM_ADJ = {0, 1, 2, 3};
    public static final int[] ALL_SCREEN_ADJ = {0, 4};
    public static final int[] NON_CACHED_PROC_STATES = {0, 1, 3, 5, 6, 7, 8, 9, 10, 11, 2, 4};
    public static final int[] BACKGROUND_PROC_STATES = {5, 6, 7, 11, 8, 9, 10};
    public static final int[] ALL_PROC_STATES = {0, 1, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 2, 4, 15};
    public static final int[] OPTIONS = {1, 2, 4, 8, 14, 16, 31};
    public static final java.lang.String[] OPTIONS_STR = {"proc", "pkg-proc", "pkg-svc", "pkg-asc", "pkg-all", "uid", "all"};
    private static final java.util.regex.Pattern sPageTypeRegex = java.util.regex.Pattern.compile("^Node\\s+(\\d+),.* zone\\s+(\\w+),.* type\\s+(\\w+)\\s+([\\s\\d]+?)\\s*$");
    public static final android.os.Parcelable.Creator<com.android.internal.app.procstats.ProcessStats> CREATOR = new android.os.Parcelable.Creator<com.android.internal.app.procstats.ProcessStats>() { // from class: com.android.internal.app.procstats.ProcessStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.app.procstats.ProcessStats createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.app.procstats.ProcessStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.app.procstats.ProcessStats[] newArray(int i) {
            return new com.android.internal.app.procstats.ProcessStats[i];
        }
    };
    static final int[] BAD_TABLE = new int[0];
    static final java.util.Comparator<com.android.internal.app.procstats.ProcessStats.AssociationDumpContainer> ASSOCIATION_COMPARATOR = new java.util.Comparator() { // from class: com.android.internal.app.procstats.ProcessStats$$ExternalSyntheticLambda1
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            return com.android.internal.app.procstats.ProcessStats.lambda$static$0((com.android.internal.app.procstats.ProcessStats.AssociationDumpContainer) obj, (com.android.internal.app.procstats.ProcessStats.AssociationDumpContainer) obj2);
        }
    };

    public ProcessStats(boolean z) {
        this.mPackages = new com.android.internal.app.ProcessMap<>();
        this.mProcesses = new com.android.internal.app.ProcessMap<>();
        this.mUidStates = new android.util.SparseArray<>();
        this.mTrackingAssociations = new java.util.ArrayList<>();
        this.mMemFactorDurations = new long[8];
        this.mMemFactor = -1;
        this.mNumAggregated = 1;
        this.mTableData = new com.android.internal.app.procstats.SparseMappingTable();
        this.mSysMemUsageArgs = new long[16];
        this.mSysMemUsage = new com.android.internal.app.procstats.SysMemUsageTable(this.mTableData);
        this.mPageTypeNodes = new java.util.ArrayList<>();
        this.mPageTypeZones = new java.util.ArrayList<>();
        this.mPageTypeLabels = new java.util.ArrayList<>();
        this.mPageTypeSizes = new java.util.ArrayList<>();
        this.mRunning = z;
        reset();
        if (z) {
            android.os.Debug.MemoryInfo memoryInfo = new android.os.Debug.MemoryInfo();
            android.os.Debug.getMemoryInfo(android.os.Process.myPid(), memoryInfo);
            this.mHasSwappedOutPss = memoryInfo.hasSwappedOutPss();
        }
    }

    public ProcessStats(android.os.Parcel parcel) {
        this.mPackages = new com.android.internal.app.ProcessMap<>();
        this.mProcesses = new com.android.internal.app.ProcessMap<>();
        this.mUidStates = new android.util.SparseArray<>();
        this.mTrackingAssociations = new java.util.ArrayList<>();
        this.mMemFactorDurations = new long[8];
        this.mMemFactor = -1;
        this.mNumAggregated = 1;
        this.mTableData = new com.android.internal.app.procstats.SparseMappingTable();
        this.mSysMemUsageArgs = new long[16];
        this.mSysMemUsage = new com.android.internal.app.procstats.SysMemUsageTable(this.mTableData);
        this.mPageTypeNodes = new java.util.ArrayList<>();
        this.mPageTypeZones = new java.util.ArrayList<>();
        this.mPageTypeLabels = new java.util.ArrayList<>();
        this.mPageTypeSizes = new java.util.ArrayList<>();
        reset();
        readFromParcel(parcel);
    }

    public ProcessStats() {
        this(false);
    }

    public void add(com.android.internal.app.procstats.ProcessStats processStats) {
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<com.android.internal.app.procstats.ProcessState>> arrayMap;
        android.util.SparseArray<com.android.internal.app.procstats.ProcessState> sparseArray;
        com.android.internal.app.procstats.ProcessStats.PackageState packageState;
        int i;
        android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> sparseArray2;
        int i2;
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> arrayMap2;
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> map = processStats.mPackages.getMap();
        for (int i3 = 0; i3 < map.size(); i3++) {
            java.lang.String keyAt = map.keyAt(i3);
            android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> valueAt = map.valueAt(i3);
            for (int i4 = 0; i4 < valueAt.size(); i4++) {
                int keyAt2 = valueAt.keyAt(i4);
                android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> valueAt2 = valueAt.valueAt(i4);
                int i5 = 0;
                while (i5 < valueAt2.size()) {
                    long keyAt3 = valueAt2.keyAt(i5);
                    com.android.internal.app.procstats.ProcessStats.PackageState valueAt3 = valueAt2.valueAt(i5);
                    int size = valueAt3.mProcesses.size();
                    int size2 = valueAt3.mServices.size();
                    int size3 = valueAt3.mAssociations.size();
                    android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> longSparseArray = valueAt2;
                    int i6 = 0;
                    while (i6 < size) {
                        int i7 = size2;
                        com.android.internal.app.procstats.ProcessState valueAt4 = valueAt3.mProcesses.valueAt(i6);
                        int i8 = size;
                        if (valueAt4.getCommonProcess() == valueAt4) {
                            packageState = valueAt3;
                            i = i5;
                            sparseArray2 = valueAt;
                            i2 = i7;
                            arrayMap2 = map;
                        } else {
                            arrayMap2 = map;
                            sparseArray2 = valueAt;
                            i2 = i7;
                            packageState = valueAt3;
                            long j = keyAt3;
                            i = i5;
                            com.android.internal.app.procstats.ProcessState processStateLocked = getProcessStateLocked(keyAt, keyAt2, keyAt3, valueAt4.getName());
                            if (processStateLocked.getCommonProcess() != processStateLocked) {
                                keyAt3 = j;
                            } else {
                                processStateLocked.setMultiPackage(true);
                                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                                keyAt3 = j;
                                com.android.internal.app.procstats.ProcessStats.PackageState packageStateLocked = getPackageStateLocked(keyAt, keyAt2, keyAt3);
                                processStateLocked = processStateLocked.clone(uptimeMillis);
                                packageStateLocked.mProcesses.put(processStateLocked.getName(), processStateLocked);
                            }
                            processStateLocked.add(valueAt4);
                        }
                        i6++;
                        valueAt3 = packageState;
                        size2 = i2;
                        map = arrayMap2;
                        size = i8;
                        valueAt = sparseArray2;
                        i5 = i;
                    }
                    com.android.internal.app.procstats.ProcessStats.PackageState packageState2 = valueAt3;
                    int i9 = i5;
                    android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> arrayMap3 = map;
                    android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> sparseArray3 = valueAt;
                    int i10 = 0;
                    for (int i11 = size2; i10 < i11; i11 = i11) {
                        com.android.internal.app.procstats.ServiceState valueAt5 = packageState2.mServices.valueAt(i10);
                        getServiceStateLocked(keyAt, keyAt2, keyAt3, valueAt5.getProcessName(), valueAt5.getName()).add(valueAt5);
                        i10++;
                    }
                    long j2 = keyAt3;
                    for (int i12 = 0; i12 < size3; i12++) {
                        com.android.internal.app.procstats.AssociationState valueAt6 = packageState2.mAssociations.valueAt(i12);
                        getAssociationStateLocked(keyAt, keyAt2, j2, valueAt6.getProcessName(), valueAt6.getName()).add(valueAt6);
                    }
                    i5 = i9 + 1;
                    valueAt2 = longSparseArray;
                    map = arrayMap3;
                    valueAt = sparseArray3;
                }
            }
        }
        android.util.SparseArray<com.android.internal.app.procstats.UidState> sparseArray4 = processStats.mUidStates;
        int size4 = sparseArray4.size();
        for (int i13 = 0; i13 < size4; i13++) {
            int keyAt4 = sparseArray4.keyAt(i13);
            com.android.internal.app.procstats.UidState uidState = this.mUidStates.get(keyAt4);
            if (uidState == null) {
                this.mUidStates.put(keyAt4, sparseArray4.valueAt(i13).m6774clone());
            } else {
                uidState.add(sparseArray4.valueAt(i13));
            }
        }
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<com.android.internal.app.procstats.ProcessState>> map2 = processStats.mProcesses.getMap();
        for (int i14 = 0; i14 < map2.size(); i14++) {
            android.util.SparseArray<com.android.internal.app.procstats.ProcessState> valueAt7 = map2.valueAt(i14);
            int i15 = 0;
            while (i15 < valueAt7.size()) {
                int keyAt5 = valueAt7.keyAt(i15);
                com.android.internal.app.procstats.ProcessState valueAt8 = valueAt7.valueAt(i15);
                java.lang.String name = valueAt8.getName();
                java.lang.String str = valueAt8.getPackage();
                long version = valueAt8.getVersion();
                com.android.internal.app.procstats.ProcessState processState = this.mProcesses.get(name, keyAt5);
                if (processState != null) {
                    arrayMap = map2;
                    sparseArray = valueAt7;
                } else {
                    arrayMap = map2;
                    sparseArray = valueAt7;
                    com.android.internal.app.procstats.ProcessState processState2 = new com.android.internal.app.procstats.ProcessState(this, str, keyAt5, version, name);
                    this.mProcesses.put(name, keyAt5, processState2);
                    com.android.internal.app.procstats.ProcessStats.PackageState packageStateLocked2 = getPackageStateLocked(str, keyAt5, version);
                    if (!packageStateLocked2.mProcesses.containsKey(name)) {
                        packageStateLocked2.mProcesses.put(name, processState2);
                    }
                    processState = processState2;
                }
                processState.add(valueAt8);
                com.android.internal.app.procstats.UidState uidState2 = this.mUidStates.get(keyAt5);
                if (uidState2 == null) {
                    uidState2 = new com.android.internal.app.procstats.UidState(this, keyAt5);
                    this.mUidStates.put(keyAt5, uidState2);
                }
                uidState2.addProcess(processState);
                i15++;
                map2 = arrayMap;
                valueAt7 = sparseArray;
            }
        }
        int size5 = this.mUidStates.size();
        for (int i16 = 0; i16 < size5; i16++) {
            this.mUidStates.valueAt(i16).updateCombinedState(-1L);
        }
        for (int i17 = 0; i17 < 8; i17++) {
            long[] jArr = this.mMemFactorDurations;
            jArr[i17] = jArr[i17] + processStats.mMemFactorDurations[i17];
        }
        this.mSysMemUsage.mergeStats(processStats.mSysMemUsage);
        this.mNumAggregated += processStats.mNumAggregated;
        if (processStats.mTimePeriodStartClock < this.mTimePeriodStartClock) {
            this.mTimePeriodStartClock = processStats.mTimePeriodStartClock;
            this.mTimePeriodStartClockStr = processStats.mTimePeriodStartClockStr;
        }
        this.mTimePeriodEndRealtime += processStats.mTimePeriodEndRealtime - processStats.mTimePeriodStartRealtime;
        this.mTimePeriodEndUptime += processStats.mTimePeriodEndUptime - processStats.mTimePeriodStartUptime;
        this.mInternalSinglePssCount += processStats.mInternalSinglePssCount;
        this.mInternalSinglePssTime += processStats.mInternalSinglePssTime;
        this.mInternalAllMemPssCount += processStats.mInternalAllMemPssCount;
        this.mInternalAllMemPssTime += processStats.mInternalAllMemPssTime;
        this.mInternalAllPollPssCount += processStats.mInternalAllPollPssCount;
        this.mInternalAllPollPssTime += processStats.mInternalAllPollPssTime;
        this.mExternalPssCount += processStats.mExternalPssCount;
        this.mExternalPssTime += processStats.mExternalPssTime;
        this.mExternalSlowPssCount += processStats.mExternalSlowPssCount;
        this.mExternalSlowPssTime += processStats.mExternalSlowPssTime;
        this.mHasSwappedOutPss |= processStats.mHasSwappedOutPss;
    }

    public void addSysMemUsage(long j, long j2, long j3, long j4, long j5) {
        if (this.mMemFactor != -1) {
            int i = this.mMemFactor * 16;
            this.mSysMemUsageArgs[0] = 1;
            int i2 = 0;
            while (i2 < 3) {
                int i3 = i2 + 1;
                this.mSysMemUsageArgs[i3] = j;
                this.mSysMemUsageArgs[i2 + 4] = j2;
                this.mSysMemUsageArgs[i2 + 7] = j3;
                this.mSysMemUsageArgs[i2 + 10] = j4;
                this.mSysMemUsageArgs[i2 + 13] = j5;
                i2 = i3;
            }
            this.mSysMemUsage.mergeStats(i, this.mSysMemUsageArgs, 0);
        }
    }

    public void computeTotalMemoryUse(com.android.internal.app.procstats.ProcessStats.TotalMemoryUseCollection totalMemoryUseCollection, long j) {
        long[] jArr;
        int i;
        totalMemoryUseCollection.totalTime = 0L;
        for (int i2 = 0; i2 < 16; i2++) {
            totalMemoryUseCollection.processStateWeight[i2] = 0.0d;
            totalMemoryUseCollection.processStatePss[i2] = 0;
            totalMemoryUseCollection.processStateTime[i2] = 0;
            totalMemoryUseCollection.processStateSamples[i2] = 0;
        }
        for (int i3 = 0; i3 < 16; i3++) {
            totalMemoryUseCollection.sysMemUsage[i3] = 0;
        }
        totalMemoryUseCollection.sysMemCachedWeight = 0.0d;
        totalMemoryUseCollection.sysMemFreeWeight = 0.0d;
        totalMemoryUseCollection.sysMemZRamWeight = 0.0d;
        totalMemoryUseCollection.sysMemKernelWeight = 0.0d;
        totalMemoryUseCollection.sysMemNativeWeight = 0.0d;
        totalMemoryUseCollection.sysMemSamples = 0;
        long[] totalMemUsage = this.mSysMemUsage.getTotalMemUsage();
        for (int i4 = 0; i4 < totalMemoryUseCollection.screenStates.length; i4++) {
            for (int i5 = 0; i5 < totalMemoryUseCollection.memStates.length; i5++) {
                int i6 = totalMemoryUseCollection.screenStates[i4] + totalMemoryUseCollection.memStates[i5];
                int i7 = i6 * 16;
                long j2 = this.mMemFactorDurations[i6];
                if (this.mMemFactor == i6) {
                    j2 += j - this.mStartTime;
                }
                totalMemoryUseCollection.totalTime += j2;
                int key = this.mSysMemUsage.getKey((byte) i7);
                if (key != -1) {
                    jArr = this.mSysMemUsage.getArrayForKey(key);
                    i = com.android.internal.app.procstats.SparseMappingTable.getIndexFromKey(key);
                    if (jArr[i + 0] >= 3) {
                        com.android.internal.app.procstats.SysMemUsageTable.mergeSysMemUsage(totalMemoryUseCollection.sysMemUsage, 0, totalMemUsage, 0);
                        double d = j2;
                        totalMemoryUseCollection.sysMemCachedWeight += jArr[i + 2] * d;
                        totalMemoryUseCollection.sysMemFreeWeight += jArr[i + 5] * d;
                        totalMemoryUseCollection.sysMemZRamWeight += jArr[i + 8] * d;
                        totalMemoryUseCollection.sysMemKernelWeight += jArr[i + 11] * d;
                        totalMemoryUseCollection.sysMemNativeWeight += jArr[i + 14] * d;
                        totalMemoryUseCollection.sysMemSamples = (int) (totalMemoryUseCollection.sysMemSamples + jArr[i + 0]);
                    }
                }
                jArr = totalMemUsage;
                i = 0;
                double d2 = j2;
                totalMemoryUseCollection.sysMemCachedWeight += jArr[i + 2] * d2;
                totalMemoryUseCollection.sysMemFreeWeight += jArr[i + 5] * d2;
                totalMemoryUseCollection.sysMemZRamWeight += jArr[i + 8] * d2;
                totalMemoryUseCollection.sysMemKernelWeight += jArr[i + 11] * d2;
                totalMemoryUseCollection.sysMemNativeWeight += jArr[i + 14] * d2;
                totalMemoryUseCollection.sysMemSamples = (int) (totalMemoryUseCollection.sysMemSamples + jArr[i + 0]);
            }
        }
        totalMemoryUseCollection.hasSwappedOutPss = this.mHasSwappedOutPss;
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<com.android.internal.app.procstats.ProcessState>> map = this.mProcesses.getMap();
        for (int i8 = 0; i8 < map.size(); i8++) {
            android.util.SparseArray<com.android.internal.app.procstats.ProcessState> valueAt = map.valueAt(i8);
            for (int i9 = 0; i9 < valueAt.size(); i9++) {
                valueAt.valueAt(i9).aggregatePss(totalMemoryUseCollection, j);
            }
        }
    }

    public void reset() {
        resetCommon();
        this.mPackages.getMap().clear();
        this.mProcesses.getMap().clear();
        this.mUidStates.clear();
        this.mMemFactor = -1;
        this.mStartTime = 0L;
    }

    public void resetSafely() {
        resetCommon();
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<com.android.internal.app.procstats.ProcessState>> map = this.mProcesses.getMap();
        for (int size = map.size() - 1; size >= 0; size--) {
            android.util.SparseArray<com.android.internal.app.procstats.ProcessState> valueAt = map.valueAt(size);
            for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                valueAt.valueAt(size2).tmpNumInUse = 0;
            }
        }
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> map2 = this.mPackages.getMap();
        for (int size3 = map2.size() - 1; size3 >= 0; size3--) {
            android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> valueAt2 = map2.valueAt(size3);
            for (int size4 = valueAt2.size() - 1; size4 >= 0; size4--) {
                android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> valueAt3 = valueAt2.valueAt(size4);
                for (int size5 = valueAt3.size() - 1; size5 >= 0; size5--) {
                    com.android.internal.app.procstats.ProcessStats.PackageState valueAt4 = valueAt3.valueAt(size5);
                    for (int size6 = valueAt4.mProcesses.size() - 1; size6 >= 0; size6--) {
                        com.android.internal.app.procstats.ProcessState valueAt5 = valueAt4.mProcesses.valueAt(size6);
                        if (valueAt5.isInUse()) {
                            valueAt5.resetSafely(uptimeMillis);
                            valueAt5.getCommonProcess().tmpNumInUse++;
                            valueAt5.getCommonProcess().tmpFoundSubProc = valueAt5;
                        } else {
                            valueAt4.mProcesses.valueAt(size6).makeDead();
                            valueAt4.mProcesses.removeAt(size6);
                        }
                    }
                    for (int size7 = valueAt4.mServices.size() - 1; size7 >= 0; size7--) {
                        com.android.internal.app.procstats.ServiceState valueAt6 = valueAt4.mServices.valueAt(size7);
                        if (valueAt6.isInUse()) {
                            valueAt6.resetSafely(uptimeMillis);
                        } else {
                            valueAt4.mServices.removeAt(size7);
                        }
                    }
                    for (int size8 = valueAt4.mAssociations.size() - 1; size8 >= 0; size8--) {
                        com.android.internal.app.procstats.AssociationState valueAt7 = valueAt4.mAssociations.valueAt(size8);
                        if (valueAt7.isInUse()) {
                            valueAt7.resetSafely(uptimeMillis);
                        } else {
                            valueAt4.mAssociations.removeAt(size8);
                        }
                    }
                    if (valueAt4.mProcesses.size() <= 0 && valueAt4.mServices.size() <= 0 && valueAt4.mAssociations.size() <= 0) {
                        valueAt3.removeAt(size5);
                    }
                }
                if (valueAt3.size() <= 0) {
                    valueAt2.removeAt(size4);
                }
            }
            if (valueAt2.size() <= 0) {
                map2.removeAt(size3);
            }
        }
        for (int size9 = map.size() - 1; size9 >= 0; size9--) {
            android.util.SparseArray<com.android.internal.app.procstats.ProcessState> valueAt8 = map.valueAt(size9);
            for (int size10 = valueAt8.size() - 1; size10 >= 0; size10--) {
                com.android.internal.app.procstats.ProcessState valueAt9 = valueAt8.valueAt(size10);
                if (valueAt9.isInUse() || valueAt9.tmpNumInUse > 0) {
                    if (!valueAt9.isActive() && valueAt9.isMultiPackage() && valueAt9.tmpNumInUse == 1) {
                        com.android.internal.app.procstats.ProcessState processState = valueAt9.tmpFoundSubProc;
                        processState.makeStandalone();
                        valueAt8.setValueAt(size10, processState);
                    } else {
                        valueAt9.resetSafely(uptimeMillis);
                    }
                } else {
                    valueAt9.makeDead();
                    valueAt8.removeAt(size10);
                }
            }
            if (valueAt8.size() <= 0) {
                map.removeAt(size9);
            }
        }
        for (int size11 = this.mUidStates.size() - 1; size11 >= 0; size11--) {
            if (this.mUidStates.valueAt(size11).isInUse()) {
                this.mUidStates.valueAt(size11).resetSafely(uptimeMillis);
            } else {
                this.mUidStates.removeAt(size11);
            }
        }
        this.mStartTime = uptimeMillis;
    }

    private void resetCommon() {
        this.mNumAggregated = 1;
        this.mTimePeriodStartClock = java.lang.System.currentTimeMillis();
        buildTimePeriodStartClockStr();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        this.mTimePeriodEndRealtime = elapsedRealtime;
        this.mTimePeriodStartRealtime = elapsedRealtime;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        this.mTimePeriodEndUptime = uptimeMillis;
        this.mTimePeriodStartUptime = uptimeMillis;
        this.mInternalSinglePssCount = 0L;
        this.mInternalSinglePssTime = 0L;
        this.mInternalAllMemPssCount = 0L;
        this.mInternalAllMemPssTime = 0L;
        this.mInternalAllPollPssCount = 0L;
        this.mInternalAllPollPssTime = 0L;
        this.mExternalPssCount = 0L;
        this.mExternalPssTime = 0L;
        this.mExternalSlowPssCount = 0L;
        this.mExternalSlowPssTime = 0L;
        this.mTableData.reset();
        java.util.Arrays.fill(this.mMemFactorDurations, 0L);
        this.mSysMemUsage.resetTable();
        this.mStartTime = 0L;
        this.mReadError = null;
        this.mFlags = 0;
        evaluateSystemProperties(true);
        updateFragmentation();
    }

    public boolean evaluateSystemProperties(boolean z) {
        java.lang.String str = android.os.SystemProperties.get("persist.sys.dalvik.vm.lib.2", dalvik.system.VMRuntime.getRuntime().vmLibrary());
        if (java.util.Objects.equals(str, this.mRuntime)) {
            return false;
        }
        if (z) {
            this.mRuntime = str;
        }
        return true;
    }

    private void buildTimePeriodStartClockStr() {
        this.mTimePeriodStartClockStr = android.text.format.DateFormat.format("yyyy-MM-dd-HH-mm-ss", this.mTimePeriodStartClock).toString();
    }

    public void updateFragmentation() {
        java.io.BufferedReader bufferedReader;
        java.lang.Throwable th;
        java.lang.Integer valueOf;
        java.io.BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new java.io.BufferedReader(new java.io.FileReader("/proc/pagetypeinfo"));
                try {
                    java.util.regex.Matcher matcher = sPageTypeRegex.matcher("");
                    this.mPageTypeNodes.clear();
                    this.mPageTypeZones.clear();
                    this.mPageTypeLabels.clear();
                    this.mPageTypeSizes.clear();
                    while (true) {
                        java.lang.String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            try {
                                bufferedReader.close();
                                return;
                            } catch (java.io.IOException e) {
                                return;
                            }
                        }
                        matcher.reset(readLine);
                        if (matcher.matches() && (valueOf = java.lang.Integer.valueOf(matcher.group(1), 10)) != null) {
                            this.mPageTypeNodes.add(valueOf);
                            this.mPageTypeZones.add(matcher.group(2));
                            this.mPageTypeLabels.add(matcher.group(3));
                            this.mPageTypeSizes.add(splitAndParseNumbers(matcher.group(4)));
                        }
                    }
                } catch (java.io.IOException e2) {
                    bufferedReader2 = bufferedReader;
                    this.mPageTypeNodes.clear();
                    this.mPageTypeZones.clear();
                    this.mPageTypeLabels.clear();
                    this.mPageTypeSizes.clear();
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (java.io.IOException e3) {
                        }
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (java.io.IOException e4) {
                        }
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th3) {
                bufferedReader = bufferedReader2;
                th = th3;
            }
        } catch (java.io.IOException e5) {
        }
    }

    private static int[] splitAndParseNumbers(java.lang.String str) {
        int length = str.length();
        int i = 0;
        boolean z = false;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt >= '0' && charAt <= '9') {
                if (!z) {
                    i++;
                    z = true;
                }
            } else {
                z = false;
            }
        }
        int[] iArr = new int[i];
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            char charAt2 = str.charAt(i5);
            if (charAt2 >= '0' && charAt2 <= '9') {
                if (z) {
                    i3 = (i3 * 10) + (charAt2 - '0');
                } else {
                    i3 = charAt2 - '0';
                    z = true;
                }
            } else if (z) {
                iArr[i4] = i3;
                i4++;
                z = false;
            }
        }
        if (i > 0) {
            iArr[i - 1] = i3;
        }
        return iArr;
    }

    private void writeCompactedLongArray(android.os.Parcel parcel, long[] jArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            long j = jArr[i2];
            if (j < 0) {
                android.util.Slog.w(TAG, "Time val negative: " + j);
                j = 0;
            }
            if (j <= 2147483647L) {
                parcel.writeInt((int) j);
            } else {
                parcel.writeInt(~((int) (2147483647L & (j >> 32))));
                parcel.writeInt((int) (j & 4294967295L));
            }
        }
    }

    private void readCompactedLongArray(android.os.Parcel parcel, int i, long[] jArr, int i2) {
        if (i <= 10) {
            parcel.readLongArray(jArr);
            return;
        }
        int length = jArr.length;
        if (i2 > length) {
            throw new java.lang.RuntimeException("bad array lengths: got " + i2 + " array is " + length);
        }
        int i3 = 0;
        while (i3 < i2) {
            int readInt = parcel.readInt();
            if (readInt >= 0) {
                jArr[i3] = readInt;
            } else {
                jArr[i3] = parcel.readInt() | ((~readInt) << 32);
            }
            i3++;
        }
        while (i3 < length) {
            jArr[i3] = 0;
            i3++;
        }
    }

    void writeCommonString(android.os.Parcel parcel, java.lang.String str) {
        java.lang.Integer num = this.mCommonStringToIndex.get(str);
        if (num != null) {
            parcel.writeInt(num.intValue());
            return;
        }
        java.lang.Integer valueOf = java.lang.Integer.valueOf(this.mCommonStringToIndex.size());
        this.mCommonStringToIndex.put(str, valueOf);
        parcel.writeInt(~valueOf.intValue());
        parcel.writeString(str);
    }

    java.lang.String readCommonString(android.os.Parcel parcel, int i) {
        if (i <= 9) {
            return parcel.readString();
        }
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            return this.mIndexToCommonString.get(readInt);
        }
        int i2 = ~readInt;
        java.lang.String readString = parcel.readString();
        while (this.mIndexToCommonString.size() <= i2) {
            this.mIndexToCommonString.add(null);
        }
        this.mIndexToCommonString.set(i2, readString);
        return readString;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeToParcel(parcel, android.os.SystemClock.uptimeMillis(), i);
    }

    public void writeToParcel(android.os.Parcel parcel, long j, int i) {
        parcel.writeInt(MAGIC);
        parcel.writeInt(41);
        parcel.writeInt(16);
        parcel.writeInt(8);
        parcel.writeInt(10);
        parcel.writeInt(16);
        parcel.writeInt(4096);
        this.mCommonStringToIndex = new android.util.ArrayMap<>(this.mProcesses.size());
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<com.android.internal.app.procstats.ProcessState>> map = this.mProcesses.getMap();
        int size = map.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.util.SparseArray<com.android.internal.app.procstats.ProcessState> valueAt = map.valueAt(i2);
            int size2 = valueAt.size();
            for (int i3 = 0; i3 < size2; i3++) {
                valueAt.valueAt(i3).commitStateTime(j);
            }
        }
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> map2 = this.mPackages.getMap();
        int size3 = map2.size();
        for (int i4 = 0; i4 < size3; i4++) {
            android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> valueAt2 = map2.valueAt(i4);
            int size4 = valueAt2.size();
            for (int i5 = 0; i5 < size4; i5++) {
                android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> valueAt3 = valueAt2.valueAt(i5);
                int size5 = valueAt3.size();
                int i6 = 0;
                while (i6 < size5) {
                    com.android.internal.app.procstats.ProcessStats.PackageState valueAt4 = valueAt3.valueAt(i6);
                    android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> sparseArray = valueAt2;
                    int size6 = valueAt4.mProcesses.size();
                    int i7 = size4;
                    int i8 = 0;
                    while (i8 < size6) {
                        int i9 = size6;
                        com.android.internal.app.procstats.ProcessState valueAt5 = valueAt4.mProcesses.valueAt(i8);
                        android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> longSparseArray = valueAt3;
                        if (valueAt5.getCommonProcess() != valueAt5) {
                            valueAt5.commitStateTime(j);
                        }
                        i8++;
                        size6 = i9;
                        valueAt3 = longSparseArray;
                    }
                    android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> longSparseArray2 = valueAt3;
                    int size7 = valueAt4.mServices.size();
                    for (int i10 = 0; i10 < size7; i10++) {
                        valueAt4.mServices.valueAt(i10).commitStateTime(j);
                    }
                    int size8 = valueAt4.mAssociations.size();
                    for (int i11 = 0; i11 < size8; i11++) {
                        valueAt4.mAssociations.valueAt(i11).commitStateTime(j);
                    }
                    i6++;
                    valueAt2 = sparseArray;
                    size4 = i7;
                    valueAt3 = longSparseArray2;
                }
            }
        }
        parcel.writeInt(this.mNumAggregated);
        parcel.writeLong(this.mTimePeriodStartClock);
        parcel.writeLong(this.mTimePeriodStartRealtime);
        parcel.writeLong(this.mTimePeriodEndRealtime);
        parcel.writeLong(this.mTimePeriodStartUptime);
        parcel.writeLong(this.mTimePeriodEndUptime);
        parcel.writeLong(this.mInternalSinglePssCount);
        parcel.writeLong(this.mInternalSinglePssTime);
        parcel.writeLong(this.mInternalAllMemPssCount);
        parcel.writeLong(this.mInternalAllMemPssTime);
        parcel.writeLong(this.mInternalAllPollPssCount);
        parcel.writeLong(this.mInternalAllPollPssTime);
        parcel.writeLong(this.mExternalPssCount);
        parcel.writeLong(this.mExternalPssTime);
        parcel.writeLong(this.mExternalSlowPssCount);
        parcel.writeLong(this.mExternalSlowPssTime);
        parcel.writeString(this.mRuntime);
        parcel.writeInt(this.mHasSwappedOutPss ? 1 : 0);
        parcel.writeInt(this.mFlags);
        this.mTableData.writeToParcel(parcel);
        if (this.mMemFactor != -1) {
            long[] jArr = this.mMemFactorDurations;
            int i12 = this.mMemFactor;
            jArr[i12] = jArr[i12] + (j - this.mStartTime);
            this.mStartTime = j;
        }
        writeCompactedLongArray(parcel, this.mMemFactorDurations, this.mMemFactorDurations.length);
        this.mSysMemUsage.writeToParcel(parcel);
        int size9 = this.mUidStates.size();
        parcel.writeInt(size9);
        for (int i13 = 0; i13 < size9; i13++) {
            parcel.writeInt(this.mUidStates.keyAt(i13));
            this.mUidStates.valueAt(i13).writeToParcel(parcel, j);
        }
        parcel.writeInt(size);
        for (int i14 = 0; i14 < size; i14++) {
            writeCommonString(parcel, map.keyAt(i14));
            android.util.SparseArray<com.android.internal.app.procstats.ProcessState> valueAt6 = map.valueAt(i14);
            int size10 = valueAt6.size();
            parcel.writeInt(size10);
            for (int i15 = 0; i15 < size10; i15++) {
                parcel.writeInt(valueAt6.keyAt(i15));
                com.android.internal.app.procstats.ProcessState valueAt7 = valueAt6.valueAt(i15);
                writeCommonString(parcel, valueAt7.getPackage());
                parcel.writeLong(valueAt7.getVersion());
                valueAt7.writeToParcel(parcel, j);
            }
        }
        parcel.writeInt(size3);
        for (int i16 = 0; i16 < size3; i16++) {
            writeCommonString(parcel, map2.keyAt(i16));
            android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> valueAt8 = map2.valueAt(i16);
            int size11 = valueAt8.size();
            parcel.writeInt(size11);
            for (int i17 = 0; i17 < size11; i17++) {
                parcel.writeInt(valueAt8.keyAt(i17));
                android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> valueAt9 = valueAt8.valueAt(i17);
                int size12 = valueAt9.size();
                parcel.writeInt(size12);
                int i18 = 0;
                while (i18 < size12) {
                    parcel.writeLong(valueAt9.keyAt(i18));
                    com.android.internal.app.procstats.ProcessStats.PackageState valueAt10 = valueAt9.valueAt(i18);
                    int size13 = valueAt10.mProcesses.size();
                    parcel.writeInt(size13);
                    int i19 = 0;
                    while (i19 < size13) {
                        android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> sparseArray2 = valueAt8;
                        writeCommonString(parcel, valueAt10.mProcesses.keyAt(i19));
                        com.android.internal.app.procstats.ProcessState valueAt11 = valueAt10.mProcesses.valueAt(i19);
                        int i20 = size11;
                        if (valueAt11.getCommonProcess() == valueAt11) {
                            parcel.writeInt(0);
                        } else {
                            parcel.writeInt(1);
                            valueAt11.writeToParcel(parcel, j);
                        }
                        i19++;
                        valueAt8 = sparseArray2;
                        size11 = i20;
                    }
                    android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> sparseArray3 = valueAt8;
                    int i21 = size11;
                    int size14 = valueAt10.mServices.size();
                    parcel.writeInt(size14);
                    for (int i22 = 0; i22 < size14; i22++) {
                        parcel.writeString(valueAt10.mServices.keyAt(i22));
                        com.android.internal.app.procstats.ServiceState valueAt12 = valueAt10.mServices.valueAt(i22);
                        writeCommonString(parcel, valueAt12.getProcessName());
                        valueAt12.writeToParcel(parcel, j);
                    }
                    int size15 = valueAt10.mAssociations.size();
                    parcel.writeInt(size15);
                    for (int i23 = 0; i23 < size15; i23++) {
                        writeCommonString(parcel, valueAt10.mAssociations.keyAt(i23));
                        com.android.internal.app.procstats.AssociationState valueAt13 = valueAt10.mAssociations.valueAt(i23);
                        writeCommonString(parcel, valueAt13.getProcessName());
                        valueAt13.writeToParcel(this, parcel, j);
                    }
                    i18++;
                    valueAt8 = sparseArray3;
                    size11 = i21;
                }
            }
        }
        int size16 = this.mPageTypeLabels.size();
        parcel.writeInt(size16);
        for (int i24 = 0; i24 < size16; i24++) {
            parcel.writeInt(this.mPageTypeNodes.get(i24).intValue());
            parcel.writeString(this.mPageTypeZones.get(i24));
            parcel.writeString(this.mPageTypeLabels.get(i24));
            parcel.writeIntArray(this.mPageTypeSizes.get(i24));
        }
        this.mCommonStringToIndex = null;
    }

    private boolean readCheckedInt(android.os.Parcel parcel, int i, java.lang.String str) {
        int readInt = parcel.readInt();
        if (readInt != i) {
            this.mReadError = "bad " + str + ": " + readInt;
            return false;
        }
        return true;
    }

    static byte[] readFully(java.io.InputStream inputStream, int[] iArr) throws java.io.IOException {
        int available = inputStream.available();
        byte[] bArr = new byte[available > 0 ? available + 1 : 16384];
        int i = 0;
        while (true) {
            int read = inputStream.read(bArr, i, bArr.length - i);
            if (read < 0) {
                iArr[0] = i;
                return bArr;
            }
            i += read;
            if (i >= bArr.length) {
                byte[] bArr2 = new byte[i + 16384];
                java.lang.System.arraycopy(bArr, 0, bArr2, 0, i);
                bArr = bArr2;
            }
        }
    }

    public void read(java.io.InputStream inputStream) {
        try {
            int[] iArr = new int[1];
            byte[] readFully = readFully(inputStream, iArr);
            android.os.Parcel obtain = android.os.Parcel.obtain();
            obtain.unmarshall(readFully, 0, iArr[0]);
            obtain.setDataPosition(0);
            inputStream.close();
            readFromParcel(obtain);
        } catch (java.io.IOException e) {
            this.mReadError = "caught exception: " + e;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int i;
        java.lang.String str;
        int i2;
        java.lang.String str2;
        java.lang.String str3;
        int i3;
        boolean z = false;
        boolean z2 = true;
        boolean z3 = this.mPackages.getMap().size() > 0 || this.mProcesses.getMap().size() > 0 || this.mUidStates.size() > 0;
        if (z3) {
            resetSafely();
        }
        if (!readCheckedInt(parcel, MAGIC, "magic number")) {
            return;
        }
        int readInt = parcel.readInt();
        if (readInt != 41) {
            this.mReadError = "bad version: " + readInt;
            return;
        }
        if (readCheckedInt(parcel, 16, "state count") && readCheckedInt(parcel, 8, "adj count") && readCheckedInt(parcel, 10, "pss count") && readCheckedInt(parcel, 16, "sys mem usage count") && readCheckedInt(parcel, 4096, "longs size")) {
            this.mIndexToCommonString = new java.util.ArrayList<>();
            this.mNumAggregated = parcel.readInt();
            this.mTimePeriodStartClock = parcel.readLong();
            buildTimePeriodStartClockStr();
            this.mTimePeriodStartRealtime = parcel.readLong();
            this.mTimePeriodEndRealtime = parcel.readLong();
            this.mTimePeriodStartUptime = parcel.readLong();
            this.mTimePeriodEndUptime = parcel.readLong();
            this.mInternalSinglePssCount = parcel.readLong();
            this.mInternalSinglePssTime = parcel.readLong();
            this.mInternalAllMemPssCount = parcel.readLong();
            this.mInternalAllMemPssTime = parcel.readLong();
            this.mInternalAllPollPssCount = parcel.readLong();
            this.mInternalAllPollPssTime = parcel.readLong();
            this.mExternalPssCount = parcel.readLong();
            this.mExternalPssTime = parcel.readLong();
            this.mExternalSlowPssCount = parcel.readLong();
            this.mExternalSlowPssTime = parcel.readLong();
            this.mRuntime = parcel.readString();
            this.mHasSwappedOutPss = parcel.readInt() != 0;
            this.mFlags = parcel.readInt();
            this.mTableData.readFromParcel(parcel);
            readCompactedLongArray(parcel, readInt, this.mMemFactorDurations, this.mMemFactorDurations.length);
            if (!this.mSysMemUsage.readFromParcel(parcel)) {
                return;
            }
            int readInt2 = parcel.readInt();
            for (int i4 = 0; i4 < readInt2; i4++) {
                int readInt3 = parcel.readInt();
                com.android.internal.app.procstats.UidState uidState = new com.android.internal.app.procstats.UidState(this, readInt3);
                if (!uidState.readFromParcel(parcel)) {
                    return;
                }
                this.mUidStates.put(readInt3, uidState);
            }
            int readInt4 = parcel.readInt();
            if (readInt4 < 0) {
                this.mReadError = "bad process count: " + readInt4;
                return;
            }
            while (true) {
                java.lang.String str4 = "bad uid count: ";
                if (readInt4 > 0) {
                    int i5 = readInt4 - 1;
                    java.lang.String readCommonString = readCommonString(parcel, readInt);
                    if (readCommonString == null) {
                        this.mReadError = "bad process name";
                        return;
                    }
                    int readInt5 = parcel.readInt();
                    if (readInt5 < 0) {
                        this.mReadError = "bad uid count: " + readInt5;
                        return;
                    }
                    while (readInt5 > 0) {
                        int i6 = readInt5 - 1;
                        int readInt6 = parcel.readInt();
                        if (readInt6 < 0) {
                            this.mReadError = "bad uid: " + readInt6;
                            return;
                        }
                        java.lang.String readCommonString2 = readCommonString(parcel, readInt);
                        if (readCommonString2 == null) {
                            this.mReadError = "bad process package name";
                            return;
                        }
                        long readLong = parcel.readLong();
                        com.android.internal.app.procstats.ProcessState processState = z3 ? this.mProcesses.get(readCommonString, readInt6) : null;
                        if (processState != null) {
                            if (processState.readFromParcel(parcel, readInt, z)) {
                                str3 = readCommonString;
                                i3 = readInt6;
                            } else {
                                return;
                            }
                        } else {
                            str3 = readCommonString;
                            i3 = readInt6;
                            com.android.internal.app.procstats.ProcessState processState2 = new com.android.internal.app.procstats.ProcessState(this, readCommonString2, readInt6, readLong, str3);
                            if (processState2.readFromParcel(parcel, readInt, true)) {
                                processState = processState2;
                            } else {
                                return;
                            }
                        }
                        java.lang.String str5 = str3;
                        this.mProcesses.put(str5, i3, processState);
                        com.android.internal.app.procstats.UidState uidState2 = this.mUidStates.get(i3);
                        if (uidState2 == null) {
                            uidState2 = new com.android.internal.app.procstats.UidState(this, i3);
                            this.mUidStates.put(i3, uidState2);
                        }
                        uidState2.addProcess(processState);
                        readCommonString = str5;
                        readInt5 = i6;
                        z = false;
                    }
                    readInt4 = i5;
                    z = false;
                } else {
                    for (int i7 = 0; i7 < readInt2; i7++) {
                        this.mUidStates.valueAt(i7).updateCombinedState(-1L);
                    }
                    int readInt7 = parcel.readInt();
                    if (readInt7 < 0) {
                        this.mReadError = "bad package count: " + readInt7;
                        return;
                    }
                    while (readInt7 > 0) {
                        int i8 = readInt7 - 1;
                        java.lang.String readCommonString3 = readCommonString(parcel, readInt);
                        if (readCommonString3 == null) {
                            this.mReadError = "bad package name";
                            return;
                        }
                        int readInt8 = parcel.readInt();
                        if (readInt8 < 0) {
                            this.mReadError = str4 + readInt8;
                            return;
                        }
                        while (readInt8 > 0) {
                            int i9 = readInt8 - 1;
                            int readInt9 = parcel.readInt();
                            if (readInt9 < 0) {
                                this.mReadError = "bad uid: " + readInt9;
                                return;
                            }
                            int readInt10 = parcel.readInt();
                            if (readInt10 < 0) {
                                this.mReadError = "bad versions count: " + readInt10;
                                return;
                            }
                            while (readInt10 > 0) {
                                int i10 = readInt10 - 1;
                                long readLong2 = parcel.readLong();
                                int i11 = readInt9;
                                java.lang.String str6 = str4;
                                com.android.internal.app.procstats.ProcessStats.PackageState packageState = new com.android.internal.app.procstats.ProcessStats.PackageState(this, readCommonString3, readInt9, readLong2);
                                android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> longSparseArray = this.mPackages.get(readCommonString3, i11);
                                if (longSparseArray == null) {
                                    longSparseArray = new android.util.LongSparseArray<>();
                                    this.mPackages.put(readCommonString3, i11, longSparseArray);
                                }
                                longSparseArray.put(readLong2, packageState);
                                int readInt11 = parcel.readInt();
                                if (readInt11 < 0) {
                                    this.mReadError = "bad package process count: " + readInt11;
                                    return;
                                }
                                while (readInt11 > 0) {
                                    readInt11--;
                                    java.lang.String readCommonString4 = readCommonString(parcel, readInt);
                                    if (readCommonString4 == null) {
                                        this.mReadError = "bad package process name";
                                        return;
                                    }
                                    int readInt12 = parcel.readInt();
                                    com.android.internal.app.procstats.ProcessState processState3 = this.mProcesses.get(readCommonString4, i11);
                                    if (processState3 == null) {
                                        this.mReadError = "no common proc: " + readCommonString4;
                                        return;
                                    }
                                    if (readInt12 != 0) {
                                        com.android.internal.app.procstats.ProcessState processState4 = z3 ? packageState.mProcesses.get(readCommonString4) : null;
                                        if (processState4 == null) {
                                            processState4 = new com.android.internal.app.procstats.ProcessState(processState3, readCommonString3, i11, readLong2, readCommonString4, 0L);
                                            if (!processState4.readFromParcel(parcel, readInt, true)) {
                                                return;
                                            }
                                        } else if (!processState4.readFromParcel(parcel, readInt, false)) {
                                            return;
                                        }
                                        packageState.mProcesses.put(readCommonString4, processState4);
                                    } else {
                                        packageState.mProcesses.put(readCommonString4, processState3);
                                    }
                                }
                                int readInt13 = parcel.readInt();
                                if (readInt13 < 0) {
                                    this.mReadError = "bad package service count: " + readInt13;
                                    return;
                                }
                                while (readInt13 > 0) {
                                    int i12 = readInt13 - 1;
                                    java.lang.String readString = parcel.readString();
                                    if (readString == null) {
                                        this.mReadError = "bad package service name";
                                        return;
                                    }
                                    java.lang.String readCommonString5 = readInt > 9 ? readCommonString(parcel, readInt) : null;
                                    com.android.internal.app.procstats.ServiceState serviceState = z3 ? packageState.mServices.get(readString) : null;
                                    if (serviceState != null) {
                                        i2 = i8;
                                        str2 = readString;
                                    } else {
                                        i2 = i8;
                                        str2 = readString;
                                        serviceState = new com.android.internal.app.procstats.ServiceState(this, readCommonString3, readString, readCommonString5, null);
                                    }
                                    if (!serviceState.readFromParcel(parcel)) {
                                        return;
                                    }
                                    packageState.mServices.put(str2, serviceState);
                                    readInt13 = i12;
                                    i8 = i2;
                                }
                                int i13 = i8;
                                int readInt14 = parcel.readInt();
                                if (readInt14 < 0) {
                                    this.mReadError = "bad package association count: " + readInt14;
                                    return;
                                }
                                while (readInt14 > 0) {
                                    int i14 = readInt14 - 1;
                                    java.lang.String readCommonString6 = readCommonString(parcel, readInt);
                                    if (readCommonString6 == null) {
                                        this.mReadError = "bad package association name";
                                        return;
                                    }
                                    java.lang.String readCommonString7 = readCommonString(parcel, readInt);
                                    com.android.internal.app.procstats.AssociationState associationState = z3 ? packageState.mAssociations.get(readCommonString6) : null;
                                    if (associationState != null) {
                                        i = i14;
                                        str = readCommonString6;
                                    } else {
                                        i = i14;
                                        str = readCommonString6;
                                        associationState = new com.android.internal.app.procstats.AssociationState(this, packageState, readCommonString6, readCommonString7, null);
                                    }
                                    java.lang.String readFromParcel = associationState.readFromParcel(this, parcel, readInt);
                                    if (readFromParcel != null) {
                                        this.mReadError = readFromParcel;
                                        return;
                                    } else {
                                        packageState.mAssociations.put(str, associationState);
                                        readInt14 = i;
                                    }
                                }
                                readInt9 = i11;
                                readInt10 = i10;
                                z2 = true;
                                i8 = i13;
                                str4 = str6;
                            }
                            readInt8 = i9;
                        }
                        readInt7 = i8;
                    }
                    int readInt15 = parcel.readInt();
                    this.mPageTypeNodes.clear();
                    this.mPageTypeNodes.ensureCapacity(readInt15);
                    this.mPageTypeZones.clear();
                    this.mPageTypeZones.ensureCapacity(readInt15);
                    this.mPageTypeLabels.clear();
                    this.mPageTypeLabels.ensureCapacity(readInt15);
                    this.mPageTypeSizes.clear();
                    this.mPageTypeSizes.ensureCapacity(readInt15);
                    for (int i15 = 0; i15 < readInt15; i15++) {
                        this.mPageTypeNodes.add(java.lang.Integer.valueOf(parcel.readInt()));
                        this.mPageTypeZones.add(parcel.readString());
                        this.mPageTypeLabels.add(parcel.readString());
                        this.mPageTypeSizes.add(parcel.createIntArray());
                    }
                    this.mIndexToCommonString = null;
                    return;
                }
            }
        }
    }

    public com.android.internal.app.procstats.ProcessStats.PackageState getPackageStateLocked(java.lang.String str, int i, long j) {
        android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> longSparseArray = this.mPackages.get(str, i);
        if (longSparseArray == null) {
            longSparseArray = new android.util.LongSparseArray<>();
            this.mPackages.put(str, i, longSparseArray);
        }
        com.android.internal.app.procstats.ProcessStats.PackageState packageState = longSparseArray.get(j);
        if (packageState != null) {
            return packageState;
        }
        com.android.internal.app.procstats.ProcessStats.PackageState packageState2 = new com.android.internal.app.procstats.ProcessStats.PackageState(this, str, i, j);
        longSparseArray.put(j, packageState2);
        return packageState2;
    }

    public com.android.internal.app.procstats.ProcessState getProcessStateLocked(java.lang.String str, int i, long j, java.lang.String str2) {
        return getProcessStateLocked(getPackageStateLocked(str, i, j), str2);
    }

    public com.android.internal.app.procstats.ProcessState getProcessStateLocked(com.android.internal.app.procstats.ProcessStats.PackageState packageState, java.lang.String str) {
        com.android.internal.app.procstats.ProcessState processState;
        com.android.internal.app.procstats.ProcessState processState2 = packageState.mProcesses.get(str);
        if (processState2 != null) {
            return processState2;
        }
        com.android.internal.app.procstats.ProcessState processState3 = this.mProcesses.get(str, packageState.mUid);
        if (processState3 != null) {
            processState = processState3;
        } else {
            com.android.internal.app.procstats.ProcessState processState4 = new com.android.internal.app.procstats.ProcessState(this, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, str);
            this.mProcesses.put(str, packageState.mUid, processState4);
            com.android.internal.app.procstats.UidState uidState = this.mUidStates.get(packageState.mUid);
            if (uidState == null) {
                uidState = new com.android.internal.app.procstats.UidState(this, packageState.mUid);
                this.mUidStates.put(packageState.mUid, uidState);
            }
            uidState.addProcess(processState4);
            processState = processState4;
        }
        if (!processState.isMultiPackage()) {
            if (!packageState.mPackageName.equals(processState.getPackage()) || packageState.mVersionCode != processState.getVersion()) {
                processState.setMultiPackage(true);
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                com.android.internal.app.procstats.ProcessStats.PackageState packageStateLocked = getPackageStateLocked(processState.getPackage(), packageState.mUid, processState.getVersion());
                if (packageStateLocked != null) {
                    com.android.internal.app.procstats.ProcessState clone = processState.clone(uptimeMillis);
                    packageStateLocked.mProcesses.put(processState.getName(), clone);
                    for (int size = packageStateLocked.mServices.size() - 1; size >= 0; size--) {
                        com.android.internal.app.procstats.ServiceState valueAt = packageStateLocked.mServices.valueAt(size);
                        if (valueAt.getProcess() == processState) {
                            valueAt.setProcess(clone);
                        }
                    }
                    for (int size2 = packageStateLocked.mAssociations.size() - 1; size2 >= 0; size2--) {
                        com.android.internal.app.procstats.AssociationState valueAt2 = packageStateLocked.mAssociations.valueAt(size2);
                        if (valueAt2.getProcess() == processState) {
                            valueAt2.setProcess(clone);
                        }
                    }
                } else {
                    android.util.Slog.w(TAG, "Cloning proc state: no package state " + processState.getPackage() + "/" + packageState.mUid + " for proc " + processState.getName());
                }
                processState = new com.android.internal.app.procstats.ProcessState(processState, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, str, uptimeMillis);
            }
        } else {
            processState = new com.android.internal.app.procstats.ProcessState(processState, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, str, android.os.SystemClock.uptimeMillis());
        }
        packageState.mProcesses.put(str, processState);
        return processState;
    }

    public com.android.internal.app.procstats.ServiceState getServiceStateLocked(java.lang.String str, int i, long j, java.lang.String str2, java.lang.String str3) {
        com.android.internal.app.procstats.ProcessStats.PackageState packageStateLocked = getPackageStateLocked(str, i, j);
        com.android.internal.app.procstats.ServiceState serviceState = packageStateLocked.mServices.get(str3);
        if (serviceState != null) {
            return serviceState;
        }
        com.android.internal.app.procstats.ServiceState serviceState2 = new com.android.internal.app.procstats.ServiceState(this, str, str3, str2, str2 != null ? getProcessStateLocked(str, i, j, str2) : null);
        packageStateLocked.mServices.put(str3, serviceState2);
        return serviceState2;
    }

    public com.android.internal.app.procstats.AssociationState getAssociationStateLocked(java.lang.String str, int i, long j, java.lang.String str2, java.lang.String str3) {
        com.android.internal.app.procstats.ProcessStats.PackageState packageStateLocked = getPackageStateLocked(str, i, j);
        com.android.internal.app.procstats.AssociationState associationState = packageStateLocked.mAssociations.get(str3);
        if (associationState != null) {
            return associationState;
        }
        com.android.internal.app.procstats.AssociationState associationState2 = new com.android.internal.app.procstats.AssociationState(this, packageStateLocked, str3, str2, str2 != null ? getProcessStateLocked(str, i, j, str2) : null);
        packageStateLocked.mAssociations.put(str3, associationState2);
        return associationState2;
    }

    public void updateTrackingAssociationsLocked(int i, long j) {
        for (int size = this.mTrackingAssociations.size() - 1; size >= 0; size--) {
            com.android.internal.app.procstats.AssociationState.SourceState sourceState = this.mTrackingAssociations.get(size);
            if (sourceState.stopActiveIfNecessary(i, j)) {
                this.mTrackingAssociations.remove(size);
            } else {
                com.android.internal.app.procstats.AssociationState associationState = sourceState.getAssociationState();
                if (associationState == null) {
                    android.util.Slog.wtf(TAG, sourceState.toString() + " shouldn't be in the tracking list.");
                } else {
                    com.android.internal.app.procstats.ProcessState process = associationState.getProcess();
                    if (process == null) {
                        android.util.Slog.wtf(TAG, "Tracking association without process: " + sourceState + " in " + associationState);
                    } else {
                        int combinedState = process.getCombinedState() % 16;
                        if (sourceState.mProcState == combinedState) {
                            sourceState.startActive(j);
                        } else {
                            sourceState.stopActive(j);
                            if (sourceState.mProcState < combinedState) {
                                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                                if (this.mNextInverseProcStateWarningUptime <= uptimeMillis) {
                                    android.util.Slog.w(TAG, "Tracking association " + sourceState + " whose proc state " + sourceState.mProcState + " is better than process " + process + " proc state " + combinedState + " (" + this.mSkippedInverseProcStateWarningCount + " skipped)");
                                    this.mSkippedInverseProcStateWarningCount = 0;
                                    this.mNextInverseProcStateWarningUptime = uptimeMillis + 10000;
                                } else {
                                    this.mSkippedInverseProcStateWarningCount++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    final class AssociationDumpContainer {
        long mActiveTime;
        java.util.ArrayList<android.util.Pair<com.android.internal.app.procstats.AssociationState.SourceKey, com.android.internal.app.procstats.AssociationState.SourceDumpContainer>> mSources;
        final com.android.internal.app.procstats.AssociationState mState;
        long mTotalTime;

        AssociationDumpContainer(com.android.internal.app.procstats.AssociationState associationState) {
            this.mState = associationState;
        }
    }

    static /* synthetic */ int lambda$static$0(com.android.internal.app.procstats.ProcessStats.AssociationDumpContainer associationDumpContainer, com.android.internal.app.procstats.ProcessStats.AssociationDumpContainer associationDumpContainer2) {
        int compareTo = associationDumpContainer.mState.getProcessName().compareTo(associationDumpContainer2.mState.getProcessName());
        if (compareTo != 0) {
            return compareTo;
        }
        if (associationDumpContainer.mActiveTime != associationDumpContainer2.mActiveTime) {
            return associationDumpContainer.mActiveTime > associationDumpContainer2.mActiveTime ? -1 : 1;
        }
        if (associationDumpContainer.mTotalTime != associationDumpContainer2.mTotalTime) {
            return associationDumpContainer.mTotalTime > associationDumpContainer2.mTotalTime ? -1 : 1;
        }
        int compareTo2 = associationDumpContainer.mState.getName().compareTo(associationDumpContainer2.mState.getName());
        if (compareTo2 != 0) {
            return compareTo2;
        }
        return 0;
    }

    public void dumpLocked(java.io.PrintWriter printWriter, java.lang.String str, long j, boolean z, boolean z2, boolean z3, boolean z4, int i) {
        boolean z5;
        com.android.internal.app.procstats.ProcessStats processStats;
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        java.lang.String str6;
        java.lang.String str7;
        long j2;
        java.lang.String str8;
        java.lang.String str9;
        java.lang.String str10;
        java.lang.String str11;
        java.lang.String str12;
        java.lang.String str13;
        java.lang.String str14;
        com.android.internal.app.procstats.ProcessStats processStats2;
        java.io.PrintWriter printWriter2;
        boolean z6;
        java.lang.String str15;
        java.lang.String str16;
        java.lang.String str17;
        java.lang.String str18;
        java.lang.String str19;
        int i2;
        int i3;
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<com.android.internal.app.procstats.ProcessState>> arrayMap;
        java.lang.String str20;
        java.lang.String str21;
        int i4;
        android.util.SparseArray<com.android.internal.app.procstats.ProcessState> sparseArray;
        java.lang.String str22;
        java.lang.String str23;
        java.lang.String str24;
        java.lang.String str25;
        java.lang.String str26;
        java.lang.String str27;
        int i5;
        boolean z7;
        int i6;
        int i7;
        boolean z8;
        boolean z9;
        boolean z10;
        int i8;
        java.lang.String str28;
        com.android.internal.app.procstats.ProcessStats.PackageState packageState;
        java.lang.String str29;
        int i9;
        java.lang.String str30;
        java.lang.String str31;
        int i10;
        int i11;
        java.lang.String str32;
        long j3;
        boolean z11;
        java.lang.String str33;
        java.lang.String str34;
        java.lang.String str35;
        com.android.internal.app.procstats.ProcessStats processStats3;
        java.lang.String str36;
        int i12;
        java.lang.String str37;
        long j4;
        int i13;
        int i14;
        java.lang.String str38;
        com.android.internal.app.procstats.ProcessStats.PackageState packageState2;
        java.lang.String str39;
        java.lang.String str40;
        int i15;
        int i16;
        long j5;
        java.lang.String str41;
        java.lang.String str42;
        int i17;
        java.lang.String str43;
        java.lang.String str44;
        java.lang.String str45;
        int i18;
        int i19;
        java.lang.String str46;
        java.lang.String str47;
        java.lang.String str48;
        int i20;
        boolean z12;
        com.android.internal.app.procstats.ProcessStats processStats4 = this;
        long dumpSingleTime = com.android.internal.app.procstats.DumpUtils.dumpSingleTime(null, null, processStats4.mMemFactorDurations, processStats4.mMemFactor, processStats4.mStartTime, j);
        printWriter.print("          Start time: ");
        printWriter.print(android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", processStats4.mTimePeriodStartClock));
        printWriter.println();
        printWriter.print("        Total uptime: ");
        android.util.TimeUtils.formatDuration((processStats4.mRunning ? android.os.SystemClock.uptimeMillis() : processStats4.mTimePeriodEndUptime) - processStats4.mTimePeriodStartUptime, printWriter);
        printWriter.println();
        printWriter.print("  Total elapsed time: ");
        android.util.TimeUtils.formatDuration((processStats4.mRunning ? android.os.SystemClock.elapsedRealtime() : processStats4.mTimePeriodEndRealtime) - processStats4.mTimePeriodStartRealtime, printWriter);
        if ((processStats4.mFlags & 2) == 0) {
            z5 = true;
        } else {
            printWriter.print(" (shutdown)");
            z5 = false;
        }
        if ((processStats4.mFlags & 4) != 0) {
            printWriter.print(" (sysprops)");
            z5 = false;
        }
        if ((processStats4.mFlags & 1) != 0) {
            printWriter.print(" (complete)");
            z5 = false;
        }
        if (z5) {
            printWriter.print(" (partial)");
        }
        if (processStats4.mHasSwappedOutPss) {
            printWriter.print(" (swapped-out-pss)");
        }
        printWriter.print(' ');
        printWriter.print(processStats4.mRuntime);
        printWriter.println();
        printWriter.print("     Aggregated over: ");
        printWriter.println(processStats4.mNumAggregated);
        if (processStats4.mSysMemUsage.getKeyCount() > 0) {
            printWriter.println();
            printWriter.println("System memory usage:");
            processStats4.mSysMemUsage.dump(printWriter, "  ", ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        }
        int i21 = i & 14;
        java.lang.String str49 = " / ";
        java.lang.String str50 = "      (Not active: ";
        java.lang.String str51 = " entries)";
        java.lang.String str52 = "  * ";
        java.lang.String str53 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        java.lang.String str54 = ":";
        if (i21 == 0) {
            processStats = processStats4;
            str2 = ":";
            str3 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            str4 = "  * ";
            str5 = " entries)";
            str6 = "      (Not active: ";
            str7 = " / ";
            j2 = dumpSingleTime;
        } else {
            android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> map = processStats4.mPackages.getMap();
            int i22 = 0;
            boolean z13 = false;
            while (i22 < map.size()) {
                java.lang.String keyAt = map.keyAt(i22);
                android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> arrayMap2 = map;
                android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> valueAt = map.valueAt(i22);
                int i23 = 0;
                while (i23 < valueAt.size()) {
                    int keyAt2 = valueAt.keyAt(i23);
                    android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> sparseArray2 = valueAt;
                    android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> valueAt2 = valueAt.valueAt(i23);
                    int i24 = i23;
                    int i25 = 0;
                    while (true) {
                        i6 = i22;
                        if (i25 < valueAt2.size()) {
                            java.lang.String str55 = str51;
                            java.lang.String str56 = str50;
                            long keyAt3 = valueAt2.keyAt(i25);
                            com.android.internal.app.procstats.ProcessStats.PackageState valueAt3 = valueAt2.valueAt(i25);
                            android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> longSparseArray = valueAt2;
                            int size = valueAt3.mProcesses.size();
                            java.lang.String str57 = str53;
                            int size2 = valueAt3.mServices.size();
                            long j6 = dumpSingleTime;
                            int size3 = valueAt3.mAssociations.size();
                            boolean z14 = str == null || str.equals(keyAt);
                            if (z14) {
                                i7 = i25;
                                z8 = false;
                                z9 = false;
                            } else {
                                int i26 = 0;
                                while (true) {
                                    if (i26 >= size) {
                                        i7 = i25;
                                        z12 = false;
                                        break;
                                    }
                                    i7 = i25;
                                    if (!str.equals(valueAt3.mProcesses.valueAt(i26).getName())) {
                                        i26++;
                                        i25 = i7;
                                    } else {
                                        z12 = true;
                                        break;
                                    }
                                }
                                if (z12) {
                                    z9 = z12;
                                    z8 = false;
                                } else {
                                    int i27 = 0;
                                    while (true) {
                                        if (i27 >= size3) {
                                            z9 = z12;
                                            z8 = false;
                                            break;
                                        }
                                        z9 = z12;
                                        if (!valueAt3.mAssociations.valueAt(i27).hasProcessOrPackage(str)) {
                                            i27++;
                                            z12 = z9;
                                        } else {
                                            z8 = true;
                                            break;
                                        }
                                    }
                                    if (!z8) {
                                        i8 = keyAt2;
                                        str28 = keyAt;
                                        processStats3 = processStats4;
                                        str37 = str54;
                                        str30 = str52;
                                        str31 = str49;
                                        i11 = i6;
                                        str33 = str57;
                                        j3 = j6;
                                        i12 = i24;
                                        str36 = str56;
                                        str53 = str33;
                                        str54 = str37;
                                        str51 = str55;
                                        str50 = str36;
                                        i24 = i12;
                                        keyAt2 = i8;
                                        i22 = i11;
                                        str52 = str30;
                                        str49 = str31;
                                        dumpSingleTime = j3;
                                        i25 = i7 + 1;
                                        processStats4 = processStats3;
                                        valueAt2 = longSparseArray;
                                        keyAt = str28;
                                    }
                                }
                            }
                            if (size > 0 || size2 > 0 || size3 > 0) {
                                if (!z13) {
                                    printWriter.println();
                                    printWriter.println("Per-Package Stats:");
                                    z13 = true;
                                }
                                printWriter.print(str52);
                                printWriter.print(keyAt);
                                printWriter.print(str49);
                                android.os.UserHandle.formatUid(printWriter, keyAt2);
                                printWriter.print(" / v");
                                printWriter.print(keyAt3);
                                printWriter.println(str54);
                                z10 = z13;
                            } else {
                                z10 = z13;
                            }
                            if ((i & 2) == 0 || z8) {
                                i8 = keyAt2;
                                str28 = keyAt;
                                packageState = valueAt3;
                                str29 = str54;
                                i9 = size2;
                                str30 = str52;
                                str31 = str49;
                                i10 = size3;
                                i11 = i6;
                                str32 = str56;
                                j3 = j6;
                                z11 = z8;
                                str33 = str57;
                            } else {
                                if (!z) {
                                    i8 = keyAt2;
                                    str28 = keyAt;
                                    packageState = valueAt3;
                                    str43 = str54;
                                    i9 = size2;
                                    str30 = str52;
                                    str31 = str49;
                                    i10 = size3;
                                    i11 = i6;
                                    str44 = str56;
                                    str45 = str55;
                                    j3 = j6;
                                    z11 = z8;
                                    str33 = str57;
                                } else if (z3) {
                                    i8 = keyAt2;
                                    str28 = keyAt;
                                    packageState = valueAt3;
                                    str43 = str54;
                                    i9 = size2;
                                    str30 = str52;
                                    str31 = str49;
                                    i10 = size3;
                                    i11 = i6;
                                    str44 = str56;
                                    str45 = str55;
                                    j3 = j6;
                                    z11 = z8;
                                    str33 = str57;
                                } else {
                                    java.util.ArrayList arrayList = new java.util.ArrayList();
                                    int i28 = 0;
                                    while (i28 < size) {
                                        com.android.internal.app.procstats.ProcessState valueAt4 = valueAt3.mProcesses.valueAt(i28);
                                        if (z14) {
                                            i20 = keyAt2;
                                        } else {
                                            i20 = keyAt2;
                                            if (!str.equals(valueAt4.getName())) {
                                                i28++;
                                                keyAt2 = i20;
                                            }
                                        }
                                        if (!z4 || valueAt4.isInUse()) {
                                            arrayList.add(valueAt4);
                                        }
                                        i28++;
                                        keyAt2 = i20;
                                    }
                                    i8 = keyAt2;
                                    str28 = keyAt;
                                    packageState = valueAt3;
                                    i11 = i6;
                                    z11 = z8;
                                    str33 = str57;
                                    str30 = str52;
                                    str31 = str49;
                                    i10 = size3;
                                    i9 = size2;
                                    j3 = j6;
                                    com.android.internal.app.procstats.DumpUtils.dumpProcessSummaryLocked(printWriter, "      ", "Prc ", arrayList, ALL_SCREEN_ADJ, ALL_MEM_ADJ, NON_CACHED_PROC_STATES, j, j3);
                                    str32 = str56;
                                    str29 = str54;
                                }
                                int i29 = 0;
                                while (i29 < size) {
                                    com.android.internal.app.procstats.ProcessState valueAt5 = packageState.mProcesses.valueAt(i29);
                                    if (!z14 && !str.equals(valueAt5.getName())) {
                                        i18 = size;
                                        str47 = str45;
                                        i19 = i29;
                                        str48 = str44;
                                        str46 = str43;
                                    } else if (z4 && !valueAt5.isInUse()) {
                                        printWriter.print(str44);
                                        printWriter.print(packageState.mProcesses.keyAt(i29));
                                        printWriter.println(str33);
                                        i18 = size;
                                        str47 = str45;
                                        i19 = i29;
                                        str48 = str44;
                                        str46 = str43;
                                    } else {
                                        printWriter.print("      Process ");
                                        printWriter.print(packageState.mProcesses.keyAt(i29));
                                        if (valueAt5.getCommonProcess().isMultiPackage()) {
                                            printWriter.print(" (multi, ");
                                        } else {
                                            printWriter.print(" (unique, ");
                                        }
                                        printWriter.print(valueAt5.getDurationsBucketCount());
                                        printWriter.print(str45);
                                        java.lang.String str58 = str43;
                                        printWriter.println(str58);
                                        i18 = size;
                                        i19 = i29;
                                        str46 = str58;
                                        valueAt5.dumpProcessState(printWriter, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, j);
                                        valueAt5.dumpPss(printWriter, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, j);
                                        str47 = str45;
                                        str48 = str44;
                                        valueAt5.dumpInternalLocked(printWriter, "        ", str, j3, j, z3);
                                    }
                                    i29 = i19 + 1;
                                    str43 = str46;
                                    str44 = str48;
                                    size = i18;
                                    str45 = str47;
                                }
                                str55 = str45;
                                str32 = str44;
                                str29 = str43;
                            }
                            java.lang.String str59 = "        Process: ";
                            if ((i & 4) == 0 || z11) {
                                str34 = str29;
                                str35 = "        Process: ";
                            } else {
                                int i30 = 0;
                                while (i30 < i9) {
                                    com.android.internal.app.procstats.ServiceState valueAt6 = packageState.mServices.valueAt(i30);
                                    if (!z14 && !str.equals(valueAt6.getProcessName())) {
                                        str41 = str29;
                                        str42 = str59;
                                        i17 = i30;
                                    } else if (z4 && !valueAt6.isInUse()) {
                                        printWriter.print("      (Not active service: ");
                                        printWriter.print(packageState.mServices.keyAt(i30));
                                        printWriter.println(str33);
                                        str41 = str29;
                                        str42 = str59;
                                        i17 = i30;
                                    } else {
                                        if (z3) {
                                            printWriter.print("      Service ");
                                        } else {
                                            printWriter.print("      * Svc ");
                                        }
                                        printWriter.print(packageState.mServices.keyAt(i30));
                                        printWriter.println(str29);
                                        printWriter.print(str59);
                                        printWriter.println(valueAt6.getProcessName());
                                        str41 = str29;
                                        str42 = str59;
                                        i17 = i30;
                                        valueAt6.dumpStats(printWriter, "        ", "          ", "    ", j, j3, z, z3);
                                    }
                                    i30 = i17 + 1;
                                    str59 = str42;
                                    str29 = str41;
                                }
                                str34 = str29;
                                str35 = str59;
                            }
                            if ((i & 8) == 0) {
                                processStats3 = this;
                                str36 = str32;
                                i12 = i24;
                                str37 = str34;
                            } else {
                                int i31 = i10;
                                java.util.ArrayList arrayList2 = new java.util.ArrayList(i31);
                                int i32 = 0;
                                while (i32 < i31) {
                                    com.android.internal.app.procstats.AssociationState valueAt7 = packageState.mAssociations.valueAt(i32);
                                    if (!z14 && !str.equals(valueAt7.getProcessName())) {
                                        if (!z11) {
                                            str40 = str32;
                                            i15 = i31;
                                            i16 = i24;
                                            str39 = str34;
                                            j5 = j3;
                                        } else if (!valueAt7.hasProcessOrPackage(str)) {
                                            str40 = str32;
                                            i15 = i31;
                                            i16 = i24;
                                            str39 = str34;
                                            j5 = j3;
                                        }
                                        i32++;
                                        j3 = j5;
                                        str34 = str39;
                                        str32 = str40;
                                        i24 = i16;
                                        i31 = i15;
                                    }
                                    str39 = str34;
                                    com.android.internal.app.procstats.ProcessStats.AssociationDumpContainer associationDumpContainer = new com.android.internal.app.procstats.ProcessStats.AssociationDumpContainer(valueAt7);
                                    str40 = str32;
                                    i15 = i31;
                                    i16 = i24;
                                    j5 = j3;
                                    associationDumpContainer.mSources = com.android.internal.app.procstats.AssociationState.createSortedAssociations(j, j5, valueAt7.mSources);
                                    associationDumpContainer.mTotalTime = valueAt7.getTotalDuration(j);
                                    associationDumpContainer.mActiveTime = valueAt7.getActiveDuration(j);
                                    arrayList2.add(associationDumpContainer);
                                    i32++;
                                    j3 = j5;
                                    str34 = str39;
                                    str32 = str40;
                                    i24 = i16;
                                    i31 = i15;
                                }
                                str36 = str32;
                                i12 = i24;
                                java.lang.String str60 = str34;
                                long j7 = j3;
                                java.util.Collections.sort(arrayList2, ASSOCIATION_COMPARATOR);
                                int size4 = arrayList2.size();
                                int i33 = 0;
                                while (i33 < size4) {
                                    com.android.internal.app.procstats.ProcessStats.AssociationDumpContainer associationDumpContainer2 = (com.android.internal.app.procstats.ProcessStats.AssociationDumpContainer) arrayList2.get(i33);
                                    com.android.internal.app.procstats.AssociationState associationState = associationDumpContainer2.mState;
                                    if (z4 && !associationState.isInUse()) {
                                        printWriter.print("      (Not active association: ");
                                        printWriter.print(packageState.mAssociations.keyAt(i33));
                                        printWriter.println(str33);
                                        packageState2 = packageState;
                                        j4 = j7;
                                        i13 = i33;
                                        i14 = size4;
                                        str38 = str60;
                                    } else {
                                        if (z3) {
                                            printWriter.print("      Association ");
                                        } else {
                                            printWriter.print("      * Asc ");
                                        }
                                        printWriter.print(associationDumpContainer2.mState.getName());
                                        printWriter.println(str60);
                                        printWriter.print(str35);
                                        printWriter.println(associationState.getProcessName());
                                        j4 = j7;
                                        i13 = i33;
                                        i14 = size4;
                                        str38 = str60;
                                        packageState2 = packageState;
                                        associationState.dumpStats(printWriter, "        ", "          ", "    ", associationDumpContainer2.mSources, j, j4, (!z11 || z14 || z9 || associationState.getProcessName().equals(str)) ? null : str, z2, z3);
                                    }
                                    i33 = i13 + 1;
                                    str60 = str38;
                                    j7 = j4;
                                    size4 = i14;
                                    packageState = packageState2;
                                }
                                processStats3 = this;
                                j3 = j7;
                                str37 = str60;
                            }
                            z13 = z10;
                            str53 = str33;
                            str54 = str37;
                            str51 = str55;
                            str50 = str36;
                            i24 = i12;
                            keyAt2 = i8;
                            i22 = i11;
                            str52 = str30;
                            str49 = str31;
                            dumpSingleTime = j3;
                            i25 = i7 + 1;
                            processStats4 = processStats3;
                            valueAt2 = longSparseArray;
                            keyAt = str28;
                        }
                    }
                    i22 = i6;
                    i23 = i24 + 1;
                    processStats4 = processStats4;
                    valueAt = sparseArray2;
                    keyAt = keyAt;
                }
                i22++;
                map = arrayMap2;
                processStats4 = processStats4;
            }
            processStats = processStats4;
            str2 = str54;
            str3 = str53;
            str4 = str52;
            str5 = str51;
            str6 = str50;
            str7 = str49;
            j2 = dumpSingleTime;
        }
        java.lang.String str61 = " (";
        java.lang.String str62 = " total";
        java.lang.String str63 = " shown of ";
        if ((i & 1) == 0) {
            str8 = " (";
            str9 = str2;
            str10 = " shown of ";
            str11 = " total";
            str12 = str6;
            str13 = str5;
        } else {
            android.util.ArrayMap<java.lang.String, android.util.SparseArray<com.android.internal.app.procstats.ProcessState>> map2 = processStats.mProcesses.getMap();
            int i34 = 0;
            int i35 = 0;
            boolean z15 = false;
            int i36 = 0;
            while (i36 < map2.size()) {
                java.lang.String keyAt4 = map2.keyAt(i36);
                android.util.SparseArray<com.android.internal.app.procstats.ProcessState> valueAt8 = map2.valueAt(i36);
                int i37 = 0;
                while (true) {
                    arrayMap = map2;
                    if (i37 < valueAt8.size()) {
                        int keyAt5 = valueAt8.keyAt(i37);
                        int i38 = i35 + 1;
                        com.android.internal.app.procstats.ProcessState valueAt9 = valueAt8.valueAt(i37);
                        if (!valueAt9.hasAnyData() || !valueAt9.isMultiPackage() || (str != null && !str.equals(keyAt4) && !str.equals(valueAt9.getPackage()))) {
                            str20 = str61;
                            str21 = str2;
                            i4 = i37;
                            sparseArray = valueAt8;
                            str22 = keyAt4;
                            str23 = str63;
                            str24 = str62;
                            str25 = str6;
                            str26 = str7;
                            str27 = str5;
                            i5 = i36;
                        } else {
                            int i39 = i34 + 1;
                            printWriter.println();
                            if (z15) {
                                z7 = z15;
                            } else {
                                printWriter.println("Multi-Package Common Processes:");
                                z7 = true;
                            }
                            if (!z4 || valueAt9.isInUse()) {
                                java.lang.String str64 = str6;
                                java.lang.String str65 = str4;
                                printWriter.print(str65);
                                printWriter.print(keyAt4);
                                java.lang.String str66 = str7;
                                printWriter.print(str66);
                                android.os.UserHandle.formatUid(printWriter, keyAt5);
                                printWriter.print(str61);
                                printWriter.print(valueAt9.getDurationsBucketCount());
                                java.lang.String str67 = str5;
                                printWriter.print(str67);
                                printWriter.println(str2);
                                str26 = str66;
                                str21 = str2;
                                str27 = str67;
                                str25 = str64;
                                i4 = i37;
                                sparseArray = valueAt8;
                                str22 = keyAt4;
                                str20 = str61;
                                str23 = str63;
                                i5 = i36;
                                valueAt9.dumpProcessState(printWriter, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, j);
                                valueAt9.dumpPss(printWriter, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, j);
                                str4 = str65;
                                str24 = str62;
                                valueAt9.dumpInternalLocked(printWriter, "        ", str, j2, j, z3);
                            } else {
                                java.lang.String str68 = str6;
                                printWriter.print(str68);
                                printWriter.print(keyAt4);
                                printWriter.println(str3);
                                str20 = str61;
                                str21 = str2;
                                str25 = str68;
                                i4 = i37;
                                sparseArray = valueAt8;
                                str22 = keyAt4;
                                str23 = str63;
                                str24 = str62;
                                str27 = str5;
                                str26 = str7;
                                i5 = i36;
                            }
                            i34 = i39;
                            z15 = z7;
                        }
                        i37 = i4 + 1;
                        str63 = str23;
                        str62 = str24;
                        i35 = i38;
                        valueAt8 = sparseArray;
                        keyAt4 = str22;
                        i36 = i5;
                        str7 = str26;
                        str5 = str27;
                        str61 = str20;
                        str2 = str21;
                        str6 = str25;
                        map2 = arrayMap;
                    }
                }
                i36++;
                str5 = str5;
                str61 = str61;
                str2 = str2;
                str6 = str6;
                map2 = arrayMap;
            }
            str8 = str61;
            str9 = str2;
            str10 = str63;
            str11 = str62;
            str12 = str6;
            str13 = str5;
            printWriter.print("  Total procs: ");
            printWriter.print(i34);
            printWriter.print(str10);
            printWriter.print(i35);
            printWriter.println(str11);
        }
        if ((i & 16) == 0) {
            str14 = str9;
        } else {
            android.util.SparseArray<com.android.internal.app.procstats.UidState> sparseArray3 = processStats.mUidStates;
            int size5 = sparseArray3.size();
            int i40 = 0;
            int i41 = 0;
            boolean z16 = false;
            int i42 = 0;
            while (i42 < size5) {
                int keyAt6 = sparseArray3.keyAt(i42);
                com.android.internal.app.procstats.UidState valueAt10 = sparseArray3.valueAt(i42);
                int i43 = i41 + 1;
                if (str != null && !valueAt10.hasPackage(str)) {
                    str17 = str12;
                    i2 = size5;
                    i3 = i42;
                    str15 = str13;
                    str16 = str8;
                    str19 = str4;
                    str18 = str9;
                } else {
                    int i44 = i40 + 1;
                    printWriter.println();
                    if (z16) {
                        z6 = z16;
                    } else {
                        printWriter.println("Per-UID Stats:");
                        z6 = true;
                    }
                    if (z4 && !valueAt10.isInUse()) {
                        printWriter.print(str12);
                        printWriter.print(android.os.UserHandle.formatUid(keyAt6));
                        printWriter.println(str3);
                        str17 = str12;
                        i2 = size5;
                        i3 = i42;
                        str15 = str13;
                        str16 = str8;
                        str19 = str4;
                        str18 = str9;
                    } else {
                        java.lang.String str69 = str4;
                        printWriter.print(str69);
                        android.os.UserHandle.formatUid(printWriter, keyAt6);
                        java.lang.String str70 = str8;
                        printWriter.print(str70);
                        printWriter.print(valueAt10.getDurationsBucketCount());
                        java.lang.String str71 = str13;
                        printWriter.print(str71);
                        java.lang.String str72 = str9;
                        printWriter.println(str72);
                        str15 = str71;
                        str16 = str70;
                        str17 = str12;
                        str18 = str72;
                        str19 = str69;
                        i2 = size5;
                        i3 = i42;
                        valueAt10.dumpState(printWriter, "        ", ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, j);
                    }
                    i40 = i44;
                    z16 = z6;
                }
                i42 = i3 + 1;
                str9 = str18;
                i41 = i43;
                str12 = str17;
                str4 = str19;
                size5 = i2;
                str8 = str16;
                str13 = str15;
            }
            str14 = str9;
            printWriter.print("  Total UIDs: ");
            printWriter.print(i40);
            printWriter.print(str10);
            printWriter.print(i41);
            printWriter.println(str11);
        }
        if (z3) {
            printWriter.println();
            if (processStats.mTrackingAssociations.size() > 0) {
                printWriter.println();
                printWriter.println("Tracking associations:");
                for (int i45 = 0; i45 < processStats.mTrackingAssociations.size(); i45++) {
                    com.android.internal.app.procstats.AssociationState.SourceState sourceState = processStats.mTrackingAssociations.get(i45);
                    com.android.internal.app.procstats.AssociationState associationState2 = sourceState.getAssociationState();
                    if (associationState2 == null) {
                        android.util.Slog.wtf(TAG, sourceState.toString() + " shouldn't be in the tracking list.");
                    } else {
                        printWriter.print("  #");
                        printWriter.print(i45);
                        printWriter.print(": ");
                        printWriter.print(associationState2.getProcessName());
                        printWriter.print("/");
                        android.os.UserHandle.formatUid(printWriter, associationState2.getUid());
                        printWriter.print(" <- ");
                        printWriter.print(sourceState.getProcessName());
                        printWriter.print("/");
                        android.os.UserHandle.formatUid(printWriter, sourceState.getUid());
                        printWriter.println(str14);
                        printWriter.print("    Tracking for: ");
                        android.util.TimeUtils.formatDuration(j - sourceState.mTrackingUptime, printWriter);
                        printWriter.println();
                        printWriter.print("    Component: ");
                        printWriter.print(new android.content.ComponentName(associationState2.getPackage(), associationState2.getName()).flattenToShortString());
                        printWriter.println();
                        printWriter.print("    Proc state: ");
                        if (sourceState.mProcState != -1) {
                            printWriter.print(com.android.internal.app.procstats.DumpUtils.STATE_NAMES[sourceState.mProcState]);
                        } else {
                            printWriter.print("--");
                        }
                        printWriter.print(" #");
                        printWriter.println(sourceState.mProcStateSeq);
                        printWriter.print("    Process: ");
                        printWriter.println(associationState2.getProcess());
                        if (sourceState.mActiveCount > 0) {
                            printWriter.print("    Active count ");
                            printWriter.print(sourceState.mActiveCount);
                            printWriter.print(": ");
                            com.android.internal.app.procstats.AssociationState.dumpActiveDurationSummary(printWriter, sourceState, j2, j, z3);
                            printWriter.println();
                        }
                    }
                }
            }
        }
        printWriter.println();
        if (z) {
            printWriter.println("Process summary:");
            processStats2 = processStats;
            printWriter2 = printWriter;
            dumpSummaryLocked(printWriter, str, j, z4);
        } else {
            processStats2 = processStats;
            printWriter2 = printWriter;
            processStats2.dumpTotalsLocked(printWriter2, j);
        }
        if (z3) {
            printWriter.println();
            printWriter2.println("Internal state:");
            printWriter2.print("  mRunning=");
            printWriter2.println(processStats2.mRunning);
        }
        if (str == null) {
            dumpFragmentationLocked(printWriter);
        }
    }

    public void dumpSummaryLocked(java.io.PrintWriter printWriter, java.lang.String str, long j, boolean z) {
        dumpFilteredSummaryLocked(printWriter, null, "  ", null, ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, NON_CACHED_PROC_STATES, j, com.android.internal.app.procstats.DumpUtils.dumpSingleTime(null, null, this.mMemFactorDurations, this.mMemFactor, this.mStartTime, j), str, z);
        printWriter.println();
        dumpTotalsLocked(printWriter, j);
    }

    private void dumpFragmentationLocked(java.io.PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("Available pages by page size:");
        int size = this.mPageTypeLabels.size();
        for (int i = 0; i < size; i++) {
            printWriter.format("Node %3d Zone %7s  %14s ", this.mPageTypeNodes.get(i), this.mPageTypeZones.get(i), this.mPageTypeLabels.get(i));
            int[] iArr = this.mPageTypeSizes.get(i);
            int length = iArr == null ? 0 : iArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                printWriter.format("%6d", java.lang.Integer.valueOf(iArr[i2]));
            }
            printWriter.println();
        }
    }

    long printMemoryCategory(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, double d, long j, long j2, int i) {
        if (d != 0.0d) {
            long j3 = (long) ((d * 1024.0d) / j);
            printWriter.print(str);
            printWriter.print(str2);
            printWriter.print(": ");
            android.util.DebugUtils.printSizeValue(printWriter, j3);
            printWriter.print(" (");
            printWriter.print(i);
            printWriter.print(" samples)");
            printWriter.println();
            return j2 + j3;
        }
        return j2;
    }

    void dumpTotalsLocked(java.io.PrintWriter printWriter, long j) {
        printWriter.println("Run time Stats:");
        com.android.internal.app.procstats.DumpUtils.dumpSingleTime(printWriter, "  ", this.mMemFactorDurations, this.mMemFactor, this.mStartTime, j);
        printWriter.println();
        printWriter.println("Memory usage:");
        com.android.internal.app.procstats.ProcessStats.TotalMemoryUseCollection totalMemoryUseCollection = new com.android.internal.app.procstats.ProcessStats.TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        computeTotalMemoryUse(totalMemoryUseCollection, j);
        long printMemoryCategory = printMemoryCategory(printWriter, "  ", "Native ", totalMemoryUseCollection.sysMemNativeWeight, totalMemoryUseCollection.totalTime, printMemoryCategory(printWriter, "  ", "Kernel ", totalMemoryUseCollection.sysMemKernelWeight, totalMemoryUseCollection.totalTime, 0L, totalMemoryUseCollection.sysMemSamples), totalMemoryUseCollection.sysMemSamples);
        for (int i = 0; i < 16; i++) {
            if (i != 9) {
                printMemoryCategory = printMemoryCategory(printWriter, "  ", com.android.internal.app.procstats.DumpUtils.STATE_NAMES[i], totalMemoryUseCollection.processStateWeight[i], totalMemoryUseCollection.totalTime, printMemoryCategory, totalMemoryUseCollection.processStateSamples[i]);
            }
        }
        long printMemoryCategory2 = printMemoryCategory(printWriter, "  ", "Z-Ram  ", totalMemoryUseCollection.sysMemZRamWeight, totalMemoryUseCollection.totalTime, printMemoryCategory(printWriter, "  ", "Free   ", totalMemoryUseCollection.sysMemFreeWeight, totalMemoryUseCollection.totalTime, printMemoryCategory(printWriter, "  ", "Cached ", totalMemoryUseCollection.sysMemCachedWeight, totalMemoryUseCollection.totalTime, printMemoryCategory, totalMemoryUseCollection.sysMemSamples), totalMemoryUseCollection.sysMemSamples), totalMemoryUseCollection.sysMemSamples);
        printWriter.print("  TOTAL  : ");
        android.util.DebugUtils.printSizeValue(printWriter, printMemoryCategory2);
        printWriter.println();
        printMemoryCategory(printWriter, "  ", com.android.internal.app.procstats.DumpUtils.STATE_NAMES[9], totalMemoryUseCollection.processStateWeight[9], totalMemoryUseCollection.totalTime, printMemoryCategory2, totalMemoryUseCollection.processStateSamples[9]);
        printWriter.println();
        printWriter.println("PSS collection stats:");
        printWriter.print("  Internal Single: ");
        printWriter.print(this.mInternalSinglePssCount);
        printWriter.print("x over ");
        android.util.TimeUtils.formatDuration(this.mInternalSinglePssTime, printWriter);
        printWriter.println();
        printWriter.print("  Internal All Procs (Memory Change): ");
        printWriter.print(this.mInternalAllMemPssCount);
        printWriter.print("x over ");
        android.util.TimeUtils.formatDuration(this.mInternalAllMemPssTime, printWriter);
        printWriter.println();
        printWriter.print("  Internal All Procs (Polling): ");
        printWriter.print(this.mInternalAllPollPssCount);
        printWriter.print("x over ");
        android.util.TimeUtils.formatDuration(this.mInternalAllPollPssTime, printWriter);
        printWriter.println();
        printWriter.print("  External: ");
        printWriter.print(this.mExternalPssCount);
        printWriter.print("x over ");
        android.util.TimeUtils.formatDuration(this.mExternalPssTime, printWriter);
        printWriter.println();
        printWriter.print("  External Slow: ");
        printWriter.print(this.mExternalSlowPssCount);
        printWriter.print("x over ");
        android.util.TimeUtils.formatDuration(this.mExternalSlowPssTime, printWriter);
        printWriter.println();
    }

    void dumpFilteredSummaryLocked(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, java.lang.String str3, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, long j, long j2, java.lang.String str4, boolean z) {
        java.util.ArrayList<com.android.internal.app.procstats.ProcessState> collectProcessesLocked = collectProcessesLocked(iArr, iArr2, iArr3, iArr4, j, str4, z);
        if (collectProcessesLocked.size() > 0) {
            if (str != null) {
                printWriter.println();
                printWriter.println(str);
            }
            com.android.internal.app.procstats.DumpUtils.dumpProcessSummaryLocked(printWriter, str2, str3, collectProcessesLocked, iArr, iArr2, iArr4, j, j2);
        }
    }

    public java.util.ArrayList<com.android.internal.app.procstats.ProcessState> collectProcessesLocked(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, long j, java.lang.String str, boolean z) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> map = this.mPackages.getMap();
        for (int i = 0; i < map.size(); i++) {
            java.lang.String keyAt = map.keyAt(i);
            android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> valueAt = map.valueAt(i);
            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> valueAt2 = valueAt.valueAt(i2);
                int size = valueAt2.size();
                for (int i3 = 0; i3 < size; i3++) {
                    com.android.internal.app.procstats.ProcessStats.PackageState valueAt3 = valueAt2.valueAt(i3);
                    int size2 = valueAt3.mProcesses.size();
                    boolean z2 = str == null || str.equals(keyAt);
                    for (int i4 = 0; i4 < size2; i4++) {
                        com.android.internal.app.procstats.ProcessState valueAt4 = valueAt3.mProcesses.valueAt(i4);
                        if ((z2 || str.equals(valueAt4.getName())) && (!z || valueAt4.isInUse())) {
                            arraySet.add(valueAt4.getCommonProcess());
                        }
                    }
                }
            }
        }
        java.util.ArrayList<com.android.internal.app.procstats.ProcessState> arrayList = new java.util.ArrayList<>(arraySet.size());
        for (int i5 = 0; i5 < arraySet.size(); i5++) {
            com.android.internal.app.procstats.ProcessState processState = (com.android.internal.app.procstats.ProcessState) arraySet.valueAt(i5);
            if (processState.computeProcessTimeLocked(iArr, iArr2, iArr3, j) > 0) {
                arrayList.add(processState);
                if (iArr3 != iArr4) {
                    processState.computeProcessTimeLocked(iArr, iArr2, iArr4, j);
                }
            }
        }
        java.util.Collections.sort(arrayList, com.android.internal.app.procstats.ProcessState.COMPARATOR);
        return arrayList;
    }

    public void dumpCheckinLocked(java.io.PrintWriter printWriter, java.lang.String str, int i) {
        boolean z;
        java.lang.String str2;
        com.android.internal.app.procstats.ProcessStats processStats;
        java.lang.String str3;
        android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> longSparseArray;
        android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> sparseArray;
        int i2;
        java.lang.String str4;
        int i3;
        int i4;
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> arrayMap;
        java.lang.String str5;
        com.android.internal.app.procstats.ProcessStats.PackageState packageState;
        int i5;
        java.lang.String str6 = str;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> map = this.mPackages.getMap();
        printWriter.println("vers,5");
        printWriter.print("period,");
        printWriter.print(this.mTimePeriodStartClockStr);
        java.lang.String str7 = ",";
        printWriter.print(",");
        printWriter.print(this.mTimePeriodStartRealtime);
        printWriter.print(",");
        printWriter.print(this.mRunning ? android.os.SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime);
        if ((this.mFlags & 2) == 0) {
            z = true;
        } else {
            printWriter.print(",shutdown");
            z = false;
        }
        if ((this.mFlags & 4) != 0) {
            printWriter.print(",sysprops");
            z = false;
        }
        if ((this.mFlags & 1) != 0) {
            printWriter.print(",complete");
            z = false;
        }
        if (z) {
            printWriter.print(",partial");
        }
        if (this.mHasSwappedOutPss) {
            printWriter.print(",swapped-out-pss");
        }
        printWriter.println();
        printWriter.print("config,");
        printWriter.println(this.mRuntime);
        if ((i & 14) == 0) {
            str2 = ",";
        } else {
            int i6 = 0;
            while (i6 < map.size()) {
                java.lang.String keyAt = map.keyAt(i6);
                if (str6 == null || str6.equals(keyAt)) {
                    android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> valueAt = map.valueAt(i6);
                    int i7 = 0;
                    while (i7 < valueAt.size()) {
                        int keyAt2 = valueAt.keyAt(i7);
                        android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> valueAt2 = valueAt.valueAt(i7);
                        int i8 = 0;
                        while (i8 < valueAt2.size()) {
                            long keyAt3 = valueAt2.keyAt(i8);
                            com.android.internal.app.procstats.ProcessStats.PackageState valueAt3 = valueAt2.valueAt(i8);
                            int size = valueAt3.mProcesses.size();
                            int size2 = valueAt3.mServices.size();
                            int i9 = i8;
                            int size3 = valueAt3.mAssociations.size();
                            if ((i & 2) == 0) {
                                longSparseArray = valueAt2;
                                sparseArray = valueAt;
                                i2 = i7;
                                str4 = keyAt;
                                i3 = size2;
                                i4 = i6;
                                arrayMap = map;
                                str5 = str7;
                                packageState = valueAt3;
                                i5 = size3;
                            } else {
                                int i10 = 0;
                                while (i10 < size) {
                                    valueAt3.mProcesses.valueAt(i10).dumpPackageProcCheckin(printWriter, keyAt, keyAt2, keyAt3, valueAt3.mProcesses.keyAt(i10), uptimeMillis);
                                    i10++;
                                    size2 = size2;
                                    keyAt = keyAt;
                                    valueAt3 = valueAt3;
                                    size3 = size3;
                                    i6 = i6;
                                    size = size;
                                    map = map;
                                    str7 = str7;
                                    valueAt2 = valueAt2;
                                    valueAt = valueAt;
                                    i7 = i7;
                                }
                                longSparseArray = valueAt2;
                                sparseArray = valueAt;
                                i2 = i7;
                                str4 = keyAt;
                                i3 = size2;
                                i4 = i6;
                                arrayMap = map;
                                str5 = str7;
                                packageState = valueAt3;
                                i5 = size3;
                            }
                            if ((i & 4) != 0) {
                                for (int i11 = 0; i11 < i3; i11++) {
                                    packageState.mServices.valueAt(i11).dumpTimesCheckin(printWriter, str4, keyAt2, keyAt3, com.android.internal.app.procstats.DumpUtils.collapseString(str4, packageState.mServices.keyAt(i11)), uptimeMillis);
                                }
                            }
                            if ((i & 8) != 0) {
                                for (int i12 = 0; i12 < i5; i12++) {
                                    packageState.mAssociations.valueAt(i12).dumpTimesCheckin(printWriter, str4, keyAt2, keyAt3, com.android.internal.app.procstats.DumpUtils.collapseString(str4, packageState.mAssociations.keyAt(i12)), uptimeMillis);
                                }
                            }
                            i8 = i9 + 1;
                            keyAt = str4;
                            i6 = i4;
                            map = arrayMap;
                            str7 = str5;
                            valueAt2 = longSparseArray;
                            valueAt = sparseArray;
                            i7 = i2;
                        }
                        i7++;
                    }
                }
                i6++;
                str6 = str;
                map = map;
                str7 = str7;
            }
            str2 = str7;
        }
        if ((i & 1) == 0) {
            processStats = this;
        } else {
            processStats = this;
            android.util.ArrayMap<java.lang.String, android.util.SparseArray<com.android.internal.app.procstats.ProcessState>> map2 = processStats.mProcesses.getMap();
            for (int i13 = 0; i13 < map2.size(); i13++) {
                java.lang.String keyAt4 = map2.keyAt(i13);
                android.util.SparseArray<com.android.internal.app.procstats.ProcessState> valueAt4 = map2.valueAt(i13);
                for (int i14 = 0; i14 < valueAt4.size(); i14++) {
                    valueAt4.valueAt(i14).dumpProcCheckin(printWriter, keyAt4, valueAt4.keyAt(i14), uptimeMillis);
                }
            }
        }
        printWriter.print("total");
        com.android.internal.app.procstats.DumpUtils.dumpAdjTimesCheckin(printWriter, ",", processStats.mMemFactorDurations, processStats.mMemFactor, processStats.mStartTime, uptimeMillis);
        printWriter.println();
        int keyCount = processStats.mSysMemUsage.getKeyCount();
        if (keyCount <= 0) {
            str3 = str2;
        } else {
            printWriter.print("sysmemusage");
            int i15 = 0;
            while (i15 < keyCount) {
                int keyAt5 = processStats.mSysMemUsage.getKeyAt(i15);
                byte idFromKey = com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt5);
                java.lang.String str8 = str2;
                printWriter.print(str8);
                com.android.internal.app.procstats.DumpUtils.printProcStateTag(printWriter, idFromKey);
                for (int i16 = 0; i16 < 16; i16++) {
                    if (i16 > 1) {
                        printWriter.print(":");
                    }
                    printWriter.print(processStats.mSysMemUsage.getValue(keyAt5, i16));
                }
                i15++;
                str2 = str8;
            }
            str3 = str2;
        }
        printWriter.println();
        com.android.internal.app.procstats.ProcessStats.TotalMemoryUseCollection totalMemoryUseCollection = new com.android.internal.app.procstats.ProcessStats.TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        processStats.computeTotalMemoryUse(totalMemoryUseCollection, uptimeMillis);
        printWriter.print("weights,");
        printWriter.print(totalMemoryUseCollection.totalTime);
        printWriter.print(str3);
        printWriter.print(totalMemoryUseCollection.sysMemCachedWeight);
        printWriter.print(":");
        printWriter.print(totalMemoryUseCollection.sysMemSamples);
        printWriter.print(str3);
        printWriter.print(totalMemoryUseCollection.sysMemFreeWeight);
        printWriter.print(":");
        printWriter.print(totalMemoryUseCollection.sysMemSamples);
        printWriter.print(str3);
        printWriter.print(totalMemoryUseCollection.sysMemZRamWeight);
        printWriter.print(":");
        printWriter.print(totalMemoryUseCollection.sysMemSamples);
        printWriter.print(str3);
        printWriter.print(totalMemoryUseCollection.sysMemKernelWeight);
        printWriter.print(":");
        printWriter.print(totalMemoryUseCollection.sysMemSamples);
        printWriter.print(str3);
        printWriter.print(totalMemoryUseCollection.sysMemNativeWeight);
        printWriter.print(":");
        printWriter.print(totalMemoryUseCollection.sysMemSamples);
        for (int i17 = 0; i17 < 16; i17++) {
            printWriter.print(str3);
            printWriter.print(totalMemoryUseCollection.processStateWeight[i17]);
            printWriter.print(":");
            printWriter.print(totalMemoryUseCollection.processStateSamples[i17]);
        }
        printWriter.println();
        int size4 = processStats.mPageTypeLabels.size();
        for (int i18 = 0; i18 < size4; i18++) {
            printWriter.print("availablepages,");
            printWriter.print(processStats.mPageTypeLabels.get(i18));
            printWriter.print(str3);
            printWriter.print(processStats.mPageTypeZones.get(i18));
            printWriter.print(str3);
            int[] iArr = processStats.mPageTypeSizes.get(i18);
            int length = iArr == null ? 0 : iArr.length;
            for (int i19 = 0; i19 < length; i19++) {
                if (i19 != 0) {
                    printWriter.print(str3);
                }
                printWriter.print(iArr[i19]);
            }
            printWriter.println();
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        dumpProtoPreamble(protoOutputStream);
        int size = this.mPageTypeLabels.size();
        for (int i2 = 0; i2 < size; i2++) {
            long start = protoOutputStream.start(2246267895818L);
            protoOutputStream.write(1120986464257L, this.mPageTypeNodes.get(i2).intValue());
            protoOutputStream.write(1138166333442L, this.mPageTypeZones.get(i2));
            protoOutputStream.write(1138166333443L, this.mPageTypeLabels.get(i2));
            int[] iArr = this.mPageTypeSizes.get(i2);
            int length = iArr == null ? 0 : iArr.length;
            for (int i3 = 0; i3 < length; i3++) {
                protoOutputStream.write(2220498092036L, iArr[i3]);
            }
            protoOutputStream.end(start);
        }
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<com.android.internal.app.procstats.ProcessState>> map = this.mProcesses.getMap();
        if ((i & 1) != 0) {
            for (int i4 = 0; i4 < map.size(); i4++) {
                java.lang.String keyAt = map.keyAt(i4);
                android.util.SparseArray<com.android.internal.app.procstats.ProcessState> valueAt = map.valueAt(i4);
                for (int i5 = 0; i5 < valueAt.size(); i5++) {
                    valueAt.valueAt(i5).dumpDebug(protoOutputStream, 2246267895816L, keyAt, valueAt.keyAt(i5), j);
                }
            }
        }
        if ((i & 14) != 0) {
            android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> map2 = this.mPackages.getMap();
            for (int i6 = 0; i6 < map2.size(); i6++) {
                android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> valueAt2 = map2.valueAt(i6);
                for (int i7 = 0; i7 < valueAt2.size(); i7++) {
                    android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> valueAt3 = valueAt2.valueAt(i7);
                    for (int i8 = 0; i8 < valueAt3.size(); i8++) {
                        valueAt3.valueAt(i8).dumpDebug(protoOutputStream, 2246267895817L, j, i);
                    }
                }
            }
        }
    }

    public void dumpAggregatedProtoForStatsd(android.util.proto.ProtoOutputStream[] protoOutputStreamArr, long j) {
        int i;
        int i2 = 0;
        dumpProtoPreamble(protoOutputStreamArr[0]);
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<com.android.internal.app.procstats.ProcessState>> map = this.mProcesses.getMap();
        com.android.internal.app.ProcessMap<android.util.ArraySet<com.android.internal.app.procstats.ProcessStats.PackageState>> processMap = new com.android.internal.app.ProcessMap<>();
        android.util.SparseArray<android.util.ArraySet<java.lang.String>> sparseArray = new android.util.SparseArray<>();
        collectProcessPackageMaps(null, false, processMap, sparseArray);
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i4 >= map.size()) {
                break;
            }
            java.lang.String keyAt = map.keyAt(i4);
            if (protoOutputStreamArr[i3].getRawSize() <= j) {
                i = i3;
            } else {
                i3++;
                if (i3 < protoOutputStreamArr.length) {
                    dumpProtoPreamble(protoOutputStreamArr[i3]);
                    i = i3;
                } else {
                    android.util.Slog.d(TAG, java.lang.String.format("Dropping process indices from %d to %d from statsd proto (too large)", java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(map.size())));
                    break;
                }
            }
            android.util.SparseArray<com.android.internal.app.procstats.ProcessState> valueAt = map.valueAt(i4);
            int i5 = i2;
            while (i5 < valueAt.size()) {
                valueAt.valueAt(i5).dumpAggregatedProtoForStatsd(protoOutputStreamArr[i], 2246267895816L, keyAt, valueAt.keyAt(i5), this.mTimePeriodEndRealtime, processMap, sparseArray);
                i5++;
                valueAt = valueAt;
                i4 = i4;
                map = map;
            }
            i4++;
            i3 = i;
            map = map;
            i2 = 0;
        }
        for (int i6 = 0; i6 <= i3; i6++) {
            protoOutputStreamArr[i6].flush();
        }
    }

    void forEachProcess(java.util.function.Consumer<com.android.internal.app.procstats.ProcessState> consumer) {
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<com.android.internal.app.procstats.ProcessState>> map = this.mProcesses.getMap();
        int size = map.size();
        for (int i = 0; i < size; i++) {
            android.util.SparseArray<com.android.internal.app.procstats.ProcessState> valueAt = map.valueAt(i);
            int size2 = valueAt.size();
            for (int i2 = 0; i2 < size2; i2++) {
                consumer.accept(valueAt.valueAt(i2));
            }
        }
    }

    void forEachAssociation(com.android.internal.util.function.QuintConsumer<com.android.internal.app.procstats.AssociationState, java.lang.Integer, java.lang.String, com.android.internal.app.procstats.AssociationState.SourceKey, com.android.internal.app.procstats.AssociationState.SourceState> quintConsumer) {
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> map = this.mPackages.getMap();
        int size = map.size();
        for (int i = 0; i < size; i++) {
            android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> valueAt = map.valueAt(i);
            int size2 = valueAt.size();
            for (int i2 = 0; i2 < size2; i2++) {
                int keyAt = valueAt.keyAt(i2);
                android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> valueAt2 = valueAt.valueAt(i2);
                int size3 = valueAt2.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    com.android.internal.app.procstats.ProcessStats.PackageState valueAt3 = valueAt2.valueAt(i3);
                    int size4 = valueAt3.mAssociations.size();
                    int i4 = 0;
                    while (i4 < size4) {
                        java.lang.String keyAt2 = valueAt3.mAssociations.keyAt(i4);
                        com.android.internal.app.procstats.AssociationState valueAt4 = valueAt3.mAssociations.valueAt(i4);
                        int size5 = valueAt4.mSources.size();
                        android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> arrayMap = map;
                        int i5 = 0;
                        while (i5 < size5) {
                            int i6 = size;
                            com.android.internal.app.procstats.AssociationState.SourceState valueAt5 = valueAt4.mSources.valueAt(i5);
                            com.android.internal.app.procstats.AssociationState associationState = valueAt4;
                            quintConsumer.accept(associationState, java.lang.Integer.valueOf(keyAt), keyAt2, valueAt4.mSources.keyAt(i5), valueAt5);
                            i5++;
                            valueAt4 = associationState;
                            size = i6;
                        }
                        i4++;
                        map = arrayMap;
                    }
                }
            }
        }
    }

    public void dumpProcessState(final int i, final com.android.internal.app.procstats.StatsEventOutput statsEventOutput) {
        forEachProcess(new java.util.function.Consumer() { // from class: com.android.internal.app.procstats.ProcessStats$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.internal.app.procstats.ProcessStats.this.lambda$dumpProcessState$1(i, statsEventOutput, (com.android.internal.app.procstats.ProcessState) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpProcessState$1(int i, com.android.internal.app.procstats.StatsEventOutput statsEventOutput, com.android.internal.app.procstats.ProcessState processState) {
        if (processState.isMultiPackage() && processState.getCommonProcess() != processState) {
            return;
        }
        processState.dumpStateDurationToStatsd(i, this, statsEventOutput);
    }

    public void dumpProcessAssociation(final int i, final com.android.internal.app.procstats.StatsEventOutput statsEventOutput) {
        forEachAssociation(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.internal.app.procstats.ProcessStats$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.function.QuintConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                com.android.internal.app.procstats.ProcessStats.this.lambda$dumpProcessAssociation$2(statsEventOutput, i, (com.android.internal.app.procstats.AssociationState) obj, (java.lang.Integer) obj2, (java.lang.String) obj3, (com.android.internal.app.procstats.AssociationState.SourceKey) obj4, (com.android.internal.app.procstats.AssociationState.SourceState) obj5);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpProcessAssociation$2(com.android.internal.app.procstats.StatsEventOutput statsEventOutput, int i, com.android.internal.app.procstats.AssociationState associationState, java.lang.Integer num, java.lang.String str, com.android.internal.app.procstats.AssociationState.SourceKey sourceKey, com.android.internal.app.procstats.AssociationState.SourceState sourceState) {
        statsEventOutput.write(i, sourceKey.mUid, sourceKey.mProcess, num.intValue(), str, (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(this.mTimePeriodStartUptime), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(this.mTimePeriodEndUptime), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(this.mTimePeriodEndUptime - this.mTimePeriodStartUptime), (int) java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(sourceState.mDuration), sourceState.mActiveCount, associationState.getProcessName());
    }

    private void dumpProtoPreamble(android.util.proto.ProtoOutputStream protoOutputStream) {
        boolean z;
        protoOutputStream.write(1112396529665L, this.mTimePeriodStartRealtime);
        protoOutputStream.write(1112396529666L, this.mRunning ? android.os.SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime);
        protoOutputStream.write(1112396529667L, this.mTimePeriodStartUptime);
        protoOutputStream.write(1112396529668L, this.mTimePeriodEndUptime);
        protoOutputStream.write(1138166333445L, this.mRuntime);
        protoOutputStream.write(1133871366150L, this.mHasSwappedOutPss);
        boolean z2 = false;
        if ((this.mFlags & 2) == 0) {
            z = true;
        } else {
            protoOutputStream.write(2259152797703L, 3);
            z = false;
        }
        if ((this.mFlags & 4) != 0) {
            protoOutputStream.write(2259152797703L, 4);
            z = false;
        }
        if ((this.mFlags & 1) == 0) {
            z2 = z;
        } else {
            protoOutputStream.write(2259152797703L, 1);
        }
        if (z2) {
            protoOutputStream.write(2259152797703L, 2);
        }
    }

    private void collectProcessPackageMaps(java.lang.String str, boolean z, com.android.internal.app.ProcessMap<android.util.ArraySet<com.android.internal.app.procstats.ProcessStats.PackageState>> processMap, android.util.SparseArray<android.util.ArraySet<java.lang.String>> sparseArray) {
        android.util.ArraySet<com.android.internal.app.procstats.ProcessStats.PackageState> arraySet;
        android.util.ArrayMap<java.lang.String, android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>>> map = this.mPackages.getMap();
        int i = 1;
        int size = map.size() - 1;
        while (size >= 0) {
            java.lang.String keyAt = map.keyAt(size);
            android.util.SparseArray<android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState>> valueAt = map.valueAt(size);
            int size2 = valueAt.size() - i;
            while (size2 >= 0) {
                android.util.LongSparseArray<com.android.internal.app.procstats.ProcessStats.PackageState> valueAt2 = valueAt.valueAt(size2);
                int size3 = valueAt2.size() - i;
                while (size3 >= 0) {
                    com.android.internal.app.procstats.ProcessStats.PackageState valueAt3 = valueAt2.valueAt(size3);
                    int i2 = (str == null || str.equals(keyAt)) ? i : 0;
                    for (int size4 = valueAt3.mProcesses.size() - i; size4 >= 0; size4--) {
                        com.android.internal.app.procstats.ProcessState valueAt4 = valueAt3.mProcesses.valueAt(size4);
                        if ((i2 != 0 || str.equals(valueAt4.getName())) && (!z || valueAt4.isInUse())) {
                            java.lang.String name = valueAt4.getName();
                            int uid = valueAt4.getUid();
                            android.util.ArraySet<com.android.internal.app.procstats.ProcessStats.PackageState> arraySet2 = processMap.get(name, uid);
                            if (arraySet2 != null) {
                                arraySet = arraySet2;
                            } else {
                                arraySet = new android.util.ArraySet<>();
                                processMap.put(name, uid, arraySet);
                            }
                            arraySet.add(valueAt3);
                            android.util.ArraySet<java.lang.String> arraySet3 = sparseArray.get(uid);
                            if (arraySet3 == null) {
                                arraySet3 = new android.util.ArraySet<>();
                                sparseArray.put(uid, arraySet3);
                            }
                            arraySet3.add(valueAt3.mPackageName);
                        }
                    }
                    size3--;
                    i = 1;
                }
                size2--;
                i = 1;
            }
            size--;
            i = 1;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v3 */
    public void dumpFilteredAssociationStatesProtoForProc(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, com.android.internal.app.procstats.ProcessState processState, android.util.SparseArray<android.util.ArraySet<java.lang.String>> sparseArray) {
        android.util.ArrayMap<com.android.internal.app.procstats.AssociationState.SourceKey, com.android.internal.app.procstats.AssociationState.SourceState> arrayMap;
        com.android.internal.app.procstats.IProcessStats asInterface;
        long j3;
        int i;
        android.util.SparseArray<android.util.ArraySet<java.lang.String>> sparseArray2 = sparseArray;
        if ((!processState.isMultiPackage() || processState.getCommonProcess() == processState) && (arrayMap = processState.mCommonSources) != null && !arrayMap.isEmpty() && (asInterface = com.android.internal.app.procstats.IProcessStats.Stub.asInterface(android.os.ServiceManager.getService(SERVICE_NAME))) != null) {
            try {
                long minAssociationDumpDuration = asInterface.getMinAssociationDumpDuration();
                ?? r11 = 1;
                int size = arrayMap.size() - 1;
                while (size >= 0) {
                    com.android.internal.app.procstats.AssociationState.SourceState valueAt = arrayMap.valueAt(size);
                    long j4 = valueAt.mDuration;
                    if (valueAt.mNesting <= 0) {
                        j3 = j4;
                    } else {
                        j3 = j4 + (j2 - valueAt.mStartUptime);
                    }
                    if (j3 < minAssociationDumpDuration) {
                        i = size;
                    } else {
                        com.android.internal.app.procstats.AssociationState.SourceKey keyAt = arrayMap.keyAt(size);
                        long start = protoOutputStream.start(j);
                        int indexOfKey = sparseArray2.indexOfKey(keyAt.mUid);
                        i = size;
                        com.android.internal.app.procstats.ProcessState.writeCompressedProcessName(protoOutputStream, 1138166333441L, keyAt.mProcess, keyAt.mPackage, (indexOfKey < 0 || sparseArray2.valueAt(indexOfKey).size() <= r11) ? false : r11);
                        protoOutputStream.write(1120986464261L, keyAt.mUid);
                        protoOutputStream.write(1120986464259L, valueAt.mCount);
                        protoOutputStream.write(1120986464260L, (int) (j3 / 1000));
                        protoOutputStream.end(start);
                    }
                    size = i - 1;
                    sparseArray2 = sparseArray;
                    r11 = 1;
                }
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public static final class ProcessStateHolder {
        public final long appVersion;
        public com.android.internal.app.procstats.ProcessStats.PackageState pkg;
        public com.android.internal.app.procstats.ProcessState state;

        public ProcessStateHolder(long j) {
            this.appVersion = j;
        }
    }

    public static final class PackageState {
        public final java.lang.String mPackageName;
        public final com.android.internal.app.procstats.ProcessStats mProcessStats;
        public final int mUid;
        public final long mVersionCode;
        public final android.util.ArrayMap<java.lang.String, com.android.internal.app.procstats.ProcessState> mProcesses = new android.util.ArrayMap<>();
        public final android.util.ArrayMap<java.lang.String, com.android.internal.app.procstats.ServiceState> mServices = new android.util.ArrayMap<>();
        public final android.util.ArrayMap<java.lang.String, com.android.internal.app.procstats.AssociationState> mAssociations = new android.util.ArrayMap<>();

        public PackageState(com.android.internal.app.procstats.ProcessStats processStats, java.lang.String str, int i, long j) {
            this.mProcessStats = processStats;
            this.mUid = i;
            this.mPackageName = str;
            this.mVersionCode = j;
        }

        public com.android.internal.app.procstats.AssociationState getAssociationStateLocked(com.android.internal.app.procstats.ProcessState processState, java.lang.String str) {
            com.android.internal.app.procstats.AssociationState associationState = this.mAssociations.get(str);
            if (associationState != null) {
                if (processState != null) {
                    associationState.setProcess(processState);
                }
                return associationState;
            }
            com.android.internal.app.procstats.AssociationState associationState2 = new com.android.internal.app.procstats.AssociationState(this.mProcessStats, this, str, processState.getName(), processState);
            this.mAssociations.put(str, associationState2);
            return associationState2;
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, long j2, int i) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1138166333441L, this.mPackageName);
            protoOutputStream.write(1120986464258L, this.mUid);
            protoOutputStream.write(1112396529667L, this.mVersionCode);
            if ((i & 2) != 0) {
                for (int i2 = 0; i2 < this.mProcesses.size(); i2++) {
                    this.mProcesses.valueAt(i2).dumpDebug(protoOutputStream, 2246267895812L, this.mProcesses.keyAt(i2), this.mUid, j2);
                }
            }
            if ((i & 4) != 0) {
                for (int i3 = 0; i3 < this.mServices.size(); i3++) {
                    this.mServices.valueAt(i3).dumpDebug(protoOutputStream, 2246267895813L, j2);
                }
            }
            if ((i & 8) != 0) {
                for (int i4 = 0; i4 < this.mAssociations.size(); i4++) {
                    this.mAssociations.valueAt(i4).dumpDebug(protoOutputStream, 2246267895814L, j2);
                }
            }
            protoOutputStream.end(start);
        }
    }

    public static final class ProcessDataCollection {
        public long avgPss;
        public long avgRss;
        public long avgUss;
        public long maxPss;
        public long maxRss;
        public long maxUss;
        final int[] memStates;
        public long minPss;
        public long minRss;
        public long minUss;
        public long numPss;
        final int[] procStates;
        final int[] screenStates;
        public long totalTime;

        public ProcessDataCollection(int[] iArr, int[] iArr2, int[] iArr3) {
            this.screenStates = iArr;
            this.memStates = iArr2;
            this.procStates = iArr3;
        }

        void print(java.io.PrintWriter printWriter, long j, boolean z) {
            if (this.totalTime > j) {
                printWriter.print("*");
            }
            com.android.internal.app.procstats.DumpUtils.printPercent(printWriter, this.totalTime / j);
            if (this.numPss > 0) {
                printWriter.print(" (");
                android.util.DebugUtils.printSizeValue(printWriter, this.minPss * 1024);
                printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                android.util.DebugUtils.printSizeValue(printWriter, this.avgPss * 1024);
                printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                android.util.DebugUtils.printSizeValue(printWriter, this.maxPss * 1024);
                printWriter.print("/");
                android.util.DebugUtils.printSizeValue(printWriter, this.minUss * 1024);
                printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                android.util.DebugUtils.printSizeValue(printWriter, this.avgUss * 1024);
                printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                android.util.DebugUtils.printSizeValue(printWriter, this.maxUss * 1024);
                printWriter.print("/");
                android.util.DebugUtils.printSizeValue(printWriter, this.minRss * 1024);
                printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                android.util.DebugUtils.printSizeValue(printWriter, this.avgRss * 1024);
                printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                android.util.DebugUtils.printSizeValue(printWriter, this.maxRss * 1024);
                if (z) {
                    printWriter.print(" over ");
                    printWriter.print(this.numPss);
                }
                printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        }
    }

    public static class TotalMemoryUseCollection {
        public boolean hasSwappedOutPss;
        final int[] memStates;
        final int[] screenStates;
        public double sysMemCachedWeight;
        public double sysMemFreeWeight;
        public double sysMemKernelWeight;
        public double sysMemNativeWeight;
        public int sysMemSamples;
        public double sysMemZRamWeight;
        public long totalTime;
        public long[] processStatePss = new long[16];
        public double[] processStateWeight = new double[16];
        public long[] processStateTime = new long[16];
        public int[] processStateSamples = new int[16];
        public long[] sysMemUsage = new long[16];

        public TotalMemoryUseCollection(int[] iArr, int[] iArr2) {
            this.screenStates = iArr;
            this.memStates = iArr2;
        }
    }
}
