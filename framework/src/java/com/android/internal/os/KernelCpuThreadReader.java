package com.android.internal.os;

/* loaded from: classes4.dex */
public class KernelCpuThreadReader {
    private static final java.lang.String CPU_STATISTICS_FILENAME = "time_in_state";
    private static final boolean DEBUG = false;
    private static final java.lang.String DEFAULT_PROCESS_NAME = "unknown_process";
    private static final java.lang.String DEFAULT_THREAD_NAME = "unknown_thread";
    private static final int ID_ERROR = -1;
    private static final java.lang.String PROCESS_DIRECTORY_FILTER = "[0-9]*";
    private static final java.lang.String PROCESS_NAME_FILENAME = "cmdline";
    private static final java.lang.String TAG = "KernelCpuThreadReader";
    private static final java.lang.String THREAD_NAME_FILENAME = "comm";
    private int[] mFrequenciesKhz;
    private com.android.internal.os.KernelCpuThreadReader.FrequencyBucketCreator mFrequencyBucketCreator;
    private final com.android.internal.os.KernelCpuThreadReader.Injector mInjector;
    private final java.nio.file.Path mProcPath;
    private final com.android.internal.os.ProcTimeInStateReader mProcTimeInStateReader;
    private java.util.function.Predicate<java.lang.Integer> mUidPredicate;
    private static final java.nio.file.Path DEFAULT_PROC_PATH = java.nio.file.Paths.get("/proc", new java.lang.String[0]);
    private static final java.nio.file.Path DEFAULT_INITIAL_TIME_IN_STATE_PATH = DEFAULT_PROC_PATH.resolve("self/time_in_state");

    public KernelCpuThreadReader(int i, java.util.function.Predicate<java.lang.Integer> predicate, java.nio.file.Path path, java.nio.file.Path path2, com.android.internal.os.KernelCpuThreadReader.Injector injector) throws java.io.IOException {
        this.mUidPredicate = predicate;
        this.mProcPath = path;
        this.mProcTimeInStateReader = new com.android.internal.os.ProcTimeInStateReader(path2);
        this.mInjector = injector;
        setNumBuckets(i);
    }

    public static com.android.internal.os.KernelCpuThreadReader create(int i, java.util.function.Predicate<java.lang.Integer> predicate) {
        try {
            return new com.android.internal.os.KernelCpuThreadReader(i, predicate, DEFAULT_PROC_PATH, DEFAULT_INITIAL_TIME_IN_STATE_PATH, new com.android.internal.os.KernelCpuThreadReader.Injector());
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to initialize KernelCpuThreadReader", e);
            return null;
        }
    }

    public java.util.ArrayList<com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage> getProcessCpuUsage() {
        com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage processCpuUsage;
        java.util.ArrayList<com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage> arrayList = new java.util.ArrayList<>();
        try {
            java.nio.file.DirectoryStream<java.nio.file.Path> newDirectoryStream = java.nio.file.Files.newDirectoryStream(this.mProcPath, PROCESS_DIRECTORY_FILTER);
            try {
                for (java.nio.file.Path path : newDirectoryStream) {
                    int processId = getProcessId(path);
                    int uidForPid = this.mInjector.getUidForPid(processId);
                    if (uidForPid != -1 && processId != -1 && this.mUidPredicate.test(java.lang.Integer.valueOf(uidForPid)) && (processCpuUsage = getProcessCpuUsage(path, processId, uidForPid)) != null) {
                        arrayList.add(processCpuUsage);
                    }
                }
                if (newDirectoryStream != null) {
                    newDirectoryStream.close();
                }
                if (arrayList.isEmpty()) {
                    android.util.Slog.w(TAG, "Didn't successfully get any process CPU information for UIDs specified");
                    return null;
                }
                return arrayList;
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Failed to iterate over process paths", e);
            return null;
        }
    }

    public int[] getCpuFrequenciesKhz() {
        return this.mFrequenciesKhz;
    }

    void setNumBuckets(int i) {
        if (this.mFrequenciesKhz != null && this.mFrequenciesKhz.length == i) {
            return;
        }
        long[] frequenciesKhz = this.mProcTimeInStateReader.getFrequenciesKhz();
        if (i != 0) {
            this.mFrequencyBucketCreator = new com.android.internal.os.KernelCpuThreadReader.FrequencyBucketCreator(frequenciesKhz, i);
            this.mFrequenciesKhz = this.mFrequencyBucketCreator.bucketFrequencies(frequenciesKhz);
            return;
        }
        this.mFrequencyBucketCreator = null;
        this.mFrequenciesKhz = new int[frequenciesKhz.length];
        for (int i2 = 0; i2 < frequenciesKhz.length; i2++) {
            this.mFrequenciesKhz[i2] = (int) frequenciesKhz[i2];
        }
    }

    public void setUidPredicate(java.util.function.Predicate<java.lang.Integer> predicate) {
        this.mUidPredicate = predicate;
    }

    private com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage getProcessCpuUsage(java.nio.file.Path path, int i, int i2) {
        java.nio.file.Path resolve = path.resolve("task");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            java.nio.file.DirectoryStream<java.nio.file.Path> newDirectoryStream = java.nio.file.Files.newDirectoryStream(resolve);
            try {
                java.util.Iterator<java.nio.file.Path> it = newDirectoryStream.iterator();
                while (it.hasNext()) {
                    com.android.internal.os.KernelCpuThreadReader.ThreadCpuUsage threadCpuUsage = getThreadCpuUsage(it.next());
                    if (threadCpuUsage != null) {
                        arrayList.add(threadCpuUsage);
                    }
                }
                if (newDirectoryStream != null) {
                    newDirectoryStream.close();
                }
                if (arrayList.isEmpty()) {
                    return null;
                }
                return new com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage(i, getProcessName(path), i2, arrayList);
            } catch (java.lang.Throwable th) {
                if (newDirectoryStream != null) {
                    try {
                        newDirectoryStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.io.IOException | java.nio.file.DirectoryIteratorException e) {
            return null;
        }
    }

    private com.android.internal.os.KernelCpuThreadReader.ThreadCpuUsage getThreadCpuUsage(java.nio.file.Path path) {
        int[] iArr;
        try {
            int parseInt = java.lang.Integer.parseInt(path.getFileName().toString());
            java.lang.String threadName = getThreadName(path);
            long[] usageTimesMillis = this.mProcTimeInStateReader.getUsageTimesMillis(path.resolve(CPU_STATISTICS_FILENAME));
            if (usageTimesMillis == null) {
                return null;
            }
            if (this.mFrequencyBucketCreator != null) {
                iArr = this.mFrequencyBucketCreator.bucketValues(usageTimesMillis);
            } else {
                int[] iArr2 = new int[usageTimesMillis.length];
                for (int i = 0; i < usageTimesMillis.length; i++) {
                    iArr2[i] = (int) usageTimesMillis[i];
                }
                iArr = iArr2;
            }
            return new com.android.internal.os.KernelCpuThreadReader.ThreadCpuUsage(parseInt, threadName, iArr);
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.w(TAG, "Failed to parse thread ID when iterating over /proc/*/task", e);
            return null;
        }
    }

    private java.lang.String getProcessName(java.nio.file.Path path) {
        java.lang.String readSingleLineProcFile = com.android.internal.os.ProcStatsUtil.readSingleLineProcFile(path.resolve(PROCESS_NAME_FILENAME).toString());
        if (readSingleLineProcFile != null) {
            return readSingleLineProcFile;
        }
        return DEFAULT_PROCESS_NAME;
    }

    private java.lang.String getThreadName(java.nio.file.Path path) {
        java.lang.String readNullSeparatedFile = com.android.internal.os.ProcStatsUtil.readNullSeparatedFile(path.resolve(THREAD_NAME_FILENAME).toString());
        if (readNullSeparatedFile == null) {
            return DEFAULT_THREAD_NAME;
        }
        return readNullSeparatedFile;
    }

    private int getProcessId(java.nio.file.Path path) {
        java.lang.String path2 = path.getFileName().toString();
        try {
            return java.lang.Integer.parseInt(path2);
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.w(TAG, "Failed to parse " + path2 + " as process ID", e);
            return -1;
        }
    }

    public static class FrequencyBucketCreator {
        private final int[] mBucketStartIndices;
        private final int mNumBuckets;
        private final int mNumFrequencies;

        public FrequencyBucketCreator(long[] jArr, int i) {
            this.mNumFrequencies = jArr.length;
            this.mBucketStartIndices = getBucketStartIndices(getClusterStartIndices(jArr), i, this.mNumFrequencies);
            this.mNumBuckets = this.mBucketStartIndices.length;
        }

        public int[] bucketValues(long[] jArr) {
            com.android.internal.util.Preconditions.checkArgument(jArr.length == this.mNumFrequencies);
            int[] iArr = new int[this.mNumBuckets];
            for (int i = 0; i < this.mNumBuckets; i++) {
                int upperBound = getUpperBound(i, this.mBucketStartIndices, jArr.length);
                for (int lowerBound = getLowerBound(i, this.mBucketStartIndices); lowerBound < upperBound; lowerBound++) {
                    iArr[i] = (int) (iArr[i] + jArr[lowerBound]);
                }
            }
            return iArr;
        }

        public int[] bucketFrequencies(long[] jArr) {
            com.android.internal.util.Preconditions.checkArgument(jArr.length == this.mNumFrequencies);
            int i = this.mNumBuckets;
            int[] iArr = new int[i];
            for (int i2 = 0; i2 < i; i2++) {
                iArr[i2] = (int) jArr[this.mBucketStartIndices[i2]];
            }
            return iArr;
        }

        private static int[] getClusterStartIndices(long[] jArr) {
            android.util.IntArray intArray = new android.util.IntArray();
            int i = 0;
            intArray.add(0);
            while (i < jArr.length - 1) {
                long j = jArr[i];
                i++;
                if (j >= jArr[i]) {
                    intArray.add(i);
                }
            }
            return intArray.toArray();
        }

        private static int[] getBucketStartIndices(int[] iArr, int i, int i2) {
            int i3;
            int length = iArr.length;
            if (length > i) {
                return java.util.Arrays.copyOfRange(iArr, 0, i);
            }
            android.util.IntArray intArray = new android.util.IntArray();
            for (int i4 = 0; i4 < length; i4++) {
                int lowerBound = getLowerBound(i4, iArr);
                int upperBound = getUpperBound(i4, iArr, i2);
                int i5 = length - 1;
                if (i4 != i5) {
                    i3 = i / length;
                } else {
                    i3 = i - ((i / length) * i5);
                }
                int max = java.lang.Math.max(1, (upperBound - lowerBound) / i3);
                for (int i6 = 0; i6 < i3; i6++) {
                    int i7 = (i6 * max) + lowerBound;
                    if (i7 >= upperBound) {
                        break;
                    }
                    intArray.add(i7);
                }
            }
            return intArray.toArray();
        }

        private static int getLowerBound(int i, int[] iArr) {
            return iArr[i];
        }

        private static int getUpperBound(int i, int[] iArr, int i2) {
            if (i != iArr.length - 1) {
                return iArr[i + 1];
            }
            return i2;
        }
    }

    public static class ProcessCpuUsage {
        public final int processId;
        public final java.lang.String processName;
        public java.util.ArrayList<com.android.internal.os.KernelCpuThreadReader.ThreadCpuUsage> threadCpuUsages;
        public final int uid;

        public ProcessCpuUsage(int i, java.lang.String str, int i2, java.util.ArrayList<com.android.internal.os.KernelCpuThreadReader.ThreadCpuUsage> arrayList) {
            this.processId = i;
            this.processName = str;
            this.uid = i2;
            this.threadCpuUsages = arrayList;
        }
    }

    public static class ThreadCpuUsage {
        public final int threadId;
        public final java.lang.String threadName;
        public int[] usageTimesMillis;

        public ThreadCpuUsage(int i, java.lang.String str, int[] iArr) {
            this.threadId = i;
            this.threadName = str;
            this.usageTimesMillis = iArr;
        }
    }

    public static class Injector {
        public int getUidForPid(int i) {
            return android.os.Process.getUidForPid(i);
        }
    }
}
