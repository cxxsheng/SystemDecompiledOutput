package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class SystemServerCpuThreadReader {
    private final com.android.server.power.stats.SystemServerCpuThreadReader.SystemServiceCpuThreadTimes mDeltaCpuThreadTimes;
    private final com.android.internal.os.KernelSingleProcessCpuThreadReader mKernelCpuThreadReader;
    private long[] mLastBinderThreadCpuTimesUs;
    private long[] mLastThreadCpuTimesUs;

    public static class SystemServiceCpuThreadTimes {
        public long[] binderThreadCpuTimesUs;
        public long[] threadCpuTimesUs;
    }

    public static com.android.server.power.stats.SystemServerCpuThreadReader create() {
        return new com.android.server.power.stats.SystemServerCpuThreadReader(com.android.internal.os.KernelSingleProcessCpuThreadReader.create(android.os.Process.myPid()));
    }

    @com.android.internal.annotations.VisibleForTesting
    public SystemServerCpuThreadReader(int i, com.android.internal.os.KernelSingleProcessCpuThreadReader.CpuTimeInStateReader cpuTimeInStateReader) throws java.io.IOException {
        this(new com.android.internal.os.KernelSingleProcessCpuThreadReader(i, cpuTimeInStateReader));
    }

    @com.android.internal.annotations.VisibleForTesting
    public SystemServerCpuThreadReader(com.android.internal.os.KernelSingleProcessCpuThreadReader kernelSingleProcessCpuThreadReader) {
        this.mDeltaCpuThreadTimes = new com.android.server.power.stats.SystemServerCpuThreadReader.SystemServiceCpuThreadTimes();
        this.mKernelCpuThreadReader = kernelSingleProcessCpuThreadReader;
    }

    public void startTrackingThreadCpuTime() {
        this.mKernelCpuThreadReader.startTrackingThreadCpuTimes();
    }

    public void setBinderThreadNativeTids(int[] iArr) {
        this.mKernelCpuThreadReader.setSelectedThreadIds(iArr);
    }

    @android.annotation.Nullable
    public com.android.server.power.stats.SystemServerCpuThreadReader.SystemServiceCpuThreadTimes readDelta() {
        int cpuFrequencyCount = this.mKernelCpuThreadReader.getCpuFrequencyCount();
        if (this.mLastThreadCpuTimesUs == null) {
            this.mLastThreadCpuTimesUs = new long[cpuFrequencyCount];
            this.mLastBinderThreadCpuTimesUs = new long[cpuFrequencyCount];
            this.mDeltaCpuThreadTimes.threadCpuTimesUs = new long[cpuFrequencyCount];
            this.mDeltaCpuThreadTimes.binderThreadCpuTimesUs = new long[cpuFrequencyCount];
        }
        com.android.internal.os.KernelSingleProcessCpuThreadReader.ProcessCpuUsage processCpuUsage = this.mKernelCpuThreadReader.getProcessCpuUsage();
        if (processCpuUsage == null) {
            return null;
        }
        for (int i = cpuFrequencyCount - 1; i >= 0; i--) {
            long j = processCpuUsage.threadCpuTimesMillis[i] * 1000;
            long j2 = processCpuUsage.selectedThreadCpuTimesMillis[i] * 1000;
            this.mDeltaCpuThreadTimes.threadCpuTimesUs[i] = java.lang.Math.max(0L, j - this.mLastThreadCpuTimesUs[i]);
            this.mDeltaCpuThreadTimes.binderThreadCpuTimesUs[i] = java.lang.Math.max(0L, j2 - this.mLastBinderThreadCpuTimesUs[i]);
            this.mLastThreadCpuTimesUs[i] = j;
            this.mLastBinderThreadCpuTimesUs[i] = j2;
        }
        return this.mDeltaCpuThreadTimes;
    }

    @android.annotation.Nullable
    public com.android.server.power.stats.SystemServerCpuThreadReader.SystemServiceCpuThreadTimes readAbsolute() {
        int cpuFrequencyCount = this.mKernelCpuThreadReader.getCpuFrequencyCount();
        com.android.internal.os.KernelSingleProcessCpuThreadReader.ProcessCpuUsage processCpuUsage = this.mKernelCpuThreadReader.getProcessCpuUsage();
        if (processCpuUsage == null) {
            return null;
        }
        com.android.server.power.stats.SystemServerCpuThreadReader.SystemServiceCpuThreadTimes systemServiceCpuThreadTimes = new com.android.server.power.stats.SystemServerCpuThreadReader.SystemServiceCpuThreadTimes();
        systemServiceCpuThreadTimes.threadCpuTimesUs = new long[cpuFrequencyCount];
        systemServiceCpuThreadTimes.binderThreadCpuTimesUs = new long[cpuFrequencyCount];
        for (int i = 0; i < cpuFrequencyCount; i++) {
            systemServiceCpuThreadTimes.threadCpuTimesUs[i] = processCpuUsage.threadCpuTimesMillis[i] * 1000;
            systemServiceCpuThreadTimes.binderThreadCpuTimesUs[i] = processCpuUsage.selectedThreadCpuTimesMillis[i] * 1000;
        }
        return systemServiceCpuThreadTimes;
    }
}
