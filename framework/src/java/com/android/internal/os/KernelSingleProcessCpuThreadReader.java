package com.android.internal.os;

/* loaded from: classes4.dex */
public class KernelSingleProcessCpuThreadReader {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "KernelSingleProcCpuThreadRdr";
    private final com.android.internal.os.KernelSingleProcessCpuThreadReader.CpuTimeInStateReader mCpuTimeInStateReader;
    private int mFrequencyCount;
    private boolean mIsTracking;
    private final int mPid;
    private int[] mSelectedThreadNativeTids = new int[0];

    public interface CpuTimeInStateReader {
        java.lang.String[] getAggregatedTaskCpuFreqTimes(int i);

        int getCpuFrequencyCount();

        boolean startAggregatingTaskCpuTimes(int i, int i2);

        boolean startTrackingProcessCpuTimes(int i);
    }

    private native int getCpuFrequencyCount(com.android.internal.os.KernelSingleProcessCpuThreadReader.CpuTimeInStateReader cpuTimeInStateReader);

    private native boolean readProcessCpuUsage(int i, long[] jArr, long[] jArr2, com.android.internal.os.KernelSingleProcessCpuThreadReader.CpuTimeInStateReader cpuTimeInStateReader);

    private native boolean startAggregatingThreadCpuTimes(int[] iArr, com.android.internal.os.KernelSingleProcessCpuThreadReader.CpuTimeInStateReader cpuTimeInStateReader);

    private native boolean startTrackingProcessCpuTimes(int i, com.android.internal.os.KernelSingleProcessCpuThreadReader.CpuTimeInStateReader cpuTimeInStateReader);

    public KernelSingleProcessCpuThreadReader(int i, com.android.internal.os.KernelSingleProcessCpuThreadReader.CpuTimeInStateReader cpuTimeInStateReader) throws java.io.IOException {
        this.mPid = i;
        this.mCpuTimeInStateReader = cpuTimeInStateReader;
    }

    public static com.android.internal.os.KernelSingleProcessCpuThreadReader create(int i) {
        try {
            return new com.android.internal.os.KernelSingleProcessCpuThreadReader(i, null);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to initialize KernelSingleProcessCpuThreadReader", e);
            return null;
        }
    }

    public void startTrackingThreadCpuTimes() {
        if (!this.mIsTracking) {
            if (!startTrackingProcessCpuTimes(this.mPid, this.mCpuTimeInStateReader)) {
                android.util.Slog.wtf(TAG, "Failed to start tracking process CPU times for " + this.mPid);
                com.android.modules.expresslog.Counter.logIncrement("cpu.value_process_tracking_start_failure_count");
            }
            if (this.mSelectedThreadNativeTids.length > 0 && !startAggregatingThreadCpuTimes(this.mSelectedThreadNativeTids, this.mCpuTimeInStateReader)) {
                android.util.Slog.wtf(TAG, "Failed to start tracking aggregated thread CPU times for " + java.util.Arrays.toString(this.mSelectedThreadNativeTids));
                com.android.modules.expresslog.Counter.logIncrement("cpu.value_aggregated_thread_tracking_start_failure_count");
            }
            this.mIsTracking = true;
        }
    }

    public void setSelectedThreadIds(int[] iArr) {
        this.mSelectedThreadNativeTids = (int[]) iArr.clone();
        if (this.mIsTracking) {
            startAggregatingThreadCpuTimes(this.mSelectedThreadNativeTids, this.mCpuTimeInStateReader);
        }
    }

    public int getCpuFrequencyCount() {
        if (this.mFrequencyCount == 0) {
            this.mFrequencyCount = getCpuFrequencyCount(this.mCpuTimeInStateReader);
        }
        return this.mFrequencyCount;
    }

    public com.android.internal.os.KernelSingleProcessCpuThreadReader.ProcessCpuUsage getProcessCpuUsage() {
        com.android.internal.os.KernelSingleProcessCpuThreadReader.ProcessCpuUsage processCpuUsage = new com.android.internal.os.KernelSingleProcessCpuThreadReader.ProcessCpuUsage(getCpuFrequencyCount());
        if (!readProcessCpuUsage(this.mPid, processCpuUsage.threadCpuTimesMillis, processCpuUsage.selectedThreadCpuTimesMillis, this.mCpuTimeInStateReader)) {
            return null;
        }
        return processCpuUsage;
    }

    public static class ProcessCpuUsage {
        public long[] selectedThreadCpuTimesMillis;
        public long[] threadCpuTimesMillis;

        public ProcessCpuUsage(int i) {
            this.threadCpuTimesMillis = new long[i];
            this.selectedThreadCpuTimesMillis = new long[i];
        }
    }
}
