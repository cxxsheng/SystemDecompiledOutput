package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class KernelWakelockStats extends java.util.HashMap<java.lang.String, com.android.server.power.stats.KernelWakelockStats.Entry> {
    int kernelWakelockVersion;

    public static class Entry {
        public long activeTimeUs;
        public int count;
        public long totalTimeUs;
        public int version;

        Entry(int i, long j, long j2, int i2) {
            this.count = i;
            this.totalTimeUs = j;
            this.activeTimeUs = j2;
            this.version = i2;
        }
    }
}
