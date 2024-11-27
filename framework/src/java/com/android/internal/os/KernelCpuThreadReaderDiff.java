package com.android.internal.os;

/* loaded from: classes4.dex */
public class KernelCpuThreadReaderDiff {
    private static final int OTHER_THREADS_ID = -1;
    private static final java.lang.String OTHER_THREADS_NAME = "__OTHER_THREADS";
    private static final java.lang.String TAG = "KernelCpuThreadReaderDiff";
    private int mMinimumTotalCpuUsageMillis;
    private java.util.Map<com.android.internal.os.KernelCpuThreadReaderDiff.ThreadKey, int[]> mPreviousCpuUsage = null;
    private final com.android.internal.os.KernelCpuThreadReader mReader;

    public KernelCpuThreadReaderDiff(com.android.internal.os.KernelCpuThreadReader kernelCpuThreadReader, int i) {
        this.mReader = (com.android.internal.os.KernelCpuThreadReader) com.android.internal.util.Preconditions.checkNotNull(kernelCpuThreadReader);
        this.mMinimumTotalCpuUsageMillis = i;
    }

    public java.util.ArrayList<com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage> getProcessCpuUsageDiffed() {
        java.util.Map<com.android.internal.os.KernelCpuThreadReaderDiff.ThreadKey, int[]> map;
        java.lang.Throwable th;
        java.util.ArrayList<com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage> processCpuUsage;
        try {
            processCpuUsage = this.mReader.getProcessCpuUsage();
            map = createCpuUsageMap(processCpuUsage);
        } catch (java.lang.Throwable th2) {
            map = null;
            th = th2;
        }
        try {
            if (this.mPreviousCpuUsage != null) {
                for (int i = 0; i < processCpuUsage.size(); i++) {
                    com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage processCpuUsage2 = processCpuUsage.get(i);
                    changeToDiffs(this.mPreviousCpuUsage, processCpuUsage2);
                    applyThresholding(processCpuUsage2);
                }
                this.mPreviousCpuUsage = map;
                return processCpuUsage;
            }
            this.mPreviousCpuUsage = map;
            return null;
        } catch (java.lang.Throwable th3) {
            th = th3;
            this.mPreviousCpuUsage = map;
            throw th;
        }
    }

    public int[] getCpuFrequenciesKhz() {
        return this.mReader.getCpuFrequenciesKhz();
    }

    void setMinimumTotalCpuUsageMillis(int i) {
        if (i < 0) {
            android.util.Slog.w(TAG, "Negative minimumTotalCpuUsageMillis: " + i);
        } else {
            this.mMinimumTotalCpuUsageMillis = i;
        }
    }

    private static java.util.Map<com.android.internal.os.KernelCpuThreadReaderDiff.ThreadKey, int[]> createCpuUsageMap(java.util.List<com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage> list) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (int i = 0; i < list.size(); i++) {
            com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage processCpuUsage = list.get(i);
            for (int i2 = 0; i2 < processCpuUsage.threadCpuUsages.size(); i2++) {
                com.android.internal.os.KernelCpuThreadReader.ThreadCpuUsage threadCpuUsage = processCpuUsage.threadCpuUsages.get(i2);
                arrayMap.put(new com.android.internal.os.KernelCpuThreadReaderDiff.ThreadKey(processCpuUsage.processId, threadCpuUsage.threadId, processCpuUsage.processName, threadCpuUsage.threadName), threadCpuUsage.usageTimesMillis);
            }
        }
        return arrayMap;
    }

    private static void changeToDiffs(java.util.Map<com.android.internal.os.KernelCpuThreadReaderDiff.ThreadKey, int[]> map, com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage processCpuUsage) {
        for (int i = 0; i < processCpuUsage.threadCpuUsages.size(); i++) {
            com.android.internal.os.KernelCpuThreadReader.ThreadCpuUsage threadCpuUsage = processCpuUsage.threadCpuUsages.get(i);
            int[] iArr = map.get(new com.android.internal.os.KernelCpuThreadReaderDiff.ThreadKey(processCpuUsage.processId, threadCpuUsage.threadId, processCpuUsage.processName, threadCpuUsage.threadName));
            if (iArr == null) {
                iArr = new int[threadCpuUsage.usageTimesMillis.length];
            }
            threadCpuUsage.usageTimesMillis = cpuTimeDiff(threadCpuUsage.usageTimesMillis, iArr);
        }
    }

    private void applyThresholding(com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage processCpuUsage) {
        java.util.ArrayList<com.android.internal.os.KernelCpuThreadReader.ThreadCpuUsage> arrayList = new java.util.ArrayList<>();
        int[] iArr = null;
        for (int i = 0; i < processCpuUsage.threadCpuUsages.size(); i++) {
            com.android.internal.os.KernelCpuThreadReader.ThreadCpuUsage threadCpuUsage = processCpuUsage.threadCpuUsages.get(i);
            if (this.mMinimumTotalCpuUsageMillis > totalCpuUsage(threadCpuUsage.usageTimesMillis)) {
                if (iArr == null) {
                    iArr = new int[threadCpuUsage.usageTimesMillis.length];
                }
                addToCpuUsage(iArr, threadCpuUsage.usageTimesMillis);
            } else {
                arrayList.add(threadCpuUsage);
            }
        }
        if (iArr != null) {
            arrayList.add(new com.android.internal.os.KernelCpuThreadReader.ThreadCpuUsage(-1, OTHER_THREADS_NAME, iArr));
        }
        processCpuUsage.threadCpuUsages = arrayList;
    }

    private static int totalCpuUsage(int[] iArr) {
        int i = 0;
        for (int i2 : iArr) {
            i += i2;
        }
        return i;
    }

    private static void addToCpuUsage(int[] iArr, int[] iArr2) {
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = iArr[i] + iArr2[i];
        }
    }

    private static int[] cpuTimeDiff(int[] iArr, int[] iArr2) {
        int[] iArr3 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr3[i] = iArr[i] - iArr2[i];
        }
        return iArr3;
    }

    private static class ThreadKey {
        private final int mProcessId;
        private final int mProcessNameHash;
        private final int mThreadId;
        private final int mThreadNameHash;

        ThreadKey(int i, int i2, java.lang.String str, java.lang.String str2) {
            this.mProcessId = i;
            this.mThreadId = i2;
            this.mProcessNameHash = java.util.Objects.hash(str);
            this.mThreadNameHash = java.util.Objects.hash(str2);
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mProcessId), java.lang.Integer.valueOf(this.mThreadId), java.lang.Integer.valueOf(this.mProcessNameHash), java.lang.Integer.valueOf(this.mThreadNameHash));
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.internal.os.KernelCpuThreadReaderDiff.ThreadKey)) {
                return false;
            }
            com.android.internal.os.KernelCpuThreadReaderDiff.ThreadKey threadKey = (com.android.internal.os.KernelCpuThreadReaderDiff.ThreadKey) obj;
            return this.mProcessId == threadKey.mProcessId && this.mThreadId == threadKey.mThreadId && this.mProcessNameHash == threadKey.mProcessNameHash && this.mThreadNameHash == threadKey.mThreadNameHash;
        }
    }
}
