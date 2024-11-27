package com.android.server.stats.pull;

/* loaded from: classes2.dex */
public final class ProcfsMemoryUtil {
    private static final int[] CMDLINE_OUT = {4096};
    private static final java.lang.String[] STATUS_KEYS = {"Uid:", "VmHWM:", "VmRSS:", "RssAnon:", "RssShmem:", "VmSwap:"};
    private static final java.lang.String[] VMSTAT_KEYS = {"oom_kill"};

    public static final class MemorySnapshot {
        public int anonRssInKilobytes;
        public int rssHighWaterMarkInKilobytes;
        public int rssInKilobytes;
        public int rssShmemKilobytes;
        public int swapInKilobytes;
        public int uid;
    }

    static final class VmStat {
        public int oomKillCount;

        VmStat() {
        }
    }

    private ProcfsMemoryUtil() {
    }

    @android.annotation.Nullable
    public static com.android.server.stats.pull.ProcfsMemoryUtil.MemorySnapshot readMemorySnapshotFromProcfs(int i) {
        long[] jArr = new long[STATUS_KEYS.length];
        jArr[0] = -1;
        jArr[3] = -1;
        jArr[4] = -1;
        jArr[5] = -1;
        android.os.Process.readProcLines("/proc/" + i + "/status", STATUS_KEYS, jArr);
        if (jArr[0] == -1 || jArr[3] == -1 || jArr[4] == -1 || jArr[5] == -1) {
            return null;
        }
        com.android.server.stats.pull.ProcfsMemoryUtil.MemorySnapshot memorySnapshot = new com.android.server.stats.pull.ProcfsMemoryUtil.MemorySnapshot();
        memorySnapshot.uid = (int) jArr[0];
        memorySnapshot.rssHighWaterMarkInKilobytes = (int) jArr[1];
        memorySnapshot.rssInKilobytes = (int) jArr[2];
        memorySnapshot.anonRssInKilobytes = (int) jArr[3];
        memorySnapshot.rssShmemKilobytes = (int) jArr[4];
        memorySnapshot.swapInKilobytes = (int) jArr[5];
        return memorySnapshot;
    }

    public static java.lang.String readCmdlineFromProcfs(int i) {
        java.lang.String[] strArr = new java.lang.String[1];
        if (!android.os.Process.readProcFile("/proc/" + i + "/cmdline", CMDLINE_OUT, strArr, null, null)) {
            return "";
        }
        return strArr[0];
    }

    public static android.util.SparseArray<java.lang.String> getProcessCmdlines() {
        int[] pids = android.os.Process.getPids("/proc", new int[1024]);
        android.util.SparseArray<java.lang.String> sparseArray = new android.util.SparseArray<>(pids.length);
        for (int i : pids) {
            if (i < 0) {
                break;
            }
            java.lang.String readCmdlineFromProcfs = readCmdlineFromProcfs(i);
            if (!readCmdlineFromProcfs.isEmpty()) {
                sparseArray.append(i, readCmdlineFromProcfs);
            }
        }
        return sparseArray;
    }

    @android.annotation.Nullable
    static com.android.server.stats.pull.ProcfsMemoryUtil.VmStat readVmStat() {
        long[] jArr = new long[VMSTAT_KEYS.length];
        jArr[0] = -1;
        android.os.Process.readProcLines("/proc/vmstat", VMSTAT_KEYS, jArr);
        if (jArr[0] == -1) {
            return null;
        }
        com.android.server.stats.pull.ProcfsMemoryUtil.VmStat vmStat = new com.android.server.stats.pull.ProcfsMemoryUtil.VmStat();
        vmStat.oomKillCount = (int) jArr[0];
        return vmStat;
    }
}
