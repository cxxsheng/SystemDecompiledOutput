package com.android.server.cpu;

/* loaded from: classes.dex */
public final class CpuInfoReader {
    private static final java.lang.String AFFECTED_CPUS_FILE = "affected_cpus";
    private static final java.lang.String CPUFREQ_DIR_PATH = "/sys/devices/system/cpu/cpufreq";
    private static final java.lang.String CPUSET_BACKGROUND_DIR = "background";
    private static final java.lang.String CPUSET_DIR_PATH = "/dev/cpuset";
    private static final java.lang.String CPUSET_TOP_APP_DIR = "top-app";
    private static final java.lang.String CPUS_FILE = "cpus";
    private static final java.lang.String CUR_SCALING_FREQ_FILE = "scaling_cur_freq";
    static final int FLAG_CPUSET_CATEGORY_BACKGROUND = 2;
    static final int FLAG_CPUSET_CATEGORY_TOP_APP = 1;
    private static final java.lang.String MAX_SCALING_FREQ_FILE = "scaling_max_freq";
    private static final long MIN_READ_INTERVAL_MILLISECONDS = 500;
    private static final java.lang.String POLICY_DIR_PREFIX = "policy";
    private static final java.lang.String PROC_STAT_FILE_PATH = "/proc/stat";
    private static final java.lang.String RELATED_CPUS_FILE = "related_cpus";
    private static final java.lang.String TIME_IN_STATE_FILE = "stats/time_in_state";
    private java.io.File mCpuFreqDir;
    private final android.util.SparseArray<java.io.File> mCpuFreqPolicyDirsById;
    private final android.util.SparseIntArray mCpusetCategoriesByCpus;
    private final java.io.File mCpusetDir;
    private android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuUsageStats> mCumulativeCpuUsageStats;
    private boolean mHasTimeInStateFile;
    private boolean mIsEnabled;
    private android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuInfo> mLastReadCpuInfos;
    private long mLastReadUptimeMillis;
    private final long mMinReadIntervalMillis;
    private java.io.File mProcStatFile;
    private final android.util.SparseArray<com.android.server.cpu.CpuInfoReader.StaticPolicyInfo> mStaticPolicyInfoById;
    private final android.util.SparseArray<android.util.LongSparseLongArray> mTimeInStateByPolicyId;
    private static final java.util.regex.Pattern PROC_STAT_PATTERN = java.util.regex.Pattern.compile("cpu(?<core>[0-9]+)\\s(?<userClockTicks>[0-9]+)\\s(?<niceClockTicks>[0-9]+)\\s(?<sysClockTicks>[0-9]+)\\s(?<idleClockTicks>[0-9]+)\\s(?<iowaitClockTicks>[0-9]+)\\s(?<irqClockTicks>[0-9]+)\\s(?<softirqClockTicks>[0-9]+)\\s(?<stealClockTicks>[0-9]+)\\s(?<guestClockTicks>[0-9]+)\\s(?<guestNiceClockTicks>[0-9]+)");
    private static final java.util.regex.Pattern TIME_IN_STATE_PATTERN = java.util.regex.Pattern.compile("(?<freqKHz>[0-9]+)\\s(?<time>[0-9]+)");
    private static final long MILLIS_PER_CLOCK_TICK = 1000 / android.system.Os.sysconf(android.system.OsConstants._SC_CLK_TCK);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface CpusetCategory {
    }

    public CpuInfoReader() {
        this(new java.io.File(CPUSET_DIR_PATH), new java.io.File(CPUFREQ_DIR_PATH), new java.io.File(PROC_STAT_FILE_PATH), 500L);
    }

    @com.android.internal.annotations.VisibleForTesting
    CpuInfoReader(java.io.File file, java.io.File file2, java.io.File file3, long j) {
        this.mCpusetCategoriesByCpus = new android.util.SparseIntArray();
        this.mCpuFreqPolicyDirsById = new android.util.SparseArray<>();
        this.mStaticPolicyInfoById = new android.util.SparseArray<>();
        this.mTimeInStateByPolicyId = new android.util.SparseArray<>();
        this.mCumulativeCpuUsageStats = new android.util.SparseArray<>();
        this.mCpusetDir = file;
        this.mCpuFreqDir = file2;
        this.mProcStatFile = file3;
        this.mMinReadIntervalMillis = j;
    }

    public boolean init() {
        if (this.mCpuFreqPolicyDirsById.size() > 0) {
            com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Ignoring duplicate CpuInfoReader init request");
            return this.mIsEnabled;
        }
        java.io.File[] listFiles = this.mCpuFreqDir.listFiles(new java.io.FileFilter() { // from class: com.android.server.cpu.CpuInfoReader$$ExternalSyntheticLambda1
            @Override // java.io.FileFilter
            public final boolean accept(java.io.File file) {
                boolean lambda$init$0;
                lambda$init$0 = com.android.server.cpu.CpuInfoReader.lambda$init$0(file);
                return lambda$init$0;
            }
        });
        if (listFiles == null || listFiles.length == 0) {
            com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Missing CPU frequency policy directories at %s", this.mCpuFreqDir.getAbsolutePath());
            return false;
        }
        populateCpuFreqPolicyDirsById(listFiles);
        if (this.mCpuFreqPolicyDirsById.size() == 0) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Failed to parse CPU frequency policy directory paths: %s", java.util.Arrays.toString(listFiles));
            return false;
        }
        readStaticPolicyInfo();
        if (this.mStaticPolicyInfoById.size() == 0) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Failed to read static CPU frequency policy info from policy dirs: %s", java.util.Arrays.toString(listFiles));
            return false;
        }
        if (!this.mProcStatFile.exists()) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Missing proc stat file at %s", this.mProcStatFile.getAbsolutePath());
            return false;
        }
        readCpusetCategories();
        if (this.mCpusetCategoriesByCpus.size() == 0) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Failed to read cpuset information from %s", this.mCpusetDir.getAbsolutePath());
            return false;
        }
        for (int i = 0; i < this.mCpuFreqPolicyDirsById.size() && !this.mHasTimeInStateFile; i++) {
            this.mHasTimeInStateFile |= new java.io.File(this.mCpuFreqPolicyDirsById.valueAt(i), TIME_IN_STATE_FILE).exists();
        }
        if (!this.mHasTimeInStateFile) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Time in state file not available for any cpufreq policy");
        }
        this.mIsEnabled = true;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$init$0(java.io.File file) {
        return file.isDirectory() && file.getName().startsWith(POLICY_DIR_PREFIX);
    }

    @android.annotation.Nullable
    public android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuInfo> readCpuInfos() {
        android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuUsageStats> sparseArray;
        android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuUsageStats> sparseArray2;
        com.android.server.cpu.CpuInfoReader cpuInfoReader = this;
        if (!cpuInfoReader.mIsEnabled) {
            return null;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long j = 0;
        if (cpuInfoReader.mLastReadUptimeMillis > 0 && uptimeMillis - cpuInfoReader.mLastReadUptimeMillis < cpuInfoReader.mMinReadIntervalMillis) {
            com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Skipping reading from device and returning the last read CpuInfos. Last read was %d ms ago, min read interval is %d ms", java.lang.Long.valueOf(uptimeMillis - cpuInfoReader.mLastReadUptimeMillis), java.lang.Long.valueOf(cpuInfoReader.mMinReadIntervalMillis));
            return cpuInfoReader.mLastReadCpuInfos;
        }
        cpuInfoReader.mLastReadUptimeMillis = uptimeMillis;
        cpuInfoReader.mLastReadCpuInfos = null;
        android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuUsageStats> readLatestCpuUsageStats = readLatestCpuUsageStats();
        if (readLatestCpuUsageStats == null || readLatestCpuUsageStats.size() == 0) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Failed to read latest CPU usage stats");
            return null;
        }
        android.util.SparseArray<com.android.server.cpu.CpuInfoReader.DynamicPolicyInfo> readDynamicPolicyInfo = readDynamicPolicyInfo();
        if (readDynamicPolicyInfo.size() == 0) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Failed to read dynamic policy infos");
            return null;
        }
        android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuInfo> sparseArray3 = new android.util.SparseArray<>();
        int i = 0;
        while (i < cpuInfoReader.mStaticPolicyInfoById.size()) {
            int keyAt = cpuInfoReader.mStaticPolicyInfoById.keyAt(i);
            com.android.server.cpu.CpuInfoReader.StaticPolicyInfo valueAt = cpuInfoReader.mStaticPolicyInfoById.valueAt(i);
            com.android.server.cpu.CpuInfoReader.DynamicPolicyInfo dynamicPolicyInfo = readDynamicPolicyInfo.get(keyAt);
            if (dynamicPolicyInfo == null) {
                com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Missing dynamic policy info for policy ID %d", java.lang.Integer.valueOf(keyAt));
                sparseArray = readLatestCpuUsageStats;
            } else {
                if (dynamicPolicyInfo.curCpuFreqKHz == j) {
                    sparseArray = readLatestCpuUsageStats;
                } else if (dynamicPolicyInfo.maxCpuFreqKHz == j) {
                    sparseArray = readLatestCpuUsageStats;
                } else if (dynamicPolicyInfo.curCpuFreqKHz > dynamicPolicyInfo.maxCpuFreqKHz) {
                    com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Current CPU frequency (%d) is greater than maximum CPU frequency (%d) for policy ID (%d). Skipping CPU frequency policy", java.lang.Long.valueOf(dynamicPolicyInfo.curCpuFreqKHz), java.lang.Long.valueOf(dynamicPolicyInfo.maxCpuFreqKHz), java.lang.Integer.valueOf(keyAt));
                    sparseArray = readLatestCpuUsageStats;
                } else {
                    int i2 = 0;
                    while (i2 < valueAt.relatedCpuCores.size()) {
                        int i3 = valueAt.relatedCpuCores.get(i2);
                        com.android.server.cpu.CpuInfoReader.CpuInfo cpuInfo = sparseArray3.get(i3);
                        if (cpuInfo != null) {
                            com.android.server.utils.Slogf.wtf(com.android.server.cpu.CpuMonitorService.TAG, "CPU info already available for the CPU core %d", java.lang.Integer.valueOf(i3));
                            if (cpuInfo.isOnline) {
                                sparseArray2 = readLatestCpuUsageStats;
                                i2++;
                                cpuInfoReader = this;
                                readLatestCpuUsageStats = sparseArray2;
                            }
                        }
                        int i4 = cpuInfoReader.mCpusetCategoriesByCpus.get(i3, -1);
                        if (i4 < 0) {
                            com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Missing cpuset information for the CPU core %d", java.lang.Integer.valueOf(i3));
                            sparseArray2 = readLatestCpuUsageStats;
                        } else {
                            com.android.server.cpu.CpuInfoReader.CpuUsageStats cpuUsageStats = readLatestCpuUsageStats.get(i3);
                            if (dynamicPolicyInfo.affectedCpuCores.indexOf(i3) < 0) {
                                sparseArray3.append(i3, new com.android.server.cpu.CpuInfoReader.CpuInfo(i3, i4, false, 0L, dynamicPolicyInfo.maxCpuFreqKHz, 0L, cpuUsageStats));
                                sparseArray2 = readLatestCpuUsageStats;
                            } else if (cpuUsageStats == null) {
                                com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Missing CPU usage information for online CPU core %d", java.lang.Integer.valueOf(i3));
                                sparseArray2 = readLatestCpuUsageStats;
                            } else {
                                sparseArray2 = readLatestCpuUsageStats;
                                com.android.server.cpu.CpuInfoReader.CpuInfo cpuInfo2 = new com.android.server.cpu.CpuInfoReader.CpuInfo(i3, i4, true, dynamicPolicyInfo.curCpuFreqKHz, dynamicPolicyInfo.maxCpuFreqKHz, dynamicPolicyInfo.avgTimeInStateCpuFreqKHz, cpuUsageStats);
                                sparseArray3.append(i3, cpuInfo2);
                                if (com.android.server.cpu.CpuMonitorService.DEBUG) {
                                    com.android.server.utils.Slogf.d(com.android.server.cpu.CpuMonitorService.TAG, "Added %s for CPU core %d", cpuInfo2, java.lang.Integer.valueOf(i3));
                                }
                            }
                        }
                        i2++;
                        cpuInfoReader = this;
                        readLatestCpuUsageStats = sparseArray2;
                    }
                    sparseArray = readLatestCpuUsageStats;
                }
                com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Current and maximum CPU frequency information mismatch/missing for policy ID %d", java.lang.Integer.valueOf(keyAt));
            }
            i++;
            j = 0;
            cpuInfoReader = this;
            readLatestCpuUsageStats = sparseArray;
        }
        this.mLastReadCpuInfos = sparseArray3;
        return sparseArray3;
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.printf("*%s*\n", new java.lang.Object[]{com.android.server.cpu.CpuInfoReader.class.getSimpleName()});
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("mCpusetDir = %s\n", new java.lang.Object[]{this.mCpusetDir.getAbsolutePath()});
        indentingPrintWriter.printf("mCpuFreqDir = %s\n", new java.lang.Object[]{this.mCpuFreqDir.getAbsolutePath()});
        indentingPrintWriter.printf("mProcStatFile = %s\n", new java.lang.Object[]{this.mProcStatFile.getAbsolutePath()});
        indentingPrintWriter.printf("mIsEnabled = %s\n", new java.lang.Object[]{java.lang.Boolean.valueOf(this.mIsEnabled)});
        indentingPrintWriter.printf("mHasTimeInStateFile = %s\n", new java.lang.Object[]{java.lang.Boolean.valueOf(this.mHasTimeInStateFile)});
        indentingPrintWriter.printf("mLastReadUptimeMillis = %d\n", new java.lang.Object[]{java.lang.Long.valueOf(this.mLastReadUptimeMillis)});
        indentingPrintWriter.printf("mMinReadIntervalMillis = %d\n", new java.lang.Object[]{java.lang.Long.valueOf(this.mMinReadIntervalMillis)});
        indentingPrintWriter.printf("Cpuset categories by CPU core:\n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mCpusetCategoriesByCpus.size(); i++) {
            indentingPrintWriter.printf("CPU core id = %d, %s\n", new java.lang.Object[]{java.lang.Integer.valueOf(this.mCpusetCategoriesByCpus.keyAt(i)), toCpusetCategoriesStr(this.mCpusetCategoriesByCpus.valueAt(i))});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Cpu frequency policy directories by policy id:");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < this.mCpuFreqPolicyDirsById.size(); i2++) {
            indentingPrintWriter.printf("Policy id = %d, Dir = %s\n", new java.lang.Object[]{java.lang.Integer.valueOf(this.mCpuFreqPolicyDirsById.keyAt(i2)), this.mCpuFreqPolicyDirsById.valueAt(i2)});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Static cpu frequency policy infos by policy id:");
        indentingPrintWriter.increaseIndent();
        for (int i3 = 0; i3 < this.mStaticPolicyInfoById.size(); i3++) {
            indentingPrintWriter.printf("Policy id = %d, %s\n", new java.lang.Object[]{java.lang.Integer.valueOf(this.mStaticPolicyInfoById.keyAt(i3)), this.mStaticPolicyInfoById.valueAt(i3)});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Cpu time in frequency state by policy id:");
        indentingPrintWriter.increaseIndent();
        for (int i4 = 0; i4 < this.mTimeInStateByPolicyId.size(); i4++) {
            indentingPrintWriter.printf("Policy id = %d, Time(millis) in state by CPU frequency(KHz) = %s\n", new java.lang.Object[]{java.lang.Integer.valueOf(this.mTimeInStateByPolicyId.keyAt(i4)), this.mTimeInStateByPolicyId.valueAt(i4)});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Last read CPU infos:");
        indentingPrintWriter.increaseIndent();
        for (int i5 = 0; i5 < this.mLastReadCpuInfos.size(); i5++) {
            indentingPrintWriter.printf("%s\n", new java.lang.Object[]{this.mLastReadCpuInfos.valueAt(i5)});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Latest cumulative CPU usage stats by CPU core:");
        indentingPrintWriter.increaseIndent();
        for (int i6 = 0; i6 < this.mCumulativeCpuUsageStats.size(); i6++) {
            indentingPrintWriter.printf("CPU core id = %d, %s\n", new java.lang.Object[]{java.lang.Integer.valueOf(this.mCumulativeCpuUsageStats.keyAt(i6)), this.mCumulativeCpuUsageStats.valueAt(i6)});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean setCpuFreqDir(java.io.File file) {
        java.io.File[] listFiles = file.listFiles(new java.io.FileFilter() { // from class: com.android.server.cpu.CpuInfoReader$$ExternalSyntheticLambda0
            @Override // java.io.FileFilter
            public final boolean accept(java.io.File file2) {
                boolean lambda$setCpuFreqDir$1;
                lambda$setCpuFreqDir$1 = com.android.server.cpu.CpuInfoReader.lambda$setCpuFreqDir$1(file2);
                return lambda$setCpuFreqDir$1;
            }
        });
        if (listFiles == null || listFiles.length == 0) {
            com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Failed to set CPU frequency directory. Missing policy directories at %s", file.getAbsolutePath());
            return false;
        }
        populateCpuFreqPolicyDirsById(listFiles);
        int size = this.mCpuFreqPolicyDirsById.size();
        int size2 = this.mStaticPolicyInfoById.size();
        if (size == 0 || size != size2) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Failed to set CPU frequency directory to %s. Total CPU frequency policies (%d) under new path is either 0 or not equal to initial total CPU frequency policies. Clearing CPU frequency policy directories", file.getAbsolutePath(), java.lang.Integer.valueOf(size), java.lang.Integer.valueOf(size2));
            this.mCpuFreqPolicyDirsById.clear();
            return false;
        }
        this.mCpuFreqDir = file;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setCpuFreqDir$1(java.io.File file) {
        return file.isDirectory() && file.getName().startsWith(POLICY_DIR_PREFIX);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean setProcStatFile(java.io.File file) {
        if (!file.exists()) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Missing proc stat file at %s", file.getAbsolutePath());
            return false;
        }
        this.mProcStatFile = file;
        return true;
    }

    private void populateCpuFreqPolicyDirsById(java.io.File[] fileArr) {
        this.mCpuFreqPolicyDirsById.clear();
        for (java.io.File file : fileArr) {
            java.lang.String substring = file.getName().substring(POLICY_DIR_PREFIX.length());
            if (!substring.isEmpty()) {
                this.mCpuFreqPolicyDirsById.append(java.lang.Integer.parseInt(substring), file);
                if (com.android.server.cpu.CpuMonitorService.DEBUG) {
                    com.android.server.utils.Slogf.d(com.android.server.cpu.CpuMonitorService.TAG, "Cached policy directory %s for policy id %s", file, substring);
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void readCpusetCategories() {
        char c;
        java.io.File[] listFiles = this.mCpusetDir.listFiles(new com.android.server.cpu.CpuInfoReader$$ExternalSyntheticLambda2());
        if (listFiles == null) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Missing cpuset directories at %s", this.mCpusetDir.getAbsolutePath());
            return;
        }
        for (java.io.File file : listFiles) {
            java.lang.String name = file.getName();
            int i = 1;
            switch (name.hashCode()) {
                case -1332194002:
                    if (name.equals(CPUSET_BACKGROUND_DIR)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1141047895:
                    if (name.equals(CPUSET_TOP_APP_DIR)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 1:
                    i = 2;
                case 0:
                    java.io.File file2 = new java.io.File(file.getPath(), CPUS_FILE);
                    android.util.IntArray readCpuCores = readCpuCores(file2);
                    if (readCpuCores == null || readCpuCores.size() == 0) {
                        com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Failed to read CPU cores from %s", file2.getAbsolutePath());
                        break;
                    } else {
                        for (int i2 = 0; i2 < readCpuCores.size(); i2++) {
                            int i3 = this.mCpusetCategoriesByCpus.get(readCpuCores.get(i2)) | i;
                            this.mCpusetCategoriesByCpus.append(readCpuCores.get(i2), i3);
                            if (com.android.server.cpu.CpuMonitorService.DEBUG) {
                                com.android.server.utils.Slogf.d(com.android.server.cpu.CpuMonitorService.TAG, "Mapping CPU core id %d with cpuset categories [%s]", java.lang.Integer.valueOf(readCpuCores.get(i2)), toCpusetCategoriesStr(i3));
                            }
                        }
                        break;
                    }
                    break;
            }
        }
    }

    private void readStaticPolicyInfo() {
        for (int i = 0; i < this.mCpuFreqPolicyDirsById.size(); i++) {
            int keyAt = this.mCpuFreqPolicyDirsById.keyAt(i);
            java.io.File file = new java.io.File(this.mCpuFreqPolicyDirsById.valueAt(i), RELATED_CPUS_FILE);
            android.util.IntArray readCpuCores = readCpuCores(file);
            if (readCpuCores == null || readCpuCores.size() == 0) {
                com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Failed to read related CPU cores from %s", file.getAbsolutePath());
            } else {
                com.android.server.cpu.CpuInfoReader.StaticPolicyInfo staticPolicyInfo = new com.android.server.cpu.CpuInfoReader.StaticPolicyInfo(readCpuCores);
                this.mStaticPolicyInfoById.append(keyAt, staticPolicyInfo);
                if (com.android.server.cpu.CpuMonitorService.DEBUG) {
                    com.android.server.utils.Slogf.d(com.android.server.cpu.CpuMonitorService.TAG, "Added static policy info %s for policy id %d", staticPolicyInfo, java.lang.Integer.valueOf(keyAt));
                }
            }
        }
    }

    private android.util.SparseArray<com.android.server.cpu.CpuInfoReader.DynamicPolicyInfo> readDynamicPolicyInfo() {
        android.util.SparseArray<com.android.server.cpu.CpuInfoReader.DynamicPolicyInfo> sparseArray = new android.util.SparseArray<>();
        for (int i = 0; i < this.mCpuFreqPolicyDirsById.size(); i++) {
            int keyAt = this.mCpuFreqPolicyDirsById.keyAt(i);
            java.io.File valueAt = this.mCpuFreqPolicyDirsById.valueAt(i);
            long readCpuFreqKHz = readCpuFreqKHz(new java.io.File(valueAt, CUR_SCALING_FREQ_FILE));
            if (readCpuFreqKHz == 0) {
                com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Missing current frequency information at %s", valueAt.getAbsolutePath());
            } else {
                long readAvgTimeInStateCpuFrequency = readAvgTimeInStateCpuFrequency(keyAt, valueAt);
                java.io.File file = new java.io.File(valueAt, AFFECTED_CPUS_FILE);
                android.util.IntArray readCpuCores = readCpuCores(file);
                if (readCpuCores == null || readCpuCores.size() == 0) {
                    com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Failed to read CPU cores from %s", file.getAbsolutePath());
                } else {
                    long readCpuFreqKHz2 = readCpuFreqKHz(new java.io.File(valueAt, MAX_SCALING_FREQ_FILE));
                    if (readCpuFreqKHz2 == 0) {
                        com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Missing max CPU frequency information at %s", valueAt.getAbsolutePath());
                    } else {
                        com.android.server.cpu.CpuInfoReader.DynamicPolicyInfo dynamicPolicyInfo = new com.android.server.cpu.CpuInfoReader.DynamicPolicyInfo(readCpuFreqKHz, readCpuFreqKHz2, readAvgTimeInStateCpuFrequency, readCpuCores);
                        sparseArray.append(keyAt, dynamicPolicyInfo);
                        if (com.android.server.cpu.CpuMonitorService.DEBUG) {
                            com.android.server.utils.Slogf.d(com.android.server.cpu.CpuMonitorService.TAG, "Read dynamic policy info %s for policy id %d", dynamicPolicyInfo, java.lang.Integer.valueOf(keyAt));
                        }
                    }
                }
            }
        }
        return sparseArray;
    }

    private long readAvgTimeInStateCpuFrequency(int i, java.io.File file) {
        android.util.LongSparseLongArray readTimeInState = readTimeInState(file);
        if (readTimeInState == null || readTimeInState.size() == 0) {
            return 0L;
        }
        android.util.LongSparseLongArray longSparseLongArray = this.mTimeInStateByPolicyId.get(i);
        if (longSparseLongArray == null) {
            this.mTimeInStateByPolicyId.put(i, readTimeInState);
            if (com.android.server.cpu.CpuMonitorService.DEBUG) {
                com.android.server.utils.Slogf.d(com.android.server.cpu.CpuMonitorService.TAG, "Added aggregated time in state info for policy id %d", java.lang.Integer.valueOf(i));
            }
            return calculateAvgCpuFreq(readTimeInState);
        }
        android.util.LongSparseLongArray calculateDeltaTimeInState = calculateDeltaTimeInState(longSparseLongArray, readTimeInState);
        this.mTimeInStateByPolicyId.put(i, readTimeInState);
        if (com.android.server.cpu.CpuMonitorService.DEBUG) {
            com.android.server.utils.Slogf.d(com.android.server.cpu.CpuMonitorService.TAG, "Added latest delta time in state info for policy id %d", java.lang.Integer.valueOf(i));
        }
        return calculateAvgCpuFreq(calculateDeltaTimeInState);
    }

    @android.annotation.Nullable
    private android.util.LongSparseLongArray readTimeInState(java.io.File file) {
        if (!this.mHasTimeInStateFile) {
            return null;
        }
        java.io.File file2 = new java.io.File(file, TIME_IN_STATE_FILE);
        try {
            java.util.List<java.lang.String> readAllLines = java.nio.file.Files.readAllLines(file2.toPath());
            if (readAllLines.isEmpty()) {
                com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Empty time in state file at %s", file2.getAbsolutePath());
                return null;
            }
            android.util.LongSparseLongArray longSparseLongArray = new android.util.LongSparseLongArray();
            for (int i = 0; i < readAllLines.size(); i++) {
                java.util.regex.Matcher matcher = TIME_IN_STATE_PATTERN.matcher(readAllLines.get(i).trim());
                if (matcher.find()) {
                    longSparseLongArray.put(java.lang.Long.parseLong(matcher.group("freqKHz")), clockTickStrToMillis(matcher.group("time")));
                }
            }
            return longSparseLongArray;
        } catch (java.lang.Exception e) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, e, "Failed to read CPU time in state from file: %s", file2.getAbsolutePath());
            return null;
        }
    }

    private static long readCpuFreqKHz(java.io.File file) {
        if (!file.exists()) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "CPU frequency file %s doesn't exist", file.getAbsolutePath());
            return 0L;
        }
        try {
            java.util.List<java.lang.String> readAllLines = java.nio.file.Files.readAllLines(file.toPath());
            if (!readAllLines.isEmpty()) {
                long parseLong = java.lang.Long.parseLong(readAllLines.get(0).trim());
                if (parseLong > 0) {
                    return parseLong;
                }
                return 0L;
            }
        } catch (java.lang.Exception e) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, e, "Failed to read integer content from file: %s", file.getAbsolutePath());
        }
        return 0L;
    }

    private static android.util.LongSparseLongArray calculateDeltaTimeInState(android.util.LongSparseLongArray longSparseLongArray, android.util.LongSparseLongArray longSparseLongArray2) {
        int size = longSparseLongArray2.size();
        android.util.LongSparseLongArray longSparseLongArray3 = new android.util.LongSparseLongArray(size);
        for (int i = 0; i < size; i++) {
            long keyAt = longSparseLongArray2.keyAt(i);
            long valueAt = longSparseLongArray2.valueAt(i);
            long j = longSparseLongArray.get(keyAt);
            if (valueAt > j) {
                valueAt -= j;
            }
            longSparseLongArray3.put(keyAt, valueAt);
        }
        return longSparseLongArray3;
    }

    private static long calculateAvgCpuFreq(android.util.LongSparseLongArray longSparseLongArray) {
        double d = 0.0d;
        double d2 = 0.0d;
        for (int i = 0; i < longSparseLongArray.size(); i++) {
            d2 += longSparseLongArray.valueAt(i);
        }
        if (d2 == 0.0d) {
            return 0L;
        }
        for (int i2 = 0; i2 < longSparseLongArray.size(); i2++) {
            d += (longSparseLongArray.keyAt(i2) * longSparseLongArray.valueAt(i2)) / d2;
        }
        return (long) d;
    }

    @android.annotation.Nullable
    private static android.util.IntArray readCpuCores(java.io.File file) {
        java.lang.String[] split;
        if (!file.exists()) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Failed to read CPU cores as the file '%s' doesn't exist", file.getAbsolutePath());
            return null;
        }
        try {
            java.util.List<java.lang.String> readAllLines = java.nio.file.Files.readAllLines(file.toPath());
            android.util.IntArray intArray = new android.util.IntArray(0);
            for (int i = 0; i < readAllLines.size(); i++) {
                java.lang.String trim = readAllLines.get(i).trim();
                if (!trim.isEmpty()) {
                    if (trim.contains(",")) {
                        split = trim.split(",");
                    } else {
                        split = trim.split(" ");
                    }
                    for (int i2 = 0; i2 < split.length; i2++) {
                        java.lang.String[] split2 = split[i2].split("-");
                        if (split2.length >= 2) {
                            int parseInt = java.lang.Integer.parseInt(split2[0]);
                            int parseInt2 = java.lang.Integer.parseInt(split2[1]);
                            if (parseInt <= parseInt2) {
                                while (parseInt <= parseInt2) {
                                    intArray.add(parseInt);
                                    parseInt++;
                                }
                            }
                        } else if (split2.length == 1) {
                            intArray.add(java.lang.Integer.parseInt(split2[0]));
                        } else {
                            com.android.server.utils.Slogf.w(com.android.server.cpu.CpuMonitorService.TAG, "Invalid CPU core range format %s", split[i2]);
                        }
                    }
                }
            }
            return intArray;
        } catch (java.lang.NumberFormatException e) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, e, "Failed to read CPU cores from %s due to incorrect file format", file.getAbsolutePath());
            return null;
        } catch (java.lang.Exception e2) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, e2, "Failed to read CPU cores from %s", file.getAbsolutePath());
            return null;
        }
    }

    @android.annotation.Nullable
    private android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuUsageStats> readLatestCpuUsageStats() {
        android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuUsageStats> readCumulativeCpuUsageStats = readCumulativeCpuUsageStats();
        if (readCumulativeCpuUsageStats.size() == 0) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, "Failed to read cumulative CPU usage stats");
            return null;
        }
        android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuUsageStats> sparseArray = new android.util.SparseArray<>();
        for (int i = 0; i < readCumulativeCpuUsageStats.size(); i++) {
            int keyAt = readCumulativeCpuUsageStats.keyAt(i);
            com.android.server.cpu.CpuInfoReader.CpuUsageStats valueAt = readCumulativeCpuUsageStats.valueAt(i);
            com.android.server.cpu.CpuInfoReader.CpuUsageStats cpuUsageStats = this.mCumulativeCpuUsageStats.get(keyAt);
            if (cpuUsageStats != null) {
                valueAt = valueAt.delta(cpuUsageStats);
            }
            sparseArray.append(keyAt, valueAt);
        }
        this.mCumulativeCpuUsageStats = readCumulativeCpuUsageStats;
        return sparseArray;
    }

    private android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuUsageStats> readCumulativeCpuUsageStats() {
        android.util.SparseArray<com.android.server.cpu.CpuInfoReader.CpuUsageStats> sparseArray = new android.util.SparseArray<>();
        try {
            java.util.List<java.lang.String> readAllLines = java.nio.file.Files.readAllLines(this.mProcStatFile.toPath());
            for (int i = 0; i < readAllLines.size(); i++) {
                java.util.regex.Matcher matcher = PROC_STAT_PATTERN.matcher(readAllLines.get(i).trim());
                if (matcher.find()) {
                    sparseArray.append(java.lang.Integer.parseInt(matcher.group("core")), new com.android.server.cpu.CpuInfoReader.CpuUsageStats(clockTickStrToMillis(matcher.group("userClockTicks")), clockTickStrToMillis(matcher.group("niceClockTicks")), clockTickStrToMillis(matcher.group("sysClockTicks")), clockTickStrToMillis(matcher.group("idleClockTicks")), clockTickStrToMillis(matcher.group("iowaitClockTicks")), clockTickStrToMillis(matcher.group("irqClockTicks")), clockTickStrToMillis(matcher.group("softirqClockTicks")), clockTickStrToMillis(matcher.group("stealClockTicks")), clockTickStrToMillis(matcher.group("guestClockTicks")), clockTickStrToMillis(matcher.group("guestNiceClockTicks"))));
                }
            }
        } catch (java.lang.Exception e) {
            com.android.server.utils.Slogf.e(com.android.server.cpu.CpuMonitorService.TAG, e, "Failed to read cpu usage stats from %s", this.mProcStatFile.getAbsolutePath());
        }
        return sparseArray;
    }

    private static long clockTickStrToMillis(java.lang.String str) {
        return java.lang.Long.parseLong(str) * MILLIS_PER_CLOCK_TICK;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String toCpusetCategoriesStr(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i & 1) != 0) {
            sb.append("FLAG_CPUSET_CATEGORY_TOP_APP");
        }
        if ((i & 2) != 0) {
            if (sb.length() > 0) {
                sb.append('|');
            }
            sb.append("FLAG_CPUSET_CATEGORY_BACKGROUND");
        }
        return sb.toString();
    }

    public static final class CpuInfo {
        public static final long MISSING_FREQUENCY = 0;
        public final long avgTimeInStateCpuFreqKHz;
        public final int cpuCore;
        public final int cpusetCategories;
        public final long curCpuFreqKHz;
        public final boolean isOnline;

        @android.annotation.Nullable
        public final com.android.server.cpu.CpuInfoReader.CpuUsageStats latestCpuUsageStats;
        private long mNormalizedAvailableCpuFreqKHz;
        public final long maxCpuFreqKHz;

        CpuInfo(int i, int i2, boolean z, long j, long j2, long j3, com.android.server.cpu.CpuInfoReader.CpuUsageStats cpuUsageStats) {
            this(i, i2, z, j, j2, j3, 0L, cpuUsageStats);
            this.mNormalizedAvailableCpuFreqKHz = computeNormalizedAvailableCpuFreqKHz();
        }

        @com.android.internal.annotations.VisibleForTesting
        CpuInfo(int i, int i2, boolean z, long j, long j2, long j3, long j4, com.android.server.cpu.CpuInfoReader.CpuUsageStats cpuUsageStats) {
            this.cpuCore = i;
            this.cpusetCategories = i2;
            this.isOnline = z;
            this.curCpuFreqKHz = j;
            this.maxCpuFreqKHz = j2;
            this.avgTimeInStateCpuFreqKHz = j3;
            this.latestCpuUsageStats = cpuUsageStats;
            this.mNormalizedAvailableCpuFreqKHz = j4;
        }

        public long getNormalizedAvailableCpuFreqKHz() {
            return this.mNormalizedAvailableCpuFreqKHz;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("CpuInfo{ cpuCore = ");
            sb.append(this.cpuCore);
            sb.append(", cpusetCategories = [");
            sb.append(com.android.server.cpu.CpuInfoReader.toCpusetCategoriesStr(this.cpusetCategories));
            sb.append("], isOnline = ");
            sb.append(this.isOnline ? "Yes" : "No");
            sb.append(", curCpuFreqKHz = ");
            sb.append(this.curCpuFreqKHz == 0 ? "missing" : java.lang.Long.valueOf(this.curCpuFreqKHz));
            sb.append(", maxCpuFreqKHz = ");
            sb.append(this.maxCpuFreqKHz == 0 ? "missing" : java.lang.Long.valueOf(this.maxCpuFreqKHz));
            sb.append(", avgTimeInStateCpuFreqKHz = ");
            sb.append(this.avgTimeInStateCpuFreqKHz != 0 ? java.lang.Long.valueOf(this.avgTimeInStateCpuFreqKHz) : "missing");
            sb.append(", latestCpuUsageStats = ");
            sb.append(this.latestCpuUsageStats);
            sb.append(", mNormalizedAvailableCpuFreqKHz = ");
            sb.append(this.mNormalizedAvailableCpuFreqKHz);
            sb.append(" }");
            return sb.toString();
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.cpu.CpuInfoReader.CpuInfo)) {
                return false;
            }
            com.android.server.cpu.CpuInfoReader.CpuInfo cpuInfo = (com.android.server.cpu.CpuInfoReader.CpuInfo) obj;
            return this.cpuCore == cpuInfo.cpuCore && this.cpusetCategories == cpuInfo.cpusetCategories && this.isOnline == cpuInfo.isOnline && this.curCpuFreqKHz == cpuInfo.curCpuFreqKHz && this.maxCpuFreqKHz == cpuInfo.maxCpuFreqKHz && this.avgTimeInStateCpuFreqKHz == cpuInfo.avgTimeInStateCpuFreqKHz && this.latestCpuUsageStats.equals(cpuInfo.latestCpuUsageStats) && this.mNormalizedAvailableCpuFreqKHz == cpuInfo.mNormalizedAvailableCpuFreqKHz;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.cpuCore), java.lang.Integer.valueOf(this.cpusetCategories), java.lang.Boolean.valueOf(this.isOnline), java.lang.Long.valueOf(this.curCpuFreqKHz), java.lang.Long.valueOf(this.maxCpuFreqKHz), java.lang.Long.valueOf(this.avgTimeInStateCpuFreqKHz), this.latestCpuUsageStats, java.lang.Long.valueOf(this.mNormalizedAvailableCpuFreqKHz));
        }

        private long computeNormalizedAvailableCpuFreqKHz() {
            if (!this.isOnline) {
                return 0L;
            }
            long totalTimeMillis = this.latestCpuUsageStats.getTotalTimeMillis();
            if (totalTimeMillis == 0) {
                com.android.server.utils.Slogf.wtf(com.android.server.cpu.CpuMonitorService.TAG, "Total CPU time millis is 0. This shouldn't happen unless stats are polled too frequently");
                return 0L;
            }
            double d = totalTimeMillis;
            return (long) (((100.0d - (((((d - this.latestCpuUsageStats.idleTimeMillis) * 100.0d) / d) * (this.avgTimeInStateCpuFreqKHz == 0 ? this.curCpuFreqKHz : this.avgTimeInStateCpuFreqKHz)) / this.maxCpuFreqKHz)) * this.maxCpuFreqKHz) / 100.0d);
        }
    }

    public static final class CpuUsageStats {
        public final long guestNiceTimeMillis;
        public final long guestTimeMillis;
        public final long idleTimeMillis;
        public final long iowaitTimeMillis;
        public final long irqTimeMillis;
        public final long niceTimeMillis;
        public final long softirqTimeMillis;
        public final long stealTimeMillis;
        public final long systemTimeMillis;
        public final long userTimeMillis;

        public CpuUsageStats(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10) {
            this.userTimeMillis = j;
            this.niceTimeMillis = j2;
            this.systemTimeMillis = j3;
            this.idleTimeMillis = j4;
            this.iowaitTimeMillis = j5;
            this.irqTimeMillis = j6;
            this.softirqTimeMillis = j7;
            this.stealTimeMillis = j8;
            this.guestTimeMillis = j9;
            this.guestNiceTimeMillis = j10;
        }

        public long getTotalTimeMillis() {
            return this.userTimeMillis + this.niceTimeMillis + this.systemTimeMillis + this.idleTimeMillis + this.iowaitTimeMillis + this.irqTimeMillis + this.softirqTimeMillis + this.stealTimeMillis + this.guestTimeMillis + this.guestNiceTimeMillis;
        }

        public java.lang.String toString() {
            return "CpuUsageStats{ userTimeMillis = " + this.userTimeMillis + ", niceTimeMillis = " + this.niceTimeMillis + ", systemTimeMillis = " + this.systemTimeMillis + ", idleTimeMillis = " + this.idleTimeMillis + ", iowaitTimeMillis = " + this.iowaitTimeMillis + ", irqTimeMillis = " + this.irqTimeMillis + ", softirqTimeMillis = " + this.softirqTimeMillis + ", stealTimeMillis = " + this.stealTimeMillis + ", guestTimeMillis = " + this.guestTimeMillis + ", guestNiceTimeMillis = " + this.guestNiceTimeMillis + " }";
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.cpu.CpuInfoReader.CpuUsageStats)) {
                return false;
            }
            com.android.server.cpu.CpuInfoReader.CpuUsageStats cpuUsageStats = (com.android.server.cpu.CpuInfoReader.CpuUsageStats) obj;
            return this.userTimeMillis == cpuUsageStats.userTimeMillis && this.niceTimeMillis == cpuUsageStats.niceTimeMillis && this.systemTimeMillis == cpuUsageStats.systemTimeMillis && this.idleTimeMillis == cpuUsageStats.idleTimeMillis && this.iowaitTimeMillis == cpuUsageStats.iowaitTimeMillis && this.irqTimeMillis == cpuUsageStats.irqTimeMillis && this.softirqTimeMillis == cpuUsageStats.softirqTimeMillis && this.stealTimeMillis == cpuUsageStats.stealTimeMillis && this.guestTimeMillis == cpuUsageStats.guestTimeMillis && this.guestNiceTimeMillis == cpuUsageStats.guestNiceTimeMillis;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Long.valueOf(this.userTimeMillis), java.lang.Long.valueOf(this.niceTimeMillis), java.lang.Long.valueOf(this.systemTimeMillis), java.lang.Long.valueOf(this.idleTimeMillis), java.lang.Long.valueOf(this.iowaitTimeMillis), java.lang.Long.valueOf(this.irqTimeMillis), java.lang.Long.valueOf(this.softirqTimeMillis), java.lang.Long.valueOf(this.stealTimeMillis), java.lang.Long.valueOf(this.guestTimeMillis), java.lang.Long.valueOf(this.guestNiceTimeMillis));
        }

        com.android.server.cpu.CpuInfoReader.CpuUsageStats delta(com.android.server.cpu.CpuInfoReader.CpuUsageStats cpuUsageStats) {
            return new com.android.server.cpu.CpuInfoReader.CpuUsageStats(diff(this.userTimeMillis, cpuUsageStats.userTimeMillis), diff(this.niceTimeMillis, cpuUsageStats.niceTimeMillis), diff(this.systemTimeMillis, cpuUsageStats.systemTimeMillis), diff(this.idleTimeMillis, cpuUsageStats.idleTimeMillis), diff(this.iowaitTimeMillis, cpuUsageStats.iowaitTimeMillis), diff(this.irqTimeMillis, cpuUsageStats.irqTimeMillis), diff(this.softirqTimeMillis, cpuUsageStats.softirqTimeMillis), diff(this.stealTimeMillis, cpuUsageStats.stealTimeMillis), diff(this.guestTimeMillis, cpuUsageStats.guestTimeMillis), diff(this.guestNiceTimeMillis, cpuUsageStats.guestNiceTimeMillis));
        }

        private static long diff(long j, long j2) {
            if (j > j2) {
                return j - j2;
            }
            return 0L;
        }
    }

    private static final class StaticPolicyInfo {
        public final android.util.IntArray relatedCpuCores;

        StaticPolicyInfo(android.util.IntArray intArray) {
            this.relatedCpuCores = intArray;
        }

        public java.lang.String toString() {
            return "StaticPolicyInfo{relatedCpuCores = " + this.relatedCpuCores + '}';
        }
    }

    private static final class DynamicPolicyInfo {
        public final android.util.IntArray affectedCpuCores;
        public final long avgTimeInStateCpuFreqKHz;
        public final long curCpuFreqKHz;
        public final long maxCpuFreqKHz;

        DynamicPolicyInfo(long j, long j2, long j3, android.util.IntArray intArray) {
            this.curCpuFreqKHz = j;
            this.maxCpuFreqKHz = j2;
            this.avgTimeInStateCpuFreqKHz = j3;
            this.affectedCpuCores = intArray;
        }

        public java.lang.String toString() {
            return "DynamicPolicyInfo{curCpuFreqKHz = " + this.curCpuFreqKHz + ", maxCpuFreqKHz = " + this.maxCpuFreqKHz + ", avgTimeInStateCpuFreqKHz = " + this.avgTimeInStateCpuFreqKHz + ", affectedCpuCores = " + this.affectedCpuCores + '}';
        }
    }
}
