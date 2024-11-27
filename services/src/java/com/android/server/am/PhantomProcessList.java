package com.android.server.am;

/* loaded from: classes.dex */
public final class PhantomProcessList {
    private static final java.lang.String[] CGROUP_PATH_PREFIXES = {"/acct/uid_", "/sys/fs/cgroup/uid_"};
    private static final java.lang.String CGROUP_PID_PREFIX = "/pid_";
    private static final java.lang.String CGROUP_PROCS = "/cgroup.procs";
    private static final int CGROUP_V1 = 0;
    private static final int CGROUP_V2 = 1;
    static final java.lang.String TAG = "ActivityManager";

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.am.PhantomProcessList.Injector mInjector;
    private final android.os.Handler mKillHandler;
    private final com.android.server.am.ActivityManagerService mService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int mUpdateSeq;
    final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseArray<com.android.server.am.PhantomProcessRecord> mPhantomProcesses = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseArray<android.util.SparseArray<com.android.server.am.PhantomProcessRecord>> mAppPhantomProcessMap = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseArray<com.android.server.am.PhantomProcessRecord> mPhantomProcessesPidFds = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseArray<com.android.server.am.PhantomProcessRecord> mZombiePhantomProcesses = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.am.PhantomProcessRecord> mTempPhantomProcesses = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.am.ProcessRecord> mPhantomToAppProcessMap = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.io.InputStream> mCgroupProcsFds = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final byte[] mDataBuffer = new byte[4096];

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mTrimPhantomProcessScheduled = false;

    @com.android.internal.annotations.VisibleForTesting
    int mCgroupVersion = 0;

    PhantomProcessList(com.android.server.am.ActivityManagerService activityManagerService) {
        this.mService = activityManagerService;
        com.android.server.am.ProcessList processList = activityManagerService.mProcessList;
        this.mKillHandler = com.android.server.am.ProcessList.sKillHandler;
        this.mInjector = new com.android.server.am.PhantomProcessList.Injector();
        probeCgroupVersion();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void lookForPhantomProcessesLocked() {
        this.mPhantomToAppProcessMap.clear();
        android.os.StrictMode.ThreadPolicy allowThreadDiskReads = android.os.StrictMode.allowThreadDiskReads();
        try {
            synchronized (this.mService.mPidsSelfLocked) {
                try {
                    for (int size = this.mService.mPidsSelfLocked.size() - 1; size >= 0; size--) {
                        lookForPhantomProcessesLocked(this.mService.mPidsSelfLocked.valueAt(size));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } finally {
            android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock", "mService.mPidsSelfLocked"})
    private void lookForPhantomProcessesLocked(com.android.server.am.ProcessRecord processRecord) {
        int readCgroupProcs;
        if (processRecord.appZygote || processRecord.isKilled() || processRecord.isKilledByAm()) {
            return;
        }
        int pid = processRecord.getPid();
        java.io.InputStream inputStream = this.mCgroupProcsFds.get(pid);
        if (inputStream == null) {
            try {
                inputStream = this.mInjector.openCgroupProcs(getCgroupFilePath(processRecord.info.uid, pid));
                this.mCgroupProcsFds.put(pid, inputStream);
            } catch (java.io.FileNotFoundException | java.lang.SecurityException e) {
                return;
            }
        }
        byte[] bArr = this.mDataBuffer;
        long j = 0;
        int i = 0;
        do {
            try {
                readCgroupProcs = this.mInjector.readCgroupProcs(inputStream, bArr, 0, bArr.length);
                if (readCgroupProcs == -1) {
                    break;
                }
                j += readCgroupProcs;
                for (int i2 = 0; i2 < readCgroupProcs; i2++) {
                    byte b = bArr[i2];
                    if (b == 10) {
                        addChildPidLocked(processRecord, i, pid);
                        i = 0;
                    } else {
                        i = (i * 10) + (b - 48);
                    }
                }
            } catch (java.io.IOException e2) {
                android.util.Slog.e(TAG, "Error in reading cgroup procs from " + processRecord, e2);
                libcore.io.IoUtils.closeQuietly(inputStream);
                this.mCgroupProcsFds.delete(pid);
                return;
            }
        } while (readCgroupProcs >= bArr.length);
        if (i != 0) {
            addChildPidLocked(processRecord, i, pid);
        }
        inputStream.skip(-j);
    }

    private void probeCgroupVersion() {
        for (int length = CGROUP_PATH_PREFIXES.length - 1; length >= 0; length--) {
            if (new java.io.File(CGROUP_PATH_PREFIXES[length] + 1000).exists()) {
                this.mCgroupVersion = length;
                return;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String getCgroupFilePath(int i, int i2) {
        return CGROUP_PATH_PREFIXES[this.mCgroupVersion] + i + CGROUP_PID_PREFIX + i2 + CGROUP_PROCS;
    }

    static java.lang.String getProcessName(int i) {
        java.lang.String readTerminatedProcFile = com.android.internal.os.ProcStatsUtil.readTerminatedProcFile("/proc/" + i + "/cmdline", (byte) 0);
        if (readTerminatedProcFile == null) {
            return null;
        }
        int lastIndexOf = readTerminatedProcFile.lastIndexOf(47);
        if (lastIndexOf > 0 && lastIndexOf < readTerminatedProcFile.length() - 1) {
            return readTerminatedProcFile.substring(lastIndexOf + 1);
        }
        return readTerminatedProcFile;
    }

    @com.android.internal.annotations.GuardedBy({"mLock", "mService.mPidsSelfLocked"})
    private void addChildPidLocked(com.android.server.am.ProcessRecord processRecord, int i, int i2) {
        if (i2 != i && this.mService.mPidsSelfLocked.get(i) == null) {
            int indexOfKey = this.mPhantomToAppProcessMap.indexOfKey(i);
            if (indexOfKey >= 0) {
                if (processRecord == this.mPhantomToAppProcessMap.valueAt(indexOfKey)) {
                    return;
                } else {
                    this.mPhantomToAppProcessMap.setValueAt(indexOfKey, processRecord);
                }
            } else {
                this.mPhantomToAppProcessMap.put(i, processRecord);
            }
            int uidForPid = android.os.Process.getUidForPid(i);
            java.lang.String processName = this.mInjector.getProcessName(i);
            if (processName == null || uidForPid < 0) {
                this.mPhantomToAppProcessMap.delete(i);
            } else {
                getOrCreatePhantomProcessIfNeededLocked(processName, uidForPid, i, true);
            }
        }
    }

    void onAppDied(int i) {
        synchronized (this.mLock) {
            try {
                int indexOfKey = this.mCgroupProcsFds.indexOfKey(i);
                if (indexOfKey >= 0) {
                    java.io.InputStream valueAt = this.mCgroupProcsFds.valueAt(indexOfKey);
                    this.mCgroupProcsFds.removeAt(indexOfKey);
                    libcore.io.IoUtils.closeQuietly(valueAt);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.server.am.PhantomProcessRecord getOrCreatePhantomProcessIfNeededLocked(java.lang.String str, int i, int i2, boolean z) {
        com.android.server.am.ProcessRecord processRecord;
        if (isAppProcess(i2)) {
            return null;
        }
        int indexOfKey = this.mPhantomProcesses.indexOfKey(i2);
        if (indexOfKey >= 0) {
            com.android.server.am.PhantomProcessRecord valueAt = this.mPhantomProcesses.valueAt(indexOfKey);
            if (valueAt.equals(str, i, i2)) {
                return valueAt;
            }
            android.util.Slog.w(TAG, "Stale " + valueAt + ", removing");
            onPhantomProcessKilledLocked(valueAt);
        } else {
            int indexOfKey2 = this.mZombiePhantomProcesses.indexOfKey(i2);
            if (indexOfKey2 >= 0) {
                com.android.server.am.PhantomProcessRecord valueAt2 = this.mZombiePhantomProcesses.valueAt(indexOfKey2);
                if (valueAt2.equals(str, i, i2)) {
                    return valueAt2;
                }
                this.mZombiePhantomProcesses.removeAt(indexOfKey2);
            }
        }
        if (!z || (processRecord = this.mPhantomToAppProcessMap.get(i2)) == null) {
            return null;
        }
        try {
            int pid = processRecord.getPid();
            com.android.server.am.PhantomProcessRecord phantomProcessRecord = new com.android.server.am.PhantomProcessRecord(str, i, i2, pid, this.mService, new java.util.function.Consumer() { // from class: com.android.server.am.PhantomProcessList$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.am.PhantomProcessList.this.onPhantomProcessKilledLocked((com.android.server.am.PhantomProcessRecord) obj);
                }
            });
            phantomProcessRecord.mUpdateSeq = this.mUpdateSeq;
            this.mPhantomProcesses.put(i2, phantomProcessRecord);
            android.util.SparseArray<com.android.server.am.PhantomProcessRecord> sparseArray = this.mAppPhantomProcessMap.get(pid);
            if (sparseArray == null) {
                sparseArray = new android.util.SparseArray<>();
                this.mAppPhantomProcessMap.put(pid, sparseArray);
            }
            sparseArray.put(i2, phantomProcessRecord);
            if (phantomProcessRecord.mPidFd != null) {
                this.mKillHandler.getLooper().getQueue().addOnFileDescriptorEventListener(phantomProcessRecord.mPidFd, 5, new android.os.MessageQueue.OnFileDescriptorEventListener() { // from class: com.android.server.am.PhantomProcessList$$ExternalSyntheticLambda1
                    @Override // android.os.MessageQueue.OnFileDescriptorEventListener
                    public final int onFileDescriptorEvents(java.io.FileDescriptor fileDescriptor, int i3) {
                        int onPhantomProcessFdEvent;
                        onPhantomProcessFdEvent = com.android.server.am.PhantomProcessList.this.onPhantomProcessFdEvent(fileDescriptor, i3);
                        return onPhantomProcessFdEvent;
                    }
                });
                this.mPhantomProcessesPidFds.put(phantomProcessRecord.mPidFd.getInt$(), phantomProcessRecord);
            }
            scheduleTrimPhantomProcessesLocked();
            return phantomProcessRecord;
        } catch (java.lang.IllegalStateException e) {
            return null;
        }
    }

    private boolean isAppProcess(int i) {
        boolean z;
        synchronized (this.mService.mPidsSelfLocked) {
            z = this.mService.mPidsSelfLocked.get(i) != null;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int onPhantomProcessFdEvent(java.io.FileDescriptor fileDescriptor, int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.am.PhantomProcessRecord phantomProcessRecord = this.mPhantomProcessesPidFds.get(fileDescriptor.getInt$());
                if (phantomProcessRecord == null) {
                    return 0;
                }
                if ((i & 1) == 0) {
                    phantomProcessRecord.killLocked("Process error", true);
                } else {
                    phantomProcessRecord.onProcDied(true);
                }
                return 0;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onPhantomProcessKilledLocked(com.android.server.am.PhantomProcessRecord phantomProcessRecord) {
        if (phantomProcessRecord.mPidFd != null && phantomProcessRecord.mPidFd.valid()) {
            this.mKillHandler.getLooper().getQueue().removeOnFileDescriptorEventListener(phantomProcessRecord.mPidFd);
            this.mPhantomProcessesPidFds.remove(phantomProcessRecord.mPidFd.getInt$());
            libcore.io.IoUtils.closeQuietly(phantomProcessRecord.mPidFd);
        }
        this.mPhantomProcesses.remove(phantomProcessRecord.mPid);
        int indexOfKey = this.mAppPhantomProcessMap.indexOfKey(phantomProcessRecord.mPpid);
        if (indexOfKey < 0) {
            return;
        }
        android.util.SparseArray<com.android.server.am.PhantomProcessRecord> valueAt = this.mAppPhantomProcessMap.valueAt(indexOfKey);
        valueAt.remove(phantomProcessRecord.mPid);
        if (valueAt.size() == 0) {
            this.mAppPhantomProcessMap.removeAt(indexOfKey);
        }
        if (phantomProcessRecord.mZombie) {
            this.mZombiePhantomProcesses.put(phantomProcessRecord.mPid, phantomProcessRecord);
        } else {
            this.mZombiePhantomProcesses.remove(phantomProcessRecord.mPid);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void scheduleTrimPhantomProcessesLocked() {
        if (!this.mTrimPhantomProcessScheduled) {
            this.mTrimPhantomProcessScheduled = true;
            this.mService.mHandler.post(new com.android.server.am.ActivityManagerConstants$$ExternalSyntheticLambda0(this));
        }
    }

    void trimPhantomProcessesIfNecessary() {
        if (!this.mService.mSystemReady || !android.util.FeatureFlagUtils.isEnabled(this.mService.mContext, "settings_enable_monitor_phantom_procs")) {
            return;
        }
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mService.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                synchronized (this.mLock) {
                    try {
                        this.mTrimPhantomProcessScheduled = false;
                        if (this.mService.mConstants.MAX_PHANTOM_PROCESSES < this.mPhantomProcesses.size()) {
                            for (int size = this.mPhantomProcesses.size() - 1; size >= 0; size--) {
                                this.mTempPhantomProcesses.add(this.mPhantomProcesses.valueAt(size));
                            }
                            synchronized (this.mService.mPidsSelfLocked) {
                                java.util.Collections.sort(this.mTempPhantomProcesses, new java.util.Comparator() { // from class: com.android.server.am.PhantomProcessList$$ExternalSyntheticLambda2
                                    @Override // java.util.Comparator
                                    public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                                        int lambda$trimPhantomProcessesIfNecessary$0;
                                        lambda$trimPhantomProcessesIfNecessary$0 = com.android.server.am.PhantomProcessList.this.lambda$trimPhantomProcessesIfNecessary$0((com.android.server.am.PhantomProcessRecord) obj, (com.android.server.am.PhantomProcessRecord) obj2);
                                        return lambda$trimPhantomProcessesIfNecessary$0;
                                    }
                                });
                            }
                            for (int size2 = this.mTempPhantomProcesses.size() - 1; size2 >= this.mService.mConstants.MAX_PHANTOM_PROCESSES; size2--) {
                                this.mTempPhantomProcesses.get(size2).killLocked("Trimming phantom processes", true);
                            }
                            this.mTempPhantomProcesses.clear();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } catch (java.lang.Throwable th2) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th2;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$trimPhantomProcessesIfNecessary$0(com.android.server.am.PhantomProcessRecord phantomProcessRecord, com.android.server.am.PhantomProcessRecord phantomProcessRecord2) {
        com.android.server.am.ProcessRecord processRecord = this.mService.mPidsSelfLocked.get(phantomProcessRecord.mPpid);
        if (processRecord == null) {
            return 1;
        }
        com.android.server.am.ProcessRecord processRecord2 = this.mService.mPidsSelfLocked.get(phantomProcessRecord2.mPpid);
        if (processRecord2 == null) {
            return -1;
        }
        if (processRecord.mState.getCurAdj() != processRecord2.mState.getCurAdj()) {
            return processRecord.mState.getCurAdj() - processRecord2.mState.getCurAdj();
        }
        if (phantomProcessRecord.mKnownSince == phantomProcessRecord2.mKnownSince) {
            return 0;
        }
        if (phantomProcessRecord.mKnownSince < phantomProcessRecord2.mKnownSince) {
            return 1;
        }
        return -1;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void pruneStaleProcessesLocked() {
        for (int size = this.mPhantomProcesses.size() - 1; size >= 0; size--) {
            com.android.server.am.PhantomProcessRecord valueAt = this.mPhantomProcesses.valueAt(size);
            if (valueAt.mUpdateSeq < this.mUpdateSeq) {
                valueAt.killLocked("Stale process", true);
            }
        }
        for (int size2 = this.mZombiePhantomProcesses.size() - 1; size2 >= 0; size2--) {
            int i = this.mZombiePhantomProcesses.valueAt(size2).mUpdateSeq;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void killPhantomProcessGroupLocked(com.android.server.am.ProcessRecord processRecord, com.android.server.am.PhantomProcessRecord phantomProcessRecord, int i, int i2, java.lang.String str) {
        synchronized (this.mLock) {
            try {
                int indexOfKey = this.mAppPhantomProcessMap.indexOfKey(phantomProcessRecord.mPpid);
                if (indexOfKey >= 0) {
                    android.util.SparseArray<com.android.server.am.PhantomProcessRecord> valueAt = this.mAppPhantomProcessMap.valueAt(indexOfKey);
                    for (int size = valueAt.size() - 1; size >= 0; size--) {
                        com.android.server.am.PhantomProcessRecord valueAt2 = valueAt.valueAt(size);
                        if (valueAt2 == phantomProcessRecord) {
                            valueAt2.killLocked(str, true);
                        } else {
                            valueAt2.killLocked("Caused by siling process: " + str, false);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        processRecord.killLocked("Caused by child process: " + str, i, i2, true);
    }

    void forEachPhantomProcessOfApp(com.android.server.am.ProcessRecord processRecord, java.util.function.Function<com.android.server.am.PhantomProcessRecord, java.lang.Boolean> function) {
        synchronized (this.mLock) {
            try {
                int indexOfKey = this.mAppPhantomProcessMap.indexOfKey(processRecord.getPid());
                if (indexOfKey >= 0) {
                    android.util.SparseArray<com.android.server.am.PhantomProcessRecord> valueAt = this.mAppPhantomProcessMap.valueAt(indexOfKey);
                    for (int size = valueAt.size() - 1; size >= 0 && function.apply(valueAt.valueAt(size)).booleanValue(); size--) {
                    }
                }
            } finally {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"tracker"})
    void updateProcessCpuStatesLocked(com.android.internal.os.ProcessCpuTracker processCpuTracker) {
        synchronized (this.mLock) {
            try {
                this.mUpdateSeq++;
                lookForPhantomProcessesLocked();
                for (int countStats = processCpuTracker.countStats() - 1; countStats >= 0; countStats--) {
                    com.android.internal.os.ProcessCpuTracker.Stats stats = processCpuTracker.getStats(countStats);
                    com.android.server.am.PhantomProcessRecord orCreatePhantomProcessIfNeededLocked = getOrCreatePhantomProcessIfNeededLocked(stats.name, stats.uid, stats.pid, false);
                    if (orCreatePhantomProcessIfNeededLocked != null) {
                        orCreatePhantomProcessIfNeededLocked.mUpdateSeq = this.mUpdateSeq;
                        orCreatePhantomProcessIfNeededLocked.mCurrentCputime += stats.rel_utime + stats.rel_stime;
                        if (orCreatePhantomProcessIfNeededLocked.mLastCputime == 0) {
                            orCreatePhantomProcessIfNeededLocked.mLastCputime = orCreatePhantomProcessIfNeededLocked.mCurrentCputime;
                        }
                        orCreatePhantomProcessIfNeededLocked.updateAdjLocked();
                    }
                }
                pruneStaleProcessesLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        synchronized (this.mLock) {
            dumpPhantomeProcessLocked(printWriter, str, "All Active App Child Processes:", this.mPhantomProcesses);
            dumpPhantomeProcessLocked(printWriter, str, "All Zombie App Child Processes:", this.mZombiePhantomProcesses);
        }
    }

    void dumpPhantomeProcessLocked(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, android.util.SparseArray<com.android.server.am.PhantomProcessRecord> sparseArray) {
        int size = sparseArray.size();
        if (size == 0) {
            return;
        }
        printWriter.println();
        printWriter.print(str);
        printWriter.println(str2);
        for (int i = 0; i < size; i++) {
            com.android.server.am.PhantomProcessRecord valueAt = sparseArray.valueAt(i);
            printWriter.print(str);
            printWriter.print("  proc #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(valueAt.toString());
            valueAt.dump(printWriter, str + "    ");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        java.io.InputStream openCgroupProcs(java.lang.String str) throws java.io.FileNotFoundException, java.lang.SecurityException {
            return new java.io.FileInputStream(str);
        }

        int readCgroupProcs(java.io.InputStream inputStream, byte[] bArr, int i, int i2) throws java.io.IOException {
            return inputStream.read(bArr, i, i2);
        }

        java.lang.String getProcessName(int i) {
            return com.android.server.am.PhantomProcessList.getProcessName(i);
        }
    }
}
