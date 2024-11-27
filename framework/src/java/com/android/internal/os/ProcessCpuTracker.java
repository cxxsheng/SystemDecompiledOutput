package com.android.internal.os;

/* loaded from: classes4.dex */
public class ProcessCpuTracker {
    private static final boolean DEBUG = false;
    static final int PROCESS_FULL_STAT_MAJOR_FAULTS = 2;
    static final int PROCESS_FULL_STAT_MINOR_FAULTS = 1;
    static final int PROCESS_FULL_STAT_STIME = 4;
    static final int PROCESS_FULL_STAT_UTIME = 3;
    static final int PROCESS_FULL_STAT_VSIZE = 5;
    static final int PROCESS_SCHEDSTAT_CPU_DELAY_TIME = 1;
    static final int PROCESS_SCHEDSTAT_CPU_TIME = 0;
    static final int PROCESS_STAT_MAJOR_FAULTS = 1;
    static final int PROCESS_STAT_MINOR_FAULTS = 0;
    static final int PROCESS_STAT_STIME = 3;
    static final int PROCESS_STAT_UTIME = 2;
    private static final java.lang.String TAG = "ProcessCpuTracker";
    private static final boolean localLOGV = false;
    private long mBaseIdleTime;
    private long mBaseIoWaitTime;
    private long mBaseIrqTime;
    private long mBaseSoftIrqTime;
    private long mBaseSystemTime;
    private long mBaseUserTime;
    private int[] mCurPids;
    private int[] mCurThreadPids;
    private long mCurrentSampleRealTime;
    private long mCurrentSampleTime;
    private long mCurrentSampleWallTime;
    private final boolean mIncludeThreads;
    private long mLastSampleRealTime;
    private long mLastSampleTime;
    private long mLastSampleWallTime;
    private int mRelIdleTime;
    private int mRelIoWaitTime;
    private int mRelIrqTime;
    private int mRelSoftIrqTime;
    private boolean mRelStatsAreGood;
    private int mRelSystemTime;
    private int mRelUserTime;
    private boolean mWorkingProcsSorted;
    private static final int[] PROCESS_STATS_FORMAT = {32, 544, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224};
    private static final int[] PROCESS_FULL_STATS_FORMAT = {32, 4640, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224, 32, 32, 32, 32, 32, 32, 32, 8224};
    private static final int[] PROCESS_SCHEDSTATS_FORMAT = {8224, 8224};
    private static final int[] SYSTEM_CPU_FORMAT = {288, 8224, 8224, 8224, 8224, 8224, 8224, 8224};
    private static final int[] LOAD_AVERAGE_FORMAT = {16416, 16416, 16416};
    private static final java.util.Comparator<com.android.internal.os.ProcessCpuTracker.Stats> sLoadComparator = new java.util.Comparator<com.android.internal.os.ProcessCpuTracker.Stats>() { // from class: com.android.internal.os.ProcessCpuTracker.1
        @Override // java.util.Comparator
        public final int compare(com.android.internal.os.ProcessCpuTracker.Stats stats, com.android.internal.os.ProcessCpuTracker.Stats stats2) {
            int i = stats.rel_utime + stats.rel_stime;
            int i2 = stats2.rel_utime + stats2.rel_stime;
            if (i != i2) {
                return i > i2 ? -1 : 1;
            }
            if (stats.added != stats2.added) {
                return stats.added ? -1 : 1;
            }
            if (stats.removed != stats2.removed) {
                return stats.added ? -1 : 1;
            }
            return 0;
        }
    };
    private final long[] mProcessStatsData = new long[4];
    private final java.lang.String[] mProcessFullStatsStringData = new java.lang.String[6];
    private final long[] mProcessFullStatsData = new long[6];
    private final long[] mSystemCpuData = new long[7];
    private final float[] mLoadAverageData = new float[3];
    private float mLoad1 = 0.0f;
    private float mLoad5 = 0.0f;
    private float mLoad15 = 0.0f;
    private final java.util.ArrayList<com.android.internal.os.ProcessCpuTracker.Stats> mProcStats = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.internal.os.ProcessCpuTracker.Stats> mWorkingProcs = new java.util.ArrayList<>();
    private boolean mFirst = true;
    private final long mJiffyMillis = 1000 / android.system.Os.sysconf(android.system.OsConstants._SC_CLK_TCK);

    public interface FilterStats {
        boolean needed(com.android.internal.os.ProcessCpuTracker.Stats stats);
    }

    public static class Stats {
        public boolean active;
        public boolean added;
        public java.lang.String baseName;
        public long base_majfaults;
        public long base_minfaults;
        public long base_stime;
        public long base_uptime;
        public long base_utime;
        public android.os.BatteryStats.Uid.Proc batteryStats;
        final java.lang.String cmdlineFile;
        public boolean interesting;
        public java.lang.String name;
        public int nameWidth;
        public final int pid;
        public int rel_majfaults;
        public int rel_minfaults;
        public int rel_stime;
        public long rel_uptime;
        public int rel_utime;
        public boolean removed;
        final java.lang.String statFile;
        final java.util.ArrayList<com.android.internal.os.ProcessCpuTracker.Stats> threadStats;
        final java.lang.String threadsDir;
        public final int uid;
        public long vsize;
        public boolean working;
        final java.util.ArrayList<com.android.internal.os.ProcessCpuTracker.Stats> workingThreads;

        Stats(int i, int i2, boolean z) {
            this.pid = i;
            if (i2 < 0) {
                java.io.File file = new java.io.File("/proc", java.lang.Integer.toString(this.pid));
                this.uid = getUid(file.toString());
                this.statFile = new java.io.File(file, "stat").toString();
                this.cmdlineFile = new java.io.File(file, "cmdline").toString();
                this.threadsDir = new java.io.File(file, "task").toString();
                if (z) {
                    this.threadStats = new java.util.ArrayList<>();
                    this.workingThreads = new java.util.ArrayList<>();
                    return;
                } else {
                    this.threadStats = null;
                    this.workingThreads = null;
                    return;
                }
            }
            java.io.File file2 = new java.io.File(new java.io.File(new java.io.File("/proc", java.lang.Integer.toString(i2)), "task"), java.lang.Integer.toString(this.pid));
            this.uid = getUid(file2.toString());
            this.statFile = new java.io.File(file2, "stat").toString();
            this.cmdlineFile = null;
            this.threadsDir = null;
            this.threadStats = null;
            this.workingThreads = null;
        }

        private static int getUid(java.lang.String str) {
            try {
                return android.system.Os.stat(str).st_uid;
            } catch (android.system.ErrnoException e) {
                android.util.Slog.w(com.android.internal.os.ProcessCpuTracker.TAG, "Failed to stat(" + str + "): " + e);
                return -1;
            }
        }
    }

    public ProcessCpuTracker(boolean z) {
        this.mIncludeThreads = z;
    }

    public void onLoadChanged(float f, float f2, float f3) {
    }

    public int onMeasureProcessName(java.lang.String str) {
        return 0;
    }

    public void init() {
        this.mFirst = true;
        update();
    }

    public void update() {
        synchronized (this) {
            updateLocked();
        }
    }

    private void updateLocked() {
        long j;
        long j2;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long[] jArr = this.mSystemCpuData;
        if (android.os.Process.readProcFile("/proc/stat", SYSTEM_CPU_FORMAT, null, jArr, null)) {
            long j3 = (jArr[0] + jArr[1]) * this.mJiffyMillis;
            long j4 = jArr[2] * this.mJiffyMillis;
            long j5 = jArr[3] * this.mJiffyMillis;
            long j6 = jArr[4] * this.mJiffyMillis;
            j2 = currentTimeMillis;
            long j7 = this.mJiffyMillis * jArr[5];
            j = elapsedRealtime;
            long j8 = this.mJiffyMillis * jArr[6];
            this.mRelUserTime = (int) (j3 - this.mBaseUserTime);
            this.mRelSystemTime = (int) (j4 - this.mBaseSystemTime);
            this.mRelIoWaitTime = (int) (j6 - this.mBaseIoWaitTime);
            this.mRelIrqTime = (int) (j7 - this.mBaseIrqTime);
            this.mRelSoftIrqTime = (int) (j8 - this.mBaseSoftIrqTime);
            this.mRelIdleTime = (int) (j5 - this.mBaseIdleTime);
            this.mRelStatsAreGood = true;
            this.mBaseUserTime = j3;
            this.mBaseSystemTime = j4;
            this.mBaseIoWaitTime = j6;
            this.mBaseIrqTime = j7;
            this.mBaseSoftIrqTime = j8;
            this.mBaseIdleTime = j5;
        } else {
            j = elapsedRealtime;
            j2 = currentTimeMillis;
        }
        this.mLastSampleTime = this.mCurrentSampleTime;
        this.mCurrentSampleTime = uptimeMillis;
        this.mLastSampleRealTime = this.mCurrentSampleRealTime;
        this.mCurrentSampleRealTime = j;
        this.mLastSampleWallTime = this.mCurrentSampleWallTime;
        this.mCurrentSampleWallTime = j2;
        android.os.StrictMode.ThreadPolicy allowThreadDiskReads = android.os.StrictMode.allowThreadDiskReads();
        try {
            this.mCurPids = collectStats("/proc", -1, this.mFirst, this.mCurPids, this.mProcStats);
            android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
            float[] fArr = this.mLoadAverageData;
            if (android.os.Process.readProcFile("/proc/loadavg", LOAD_AVERAGE_FORMAT, null, null, fArr)) {
                float f = fArr[0];
                float f2 = fArr[1];
                float f3 = fArr[2];
                if (f != this.mLoad1 || f2 != this.mLoad5 || f3 != this.mLoad15) {
                    this.mLoad1 = f;
                    this.mLoad5 = f2;
                    this.mLoad15 = f3;
                    onLoadChanged(f, f2, f3);
                }
            }
            this.mWorkingProcsSorted = false;
            this.mFirst = false;
        } catch (java.lang.Throwable th) {
            android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
            throw th;
        }
    }

    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r10v3 */
    private int[] collectStats(java.lang.String str, int i, boolean z, int[] iArr, java.util.ArrayList<com.android.internal.os.ProcessCpuTracker.Stats> arrayList) {
        int i2;
        int i3;
        int i4;
        long j;
        long j2;
        long j3;
        long j4;
        int[] pids = android.os.Process.getPids(str, iArr);
        ?? r10 = 0;
        int length = pids == null ? 0 : pids.length;
        int size = arrayList.size();
        int i5 = 0;
        int i6 = 0;
        while (i6 < length) {
            int i7 = pids[i6];
            if (i7 < 0) {
                break;
            }
            com.android.internal.os.ProcessCpuTracker.Stats stats = i5 < size ? arrayList.get(i5) : null;
            if (stats == null || stats.pid != i7) {
                i2 = length;
                if (stats == null || stats.pid > i7) {
                    com.android.internal.os.ProcessCpuTracker.Stats stats2 = new com.android.internal.os.ProcessCpuTracker.Stats(i7, i, this.mIncludeThreads);
                    arrayList.add(i5, stats2);
                    i3 = i5 + 1;
                    size++;
                    java.lang.String[] strArr = this.mProcessFullStatsStringData;
                    long[] jArr = this.mProcessFullStatsData;
                    stats2.base_uptime = android.os.SystemClock.uptimeMillis();
                    if (android.os.Process.readProcFile(stats2.statFile.toString(), PROCESS_FULL_STATS_FORMAT, strArr, jArr, null)) {
                        stats2.vsize = jArr[5];
                        stats2.interesting = true;
                        stats2.baseName = strArr[0];
                        stats2.base_minfaults = jArr[1];
                        stats2.base_majfaults = jArr[2];
                        stats2.base_utime = jArr[3] * this.mJiffyMillis;
                        stats2.base_stime = jArr[4] * this.mJiffyMillis;
                    } else {
                        android.util.Slog.w(TAG, "Skipping unknown process pid " + i7);
                        stats2.baseName = "<unknown>";
                        stats2.base_stime = 0L;
                        stats2.base_utime = 0L;
                        stats2.base_majfaults = 0L;
                        stats2.base_minfaults = 0L;
                    }
                    if (i < 0) {
                        getName(stats2, stats2.cmdlineFile);
                        if (stats2.threadStats != null) {
                            this.mCurThreadPids = collectStats(stats2.threadsDir, i7, true, this.mCurThreadPids, stats2.threadStats);
                        }
                    } else if (stats2.interesting) {
                        stats2.name = stats2.baseName;
                        stats2.nameWidth = onMeasureProcessName(stats2.name);
                    }
                    stats2.rel_utime = 0;
                    stats2.rel_stime = 0;
                    stats2.rel_minfaults = 0;
                    stats2.rel_majfaults = 0;
                    i4 = 1;
                    stats2.added = true;
                    if (!z && stats2.interesting) {
                        stats2.working = true;
                    }
                } else {
                    stats.rel_utime = 0;
                    stats.rel_stime = 0;
                    stats.rel_minfaults = 0;
                    stats.rel_majfaults = 0;
                    stats.removed = true;
                    stats.working = true;
                    arrayList.remove(i5);
                    size--;
                    i6--;
                    i3 = i5;
                    i4 = 1;
                }
            } else {
                stats.added = r10;
                stats.working = r10;
                int i8 = i5 + 1;
                if (!stats.interesting) {
                    i2 = length;
                } else {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    long[] jArr2 = this.mProcessStatsData;
                    if (android.os.Process.readProcFile(stats.statFile.toString(), PROCESS_STATS_FORMAT, null, jArr2, null)) {
                        long j5 = jArr2[r10];
                        long j6 = jArr2[1];
                        i2 = length;
                        long j7 = this.mJiffyMillis * jArr2[2];
                        com.android.internal.os.ProcessCpuTracker.Stats stats3 = stats;
                        long j8 = jArr2[3] * this.mJiffyMillis;
                        if (j7 == stats3.base_utime && j8 == stats3.base_stime) {
                            stats3.rel_utime = 0;
                            stats3.rel_stime = 0;
                            stats3.rel_minfaults = 0;
                            stats3.rel_majfaults = 0;
                            if (stats3.active) {
                                stats3.active = false;
                            }
                        } else {
                            if (!stats3.active) {
                                stats3.active = true;
                            }
                            if (i < 0) {
                                getName(stats3, stats3.cmdlineFile);
                                if (stats3.threadStats != null) {
                                    j = j8;
                                    j2 = uptimeMillis;
                                    j3 = j5;
                                    j4 = j6;
                                    this.mCurThreadPids = collectStats(stats3.threadsDir, i7, false, this.mCurThreadPids, stats3.threadStats);
                                } else {
                                    j = j8;
                                    j2 = uptimeMillis;
                                    j3 = j5;
                                    j4 = j6;
                                }
                            } else {
                                j = j8;
                                j2 = uptimeMillis;
                                j3 = j5;
                                j4 = j6;
                            }
                            long j9 = j2;
                            stats3.rel_uptime = j9 - stats3.base_uptime;
                            stats3.base_uptime = j9;
                            stats3.rel_utime = (int) (j7 - stats3.base_utime);
                            long j10 = j;
                            stats3.rel_stime = (int) (j10 - stats3.base_stime);
                            stats3.base_utime = j7;
                            stats3.base_stime = j10;
                            long j11 = j3;
                            stats3.rel_minfaults = (int) (j11 - stats3.base_minfaults);
                            long j12 = j4;
                            stats3.rel_majfaults = (int) (j12 - stats3.base_majfaults);
                            stats3.base_minfaults = j11;
                            stats3.base_majfaults = j12;
                            stats3.working = true;
                        }
                    } else {
                        i2 = length;
                    }
                }
                i3 = i8;
                i4 = 1;
            }
            i6 += i4;
            i5 = i3;
            length = i2;
            r10 = 0;
        }
        while (i5 < size) {
            com.android.internal.os.ProcessCpuTracker.Stats stats4 = arrayList.get(i5);
            stats4.rel_utime = 0;
            stats4.rel_stime = 0;
            stats4.rel_minfaults = 0;
            stats4.rel_majfaults = 0;
            stats4.removed = true;
            stats4.working = true;
            arrayList.remove(i5);
            size--;
        }
        return pids;
    }

    public long getCpuTimeForPid(int i) {
        long[] jArr = new long[4];
        if (android.os.Process.readProcFile("/proc/" + i + "/stat", PROCESS_STATS_FORMAT, null, jArr, null)) {
            return (jArr[2] + jArr[3]) * this.mJiffyMillis;
        }
        return 0L;
    }

    public long getCpuDelayTimeForPid(int i) {
        long[] jArr = new long[4];
        if (android.os.Process.readProcFile("/proc/" + i + "/schedstat", PROCESS_SCHEDSTATS_FORMAT, null, jArr, null)) {
            return jArr[1] / 1000000;
        }
        return 0L;
    }

    public final int getLastUserTime() {
        return this.mRelUserTime;
    }

    public final int getLastSystemTime() {
        return this.mRelSystemTime;
    }

    public final int getLastIoWaitTime() {
        return this.mRelIoWaitTime;
    }

    public final int getLastIrqTime() {
        return this.mRelIrqTime;
    }

    public final int getLastSoftIrqTime() {
        return this.mRelSoftIrqTime;
    }

    public final int getLastIdleTime() {
        return this.mRelIdleTime;
    }

    public final boolean hasGoodLastStats() {
        return this.mRelStatsAreGood;
    }

    public final float getTotalCpuPercent() {
        int i = this.mRelUserTime + this.mRelSystemTime + this.mRelIrqTime + this.mRelIdleTime;
        if (i <= 0) {
            return 0.0f;
        }
        return (((this.mRelUserTime + this.mRelSystemTime) + this.mRelIrqTime) * 100.0f) / i;
    }

    final void buildWorkingProcs() {
        if (!this.mWorkingProcsSorted) {
            this.mWorkingProcs.clear();
            int size = this.mProcStats.size();
            for (int i = 0; i < size; i++) {
                com.android.internal.os.ProcessCpuTracker.Stats stats = this.mProcStats.get(i);
                if (stats.working) {
                    this.mWorkingProcs.add(stats);
                    if (stats.threadStats != null && stats.threadStats.size() > 1) {
                        stats.workingThreads.clear();
                        int size2 = stats.threadStats.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            com.android.internal.os.ProcessCpuTracker.Stats stats2 = stats.threadStats.get(i2);
                            if (stats2.working) {
                                stats.workingThreads.add(stats2);
                            }
                        }
                        java.util.Collections.sort(stats.workingThreads, sLoadComparator);
                    }
                }
            }
            java.util.Collections.sort(this.mWorkingProcs, sLoadComparator);
            this.mWorkingProcsSorted = true;
        }
    }

    public final int countStats() {
        return this.mProcStats.size();
    }

    public final com.android.internal.os.ProcessCpuTracker.Stats getStats(int i) {
        return this.mProcStats.get(i);
    }

    public final java.util.List<com.android.internal.os.ProcessCpuTracker.Stats> getStats(com.android.internal.os.ProcessCpuTracker.FilterStats filterStats) {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mProcStats.size());
        int size = this.mProcStats.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.os.ProcessCpuTracker.Stats stats = this.mProcStats.get(i);
            if (filterStats.needed(stats)) {
                arrayList.add(stats);
            }
        }
        return arrayList;
    }

    public final int countWorkingStats() {
        buildWorkingProcs();
        return this.mWorkingProcs.size();
    }

    public final com.android.internal.os.ProcessCpuTracker.Stats getWorkingStats(int i) {
        return this.mWorkingProcs.get(i);
    }

    public final void dumpProto(java.io.FileDescriptor fileDescriptor) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        long start = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1108101562369L, this.mLoad1);
        protoOutputStream.write(1108101562370L, this.mLoad5);
        protoOutputStream.write(1108101562371L, this.mLoad15);
        protoOutputStream.end(start);
        buildWorkingProcs();
        protoOutputStream.write(1112396529666L, uptimeMillis);
        protoOutputStream.write(1112396529667L, this.mLastSampleTime);
        protoOutputStream.write(1112396529668L, this.mCurrentSampleTime);
        protoOutputStream.write(1112396529669L, this.mLastSampleRealTime);
        protoOutputStream.write(1112396529670L, this.mCurrentSampleRealTime);
        protoOutputStream.write(1112396529671L, this.mLastSampleWallTime);
        protoOutputStream.write(1112396529672L, this.mCurrentSampleWallTime);
        protoOutputStream.write(1120986464265L, this.mRelUserTime);
        protoOutputStream.write(1120986464266L, this.mRelSystemTime);
        protoOutputStream.write(1120986464267L, this.mRelIoWaitTime);
        protoOutputStream.write(1120986464268L, this.mRelIrqTime);
        protoOutputStream.write(1120986464269L, this.mRelSoftIrqTime);
        protoOutputStream.write(1120986464270L, this.mRelIdleTime);
        protoOutputStream.write(1120986464271L, this.mRelUserTime + this.mRelSystemTime + this.mRelIoWaitTime + this.mRelIrqTime + this.mRelSoftIrqTime + this.mRelIdleTime);
        java.util.Iterator<com.android.internal.os.ProcessCpuTracker.Stats> it = this.mWorkingProcs.iterator();
        while (it.hasNext()) {
            com.android.internal.os.ProcessCpuTracker.Stats next = it.next();
            dumpProcessCpuProto(protoOutputStream, next, null);
            if (!next.removed && next.workingThreads != null) {
                java.util.Iterator<com.android.internal.os.ProcessCpuTracker.Stats> it2 = next.workingThreads.iterator();
                while (it2.hasNext()) {
                    dumpProcessCpuProto(protoOutputStream, it2.next(), next);
                }
            }
        }
        protoOutputStream.flush();
    }

    private static void dumpProcessCpuProto(android.util.proto.ProtoOutputStream protoOutputStream, com.android.internal.os.ProcessCpuTracker.Stats stats, com.android.internal.os.ProcessCpuTracker.Stats stats2) {
        long start = protoOutputStream.start(2246267895824L);
        protoOutputStream.write(1120986464257L, stats.uid);
        protoOutputStream.write(1120986464258L, stats.pid);
        protoOutputStream.write(1138166333443L, stats.name);
        protoOutputStream.write(1133871366148L, stats.added);
        protoOutputStream.write(1133871366149L, stats.removed);
        protoOutputStream.write(1120986464262L, stats.rel_uptime);
        protoOutputStream.write(1120986464263L, stats.rel_utime);
        protoOutputStream.write(1120986464264L, stats.rel_stime);
        protoOutputStream.write(1120986464265L, stats.rel_minfaults);
        protoOutputStream.write(1120986464266L, stats.rel_majfaults);
        if (stats2 != null) {
            protoOutputStream.write(1120986464267L, stats2.pid);
        }
        protoOutputStream.end(start);
    }

    public final java.lang.String printCurrentLoad() {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter((java.io.Writer) stringWriter, false, 128);
        fastPrintWriter.print("Load: ");
        fastPrintWriter.print(this.mLoad1);
        fastPrintWriter.print(" / ");
        fastPrintWriter.print(this.mLoad5);
        fastPrintWriter.print(" / ");
        fastPrintWriter.println(this.mLoad15);
        fastPrintWriter.flush();
        return stringWriter.toString();
    }

    public final java.lang.String printCurrentState(long j) {
        return printCurrentState(j, Integer.MAX_VALUE);
    }

    public final java.lang.String printCurrentState(long j, int i) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        buildWorkingProcs();
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        int i2 = 0;
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter((java.io.Writer) stringWriter, false, 1024);
        fastPrintWriter.print("CPU usage from ");
        if (j > this.mLastSampleTime) {
            fastPrintWriter.print(j - this.mLastSampleTime);
            fastPrintWriter.print("ms to ");
            fastPrintWriter.print(j - this.mCurrentSampleTime);
            fastPrintWriter.print("ms ago");
        } else {
            fastPrintWriter.print(this.mLastSampleTime - j);
            fastPrintWriter.print("ms to ");
            fastPrintWriter.print(this.mCurrentSampleTime - j);
            fastPrintWriter.print("ms later");
        }
        fastPrintWriter.print(" (");
        fastPrintWriter.print(simpleDateFormat.format(new java.util.Date(this.mLastSampleWallTime)));
        fastPrintWriter.print(" to ");
        fastPrintWriter.print(simpleDateFormat.format(new java.util.Date(this.mCurrentSampleWallTime)));
        fastPrintWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        long j2 = this.mCurrentSampleTime - this.mLastSampleTime;
        long j3 = this.mCurrentSampleRealTime - this.mLastSampleRealTime;
        long j4 = j3 > 0 ? (j2 * 100) / j3 : 0L;
        if (j4 != 100) {
            fastPrintWriter.print(" with ");
            fastPrintWriter.print(j4);
            fastPrintWriter.print("% awake");
        }
        fastPrintWriter.println(":");
        int i3 = this.mRelUserTime + this.mRelSystemTime + this.mRelIoWaitTime + this.mRelIrqTime + this.mRelSoftIrqTime + this.mRelIdleTime;
        int min = java.lang.Math.min(i, this.mWorkingProcs.size());
        int i4 = 0;
        while (i4 < min) {
            com.android.internal.os.ProcessCpuTracker.Stats stats = this.mWorkingProcs.get(i4);
            int i5 = i4;
            int i6 = min;
            int i7 = i2;
            printProcessCPU(fastPrintWriter, stats.added ? " +" : stats.removed ? " -" : "  ", stats.pid, stats.name, (int) stats.rel_uptime, stats.rel_utime, stats.rel_stime, 0, 0, 0, stats.rel_minfaults, stats.rel_majfaults);
            com.android.internal.os.ProcessCpuTracker.Stats stats2 = stats;
            if (!stats2.removed && stats2.workingThreads != null) {
                int size = stats2.workingThreads.size();
                int i8 = i7;
                while (i8 < size) {
                    com.android.internal.os.ProcessCpuTracker.Stats stats3 = stats2.workingThreads.get(i8);
                    printProcessCPU(fastPrintWriter, stats3.added ? "   +" : stats3.removed ? "   -" : "    ", stats3.pid, stats3.name, (int) stats2.rel_uptime, stats3.rel_utime, stats3.rel_stime, 0, 0, 0, 0, 0);
                    i8++;
                    size = size;
                    stats2 = stats2;
                }
            }
            i4 = i5 + 1;
            min = i6;
            i2 = i7;
        }
        printProcessCPU(fastPrintWriter, "", -1, "TOTAL", i3, this.mRelUserTime, this.mRelSystemTime, this.mRelIoWaitTime, this.mRelIrqTime, this.mRelSoftIrqTime, 0, 0);
        fastPrintWriter.flush();
        return stringWriter.toString();
    }

    private void printRatio(java.io.PrintWriter printWriter, long j, long j2) {
        long j3 = (j * 1000) / j2;
        long j4 = j3 / 10;
        printWriter.print(j4);
        if (j4 < 10) {
            long j5 = j3 - (j4 * 10);
            if (j5 != 0) {
                printWriter.print('.');
                printWriter.print(j5);
            }
        }
    }

    private void printProcessCPU(java.io.PrintWriter printWriter, java.lang.String str, int i, java.lang.String str2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        printWriter.print(str);
        long j = i2 == 0 ? 1 : i2;
        printRatio(printWriter, i3 + i4 + i5 + i6 + i7, j);
        printWriter.print("% ");
        if (i >= 0) {
            printWriter.print(i);
            printWriter.print("/");
        }
        printWriter.print(str2);
        printWriter.print(": ");
        printRatio(printWriter, i3, j);
        printWriter.print("% user + ");
        printRatio(printWriter, i4, j);
        printWriter.print("% kernel");
        if (i5 > 0) {
            printWriter.print(" + ");
            printRatio(printWriter, i5, j);
            printWriter.print("% iowait");
        }
        if (i6 > 0) {
            printWriter.print(" + ");
            printRatio(printWriter, i6, j);
            printWriter.print("% irq");
        }
        if (i7 > 0) {
            printWriter.print(" + ");
            printRatio(printWriter, i7, j);
            printWriter.print("% softirq");
        }
        if (i8 > 0 || i9 > 0) {
            printWriter.print(" / faults:");
            if (i8 > 0) {
                printWriter.print(" ");
                printWriter.print(i8);
                printWriter.print(" minor");
            }
            if (i9 > 0) {
                printWriter.print(" ");
                printWriter.print(i9);
                printWriter.print(" major");
            }
        }
        printWriter.println();
    }

    private void getName(com.android.internal.os.ProcessCpuTracker.Stats stats, java.lang.String str) {
        java.lang.String str2 = stats.name;
        if (stats.name == null || stats.name.equals("app_process") || stats.name.equals("<pre-initialized>") || stats.name.equals("usap32") || stats.name.equals("usap64")) {
            java.lang.String readTerminatedProcFile = com.android.internal.os.ProcStatsUtil.readTerminatedProcFile(str, (byte) 0);
            if (readTerminatedProcFile != null && readTerminatedProcFile.length() > 1) {
                int lastIndexOf = readTerminatedProcFile.lastIndexOf("/");
                str2 = (lastIndexOf <= 0 || lastIndexOf >= readTerminatedProcFile.length() - 1) ? readTerminatedProcFile : readTerminatedProcFile.substring(lastIndexOf + 1);
            }
            if (str2 == null) {
                str2 = stats.baseName;
            }
        }
        if (stats.name == null || !str2.equals(stats.name)) {
            stats.name = str2;
            stats.nameWidth = onMeasureProcessName(stats.name);
        }
    }
}
