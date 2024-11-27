package com.android.server.stats.pull;

/* loaded from: classes2.dex */
final class SystemMemoryUtil {

    static final class Metrics {
        public int activeAnonKb;
        public int activeFileKb;
        public int activeKb;
        public int availableKb;
        public int cmaFreeKb;
        public int cmaTotalKb;
        public int dmaBufTotalExportedKb;
        public int freeKb;
        public int gpuPrivateAllocationsKb;
        public int gpuTotalUsageKb;
        public int inactiveAnonKb;
        public int inactiveFileKb;
        public int inactiveKb;
        public int kernelStackKb;
        public int pageTablesKb;
        public int shmemKb;
        public int swapFreeKb;
        public int swapTotalKb;
        public int totalIonKb;
        public int totalKb;
        public int unaccountedKb;
        public int unreclaimableSlabKb;
        public int vmallocUsedKb;

        Metrics() {
        }
    }

    private SystemMemoryUtil() {
    }

    static com.android.server.stats.pull.SystemMemoryUtil.Metrics getMetrics() {
        long max;
        int dmabufHeapTotalExportedKb = (int) android.os.Debug.getDmabufHeapTotalExportedKb();
        int gpuTotalUsageKb = (int) android.os.Debug.getGpuTotalUsageKb();
        int gpuPrivateMemoryKb = (int) android.os.Debug.getGpuPrivateMemoryKb();
        int dmabufTotalExportedKb = (int) android.os.Debug.getDmabufTotalExportedKb();
        long[] jArr = new long[26];
        android.os.Debug.getMemInfo(jArr);
        long j = jArr[15];
        if (j == 0) {
            j = jArr[6];
        }
        long j2 = jArr[1] + jArr[10] + jArr[2] + jArr[16] + jArr[17] + jArr[18] + jArr[7] + j + jArr[12] + jArr[13];
        if (!android.os.Debug.isVmapStack()) {
            j2 += jArr[14];
        }
        if (dmabufTotalExportedKb >= 0 && gpuPrivateMemoryKb >= 0) {
            max = j2 + dmabufTotalExportedKb + gpuPrivateMemoryKb;
        } else {
            max = j2 + java.lang.Math.max(0, gpuTotalUsageKb);
            if (dmabufTotalExportedKb >= 0) {
                max += dmabufTotalExportedKb;
            } else if (dmabufHeapTotalExportedKb >= 0) {
                max += dmabufHeapTotalExportedKb;
            }
        }
        com.android.server.stats.pull.SystemMemoryUtil.Metrics metrics = new com.android.server.stats.pull.SystemMemoryUtil.Metrics();
        metrics.unreclaimableSlabKb = (int) jArr[7];
        metrics.vmallocUsedKb = (int) jArr[12];
        metrics.pageTablesKb = (int) jArr[13];
        metrics.kernelStackKb = (int) jArr[14];
        metrics.shmemKb = (int) jArr[4];
        metrics.totalKb = (int) jArr[0];
        metrics.freeKb = (int) jArr[1];
        metrics.availableKb = (int) jArr[19];
        metrics.activeKb = (int) jArr[16];
        metrics.inactiveKb = (int) jArr[17];
        metrics.activeAnonKb = (int) jArr[20];
        metrics.inactiveAnonKb = (int) jArr[21];
        metrics.activeFileKb = (int) jArr[22];
        metrics.inactiveFileKb = (int) jArr[23];
        metrics.swapTotalKb = (int) jArr[8];
        metrics.swapFreeKb = (int) jArr[9];
        metrics.cmaTotalKb = (int) jArr[24];
        metrics.cmaFreeKb = (int) jArr[25];
        metrics.totalIonKb = dmabufHeapTotalExportedKb;
        metrics.gpuTotalUsageKb = gpuTotalUsageKb;
        metrics.gpuPrivateAllocationsKb = gpuPrivateMemoryKb;
        metrics.dmaBufTotalExportedKb = dmabufTotalExportedKb;
        metrics.unaccountedKb = (int) (jArr[0] - max);
        return metrics;
    }
}
