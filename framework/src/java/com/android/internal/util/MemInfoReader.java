package com.android.internal.util;

/* loaded from: classes5.dex */
public final class MemInfoReader {
    final long[] mInfos = new long[26];

    public void readMemInfo() {
        android.os.StrictMode.ThreadPolicy allowThreadDiskReads = android.os.StrictMode.allowThreadDiskReads();
        try {
            android.os.Debug.getMemInfo(this.mInfos);
        } finally {
            android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }

    public long getTotalSize() {
        return this.mInfos[0] * 1024;
    }

    public long getFreeSize() {
        return this.mInfos[1] * 1024;
    }

    public long getCachedSize() {
        return getCachedSizeKb() * 1024;
    }

    public long getKernelUsedSize() {
        return getKernelUsedSizeKb() * 1024;
    }

    public long getTotalSizeKb() {
        return this.mInfos[0];
    }

    public long getFreeSizeKb() {
        return this.mInfos[1];
    }

    public long getCachedSizeKb() {
        long j = this.mInfos[15];
        if (j == 0) {
            j = this.mInfos[6];
        }
        return ((this.mInfos[2] + j) + this.mInfos[3]) - this.mInfos[11];
    }

    public long getKernelUsedSizeKb() {
        long j = this.mInfos[4] + this.mInfos[7] + this.mInfos[12] + this.mInfos[13];
        if (!android.os.Debug.isVmapStack()) {
            return j + this.mInfos[14];
        }
        return j;
    }

    public long getSwapTotalSizeKb() {
        return this.mInfos[8];
    }

    public long getSwapFreeSizeKb() {
        return this.mInfos[9];
    }

    public long getZramTotalSizeKb() {
        return this.mInfos[10];
    }

    public long[] getRawInfo() {
        return this.mInfos;
    }
}
