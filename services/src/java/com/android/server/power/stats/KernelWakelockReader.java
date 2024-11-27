package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class KernelWakelockReader {
    private static final java.lang.String TAG = "KernelWakelockReader";
    private static final java.lang.String sSysClassWakeupDir = "/sys/class/wakeup";
    private static final java.lang.String sWakelockFile = "/proc/wakelocks";
    private static final java.lang.String sWakeupSourceFile = "/d/wakeup_sources";
    private static int sKernelWakelockUpdateVersion = 0;
    private static final int[] PROC_WAKELOCKS_FORMAT = {5129, 8201, 9, 8201, 9, 8201};
    private static final int[] WAKEUP_SOURCES_FORMAT = {4105, 8457, com.android.internal.util.FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED, com.android.internal.util.FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED, com.android.internal.util.FrameworkStatsLog.USER_LIFECYCLE_EVENT_OCCURRED, 8457, 8457};
    private final java.lang.String[] mProcWakelocksName = new java.lang.String[3];
    private final long[] mProcWakelocksData = new long[4];
    private android.system.suspend.internal.ISuspendControlServiceInternal mSuspendControlService = null;
    private byte[] mKernelWakelockBuffer = new byte[32768];

    public com.android.server.power.stats.KernelWakelockStats readKernelWakelockStats(com.android.server.power.stats.KernelWakelockStats kernelWakelockStats) {
        java.io.FileInputStream fileInputStream;
        boolean z;
        int i;
        com.android.server.power.stats.KernelWakelockStats removeOldStats;
        if (new java.io.File(sSysClassWakeupDir).exists()) {
            synchronized (com.android.server.power.stats.KernelWakelockReader.class) {
                try {
                    updateVersion(kernelWakelockStats);
                    if (getWakelockStatsFromSystemSuspend(kernelWakelockStats) == null) {
                        android.util.Slog.w(TAG, "Failed to get wakelock stats from SystemSuspend");
                        return null;
                    }
                    return removeOldStats(kernelWakelockStats);
                } finally {
                }
            }
        }
        int i2 = 0;
        java.util.Arrays.fill(this.mKernelWakelockBuffer, (byte) 0);
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        int allowThreadDiskReadsMask = android.os.StrictMode.allowThreadDiskReadsMask();
        try {
            try {
                fileInputStream = new java.io.FileInputStream(sWakelockFile);
                z = false;
                i = 0;
            } catch (java.io.FileNotFoundException e) {
                try {
                    fileInputStream = new java.io.FileInputStream(sWakeupSourceFile);
                    z = true;
                    i = 0;
                } catch (java.io.FileNotFoundException e2) {
                    android.util.Slog.wtf(TAG, "neither /proc/wakelocks nor /d/wakeup_sources exists");
                    return null;
                }
            }
            while (true) {
                int read = fileInputStream.read(this.mKernelWakelockBuffer, i, this.mKernelWakelockBuffer.length - i);
                if (read <= 0) {
                    break;
                }
                i += read;
            }
            fileInputStream.close();
            android.os.StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
            long uptimeMillis2 = android.os.SystemClock.uptimeMillis() - uptimeMillis;
            if (uptimeMillis2 > 100) {
                android.util.Slog.w(TAG, "Reading wakelock stats took " + uptimeMillis2 + "ms");
            }
            if (i > 0) {
                if (i >= this.mKernelWakelockBuffer.length) {
                    android.util.Slog.wtf(TAG, "Kernel wake locks exceeded mKernelWakelockBuffer size " + this.mKernelWakelockBuffer.length);
                }
                while (true) {
                    if (i2 >= i) {
                        break;
                    }
                    if (this.mKernelWakelockBuffer[i2] == 0) {
                        i = i2;
                        break;
                    }
                    i2++;
                }
            }
            synchronized (com.android.server.power.stats.KernelWakelockReader.class) {
                try {
                    updateVersion(kernelWakelockStats);
                    if (getWakelockStatsFromSystemSuspend(kernelWakelockStats) == null) {
                        android.util.Slog.w(TAG, "Failed to get Native wakelock stats from SystemSuspend");
                    }
                    parseProcWakelocks(this.mKernelWakelockBuffer, i, z, kernelWakelockStats);
                    removeOldStats = removeOldStats(kernelWakelockStats);
                } finally {
                }
            }
            return removeOldStats;
        } catch (java.io.IOException e3) {
            android.util.Slog.wtf(TAG, "failed to read kernel wakelocks", e3);
            return null;
        } finally {
            android.os.StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
        }
    }

    private android.system.suspend.internal.ISuspendControlServiceInternal waitForSuspendControlService() throws android.os.ServiceManager.ServiceNotFoundException {
        for (int i = 0; i < 5; i++) {
            this.mSuspendControlService = android.system.suspend.internal.ISuspendControlServiceInternal.Stub.asInterface(android.os.ServiceManager.getService("suspend_control_internal"));
            if (this.mSuspendControlService != null) {
                return this.mSuspendControlService;
            }
        }
        throw new android.os.ServiceManager.ServiceNotFoundException("suspend_control_internal");
    }

    private com.android.server.power.stats.KernelWakelockStats getWakelockStatsFromSystemSuspend(com.android.server.power.stats.KernelWakelockStats kernelWakelockStats) {
        if (this.mSuspendControlService == null) {
            try {
                this.mSuspendControlService = waitForSuspendControlService();
            } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                android.util.Slog.wtf(TAG, "Required service suspend_control not available", e);
                return null;
            }
        }
        try {
            updateWakelockStats(this.mSuspendControlService.getWakeLockStats(), kernelWakelockStats);
            return kernelWakelockStats;
        } catch (android.os.RemoteException e2) {
            android.util.Slog.wtf(TAG, "Failed to obtain wakelock stats from ISuspendControlService", e2);
            return null;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.power.stats.KernelWakelockStats updateWakelockStats(android.system.suspend.internal.WakeLockInfo[] wakeLockInfoArr, com.android.server.power.stats.KernelWakelockStats kernelWakelockStats) {
        for (android.system.suspend.internal.WakeLockInfo wakeLockInfo : wakeLockInfoArr) {
            if (!kernelWakelockStats.containsKey(wakeLockInfo.name)) {
                kernelWakelockStats.put(wakeLockInfo.name, new com.android.server.power.stats.KernelWakelockStats.Entry((int) wakeLockInfo.activeCount, wakeLockInfo.totalTime * 1000, wakeLockInfo.isActive ? wakeLockInfo.activeTime * 1000 : 0L, sKernelWakelockUpdateVersion));
            } else {
                com.android.server.power.stats.KernelWakelockStats.Entry entry = kernelWakelockStats.get(wakeLockInfo.name);
                entry.count = (int) wakeLockInfo.activeCount;
                entry.totalTimeUs = wakeLockInfo.totalTime * 1000;
                entry.activeTimeUs = wakeLockInfo.isActive ? wakeLockInfo.activeTime * 1000 : 0L;
                entry.version = sKernelWakelockUpdateVersion;
            }
        }
        return kernelWakelockStats;
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.power.stats.KernelWakelockStats parseProcWakelocks(byte[] bArr, int i, boolean z, com.android.server.power.stats.KernelWakelockStats kernelWakelockStats) {
        long j;
        long j2;
        int i2 = 0;
        while (i2 < i && bArr[i2] != 10 && bArr[i2] != 0) {
            i2++;
        }
        int i3 = i2 + 1;
        synchronized (this) {
            int i4 = i3;
            while (i3 < i) {
                int i5 = i4;
                while (i5 < i) {
                    try {
                        if (bArr[i5] == 10 || bArr[i5] == 0) {
                            break;
                        }
                        i5++;
                    } finally {
                    }
                }
                if (i5 <= i - 1) {
                    java.lang.String[] strArr = this.mProcWakelocksName;
                    long[] jArr = this.mProcWakelocksData;
                    for (int i6 = i4; i6 < i5; i6++) {
                        if ((bArr[i6] & 128) != 0) {
                            bArr[i6] = 63;
                        }
                    }
                    int i7 = i5;
                    boolean parseProcLine = android.os.Process.parseProcLine(bArr, i4, i5, z ? WAKEUP_SOURCES_FORMAT : PROC_WAKELOCKS_FORMAT, strArr, jArr, null);
                    java.lang.String trim = strArr[0].trim();
                    int i8 = (int) jArr[1];
                    if (z) {
                        long j3 = jArr[2] * 1000;
                        j = jArr[3] * 1000;
                        j2 = j3;
                    } else {
                        long j4 = (jArr[2] + 500) / 1000;
                        j = (jArr[3] + 500) / 1000;
                        j2 = j4;
                    }
                    if (parseProcLine && trim.length() > 0) {
                        if (!kernelWakelockStats.containsKey(trim)) {
                            kernelWakelockStats.put(trim, new com.android.server.power.stats.KernelWakelockStats.Entry(i8, j, j2, sKernelWakelockUpdateVersion));
                        } else {
                            com.android.server.power.stats.KernelWakelockStats.Entry entry = (com.android.server.power.stats.KernelWakelockStats.Entry) kernelWakelockStats.get(trim);
                            if (entry.version == sKernelWakelockUpdateVersion) {
                                entry.count += i8;
                                entry.totalTimeUs += j;
                                entry.activeTimeUs = j2;
                            } else {
                                entry.count = i8;
                                entry.totalTimeUs = j;
                                entry.activeTimeUs = j2;
                                entry.version = sKernelWakelockUpdateVersion;
                            }
                        }
                    } else if (!parseProcLine) {
                        try {
                            android.util.Slog.wtf(TAG, "Failed to parse proc line: " + new java.lang.String(bArr, i4, i7 - i4));
                        } catch (java.lang.Exception e) {
                            android.util.Slog.wtf(TAG, "Failed to parse proc line!");
                        }
                    }
                    i4 = i7 + 1;
                    i3 = i7;
                }
            }
        }
        return kernelWakelockStats;
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.power.stats.KernelWakelockStats updateVersion(com.android.server.power.stats.KernelWakelockStats kernelWakelockStats) {
        sKernelWakelockUpdateVersion++;
        kernelWakelockStats.kernelWakelockVersion = sKernelWakelockUpdateVersion;
        return kernelWakelockStats;
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.power.stats.KernelWakelockStats removeOldStats(com.android.server.power.stats.KernelWakelockStats kernelWakelockStats) {
        java.util.Iterator<com.android.server.power.stats.KernelWakelockStats.Entry> it = kernelWakelockStats.values().iterator();
        while (it.hasNext()) {
            if (it.next().version != sKernelWakelockUpdateVersion) {
                it.remove();
            }
        }
        return kernelWakelockStats;
    }
}
