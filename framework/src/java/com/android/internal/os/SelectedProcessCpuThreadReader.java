package com.android.internal.os;

/* loaded from: classes4.dex */
public final class SelectedProcessCpuThreadReader {
    private final java.lang.String[] mCmdline;
    private com.android.internal.os.KernelSingleProcessCpuThreadReader mKernelCpuThreadReader;
    private int mPid;

    public SelectedProcessCpuThreadReader(java.lang.String str) {
        this.mCmdline = new java.lang.String[]{str};
    }

    public com.android.internal.os.KernelSingleProcessCpuThreadReader.ProcessCpuUsage readAbsolute() {
        int[] pidsForCommands = android.os.Process.getPidsForCommands(this.mCmdline);
        if (pidsForCommands == null || pidsForCommands.length != 1) {
            return null;
        }
        int i = pidsForCommands[0];
        if (this.mPid == i) {
            return this.mKernelCpuThreadReader.getProcessCpuUsage();
        }
        this.mPid = i;
        this.mKernelCpuThreadReader = com.android.internal.os.KernelSingleProcessCpuThreadReader.create(this.mPid);
        this.mKernelCpuThreadReader.startTrackingThreadCpuTimes();
        return null;
    }
}
