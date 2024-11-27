package com.android.server.am;

/* loaded from: classes.dex */
public final class ProcessStatsService extends com.android.internal.app.procstats.IProcessStats.Stub {
    static final boolean DEBUG = false;
    static final int MAX_HISTORIC_STATES = 8;
    static final java.lang.String STATE_FILE_CHECKIN_SUFFIX = ".ci";
    static final java.lang.String STATE_FILE_PREFIX = "state-v2-";
    static final java.lang.String STATE_FILE_SUFFIX = ".bin";
    static final java.lang.String TAG = "ProcessStatsService";
    static long WRITE_PERIOD = 1800000;
    final com.android.server.am.ActivityManagerService mAm;
    final java.io.File mBaseDir;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mCommitPending;

    @com.android.internal.annotations.GuardedBy({"mFileLock"})
    android.util.AtomicFile mFile;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    java.lang.Boolean mInjectedScreenState;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long mLastWriteTime;
    boolean mMemFactorLowered;

    @com.android.internal.annotations.GuardedBy({"mPendingWriteLock"})
    android.os.Parcel mPendingWrite;

    @com.android.internal.annotations.GuardedBy({"mPendingWriteLock"})
    boolean mPendingWriteCommitted;

    @com.android.internal.annotations.GuardedBy({"mPendingWriteLock"})
    android.util.AtomicFile mPendingWriteFile;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final com.android.internal.app.procstats.ProcessStats mProcessStats;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean mShuttingDown;
    final java.lang.Object mLock = new java.lang.Object();
    final java.lang.Object mPendingWriteLock = new java.lang.Object();
    final java.util.concurrent.locks.ReentrantLock mFileLock = new java.util.concurrent.locks.ReentrantLock();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int mLastMemOnlyState = -1;

    public ProcessStatsService(com.android.server.am.ActivityManagerService activityManagerService, java.io.File file) {
        this.mAm = activityManagerService;
        this.mBaseDir = file;
        this.mBaseDir.mkdirs();
        synchronized (this.mLock) {
            this.mProcessStats = new com.android.internal.app.procstats.ProcessStats(true);
            updateFileLocked();
        }
        android.os.SystemProperties.addChangeCallback(new java.lang.Runnable() { // from class: com.android.server.am.ProcessStatsService.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (com.android.server.am.ProcessStatsService.this.mLock) {
                    try {
                        if (com.android.server.am.ProcessStatsService.this.mProcessStats.evaluateSystemProperties(false)) {
                            com.android.server.am.ProcessStatsService.this.mProcessStats.mFlags |= 4;
                            com.android.server.am.ProcessStatsService.this.writeStateLocked(true, true);
                            com.android.server.am.ProcessStatsService.this.mProcessStats.evaluateSystemProperties(true);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        });
    }

    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (java.lang.RuntimeException e) {
            if (!(e instanceof java.lang.SecurityException)) {
                android.util.Slog.wtf(TAG, "Process Stats Crash", e);
            }
            throw e;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void updateProcessStateHolderLocked(com.android.internal.app.procstats.ProcessStats.ProcessStateHolder processStateHolder, java.lang.String str, int i, long j, java.lang.String str2) {
        processStateHolder.pkg = this.mProcessStats.getPackageStateLocked(str, i, j);
        processStateHolder.state = this.mProcessStats.getProcessStateLocked(processStateHolder.pkg, str2);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.internal.app.procstats.ProcessState getProcessStateLocked(java.lang.String str, int i, long j, java.lang.String str2) {
        return this.mProcessStats.getProcessStateLocked(str, i, j, str2);
    }

    com.android.internal.app.procstats.ServiceState getServiceState(java.lang.String str, int i, long j, java.lang.String str2, java.lang.String str3) {
        com.android.internal.app.procstats.ServiceState serviceStateLocked;
        synchronized (this.mLock) {
            serviceStateLocked = this.mProcessStats.getServiceStateLocked(str, i, j, str2, str3);
        }
        return serviceStateLocked;
    }

    boolean isMemFactorLowered() {
        return this.mMemFactorLowered;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean setMemFactorLocked(int i, boolean z, long j) {
        this.mMemFactorLowered = i < this.mLastMemOnlyState;
        this.mLastMemOnlyState = i;
        if (this.mInjectedScreenState != null) {
            z = this.mInjectedScreenState.booleanValue();
        }
        if (z) {
            i += 4;
        }
        if (i == this.mProcessStats.mMemFactor) {
            return false;
        }
        if (this.mProcessStats.mMemFactor != -1) {
            long[] jArr = this.mProcessStats.mMemFactorDurations;
            int i2 = this.mProcessStats.mMemFactor;
            jArr[i2] = jArr[i2] + (j - this.mProcessStats.mStartTime);
        }
        this.mProcessStats.mMemFactor = i;
        this.mProcessStats.mStartTime = j;
        android.util.ArrayMap map = this.mProcessStats.mPackages.getMap();
        for (int size = map.size() - 1; size >= 0; size--) {
            android.util.SparseArray sparseArray = (android.util.SparseArray) map.valueAt(size);
            for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
                android.util.LongSparseArray longSparseArray = (android.util.LongSparseArray) sparseArray.valueAt(size2);
                for (int size3 = longSparseArray.size() - 1; size3 >= 0; size3--) {
                    android.util.ArrayMap arrayMap = ((com.android.internal.app.procstats.ProcessStats.PackageState) longSparseArray.valueAt(size3)).mServices;
                    for (int size4 = arrayMap.size() - 1; size4 >= 0; size4--) {
                        ((com.android.internal.app.procstats.ServiceState) arrayMap.valueAt(size4)).setMemFactor(i, j);
                    }
                }
            }
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    int getMemFactorLocked() {
        if (this.mProcessStats.mMemFactor != -1) {
            return this.mProcessStats.mMemFactor;
        }
        return 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void addSysMemUsageLocked(long j, long j2, long j3, long j4, long j5) {
        this.mProcessStats.addSysMemUsage(j, j2, j3, j4, j5);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void updateTrackingAssociationsLocked(int i, long j) {
        this.mProcessStats.updateTrackingAssociationsLocked(i, j);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean shouldWriteNowLocked(long j) {
        if (j <= this.mLastWriteTime + WRITE_PERIOD) {
            return false;
        }
        if (android.os.SystemClock.elapsedRealtime() > this.mProcessStats.mTimePeriodStartRealtime + com.android.internal.app.procstats.ProcessStats.COMMIT_PERIOD && android.os.SystemClock.uptimeMillis() > this.mProcessStats.mTimePeriodStartUptime + com.android.internal.app.procstats.ProcessStats.COMMIT_UPTIME_PERIOD) {
            this.mCommitPending = true;
        }
        return true;
    }

    void shutdown() {
        android.util.Slog.w(TAG, "Writing process stats before shutdown...");
        synchronized (this.mLock) {
            this.mProcessStats.mFlags |= 2;
            writeStateSyncLocked();
            this.mShuttingDown = true;
        }
    }

    void writeStateAsync() {
        synchronized (this.mLock) {
            writeStateLocked(false);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void writeStateSyncLocked() {
        writeStateLocked(true);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void writeStateLocked(boolean z) {
        if (this.mShuttingDown) {
            return;
        }
        boolean z2 = this.mCommitPending;
        this.mCommitPending = false;
        writeStateLocked(z, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void writeStateLocked(boolean z, boolean z2) {
        synchronized (this.mPendingWriteLock) {
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                if (this.mPendingWrite == null || !this.mPendingWriteCommitted) {
                    this.mPendingWrite = android.os.Parcel.obtain();
                    this.mProcessStats.mTimePeriodEndRealtime = android.os.SystemClock.elapsedRealtime();
                    this.mProcessStats.mTimePeriodEndUptime = uptimeMillis;
                    if (z2) {
                        this.mProcessStats.mFlags |= 1;
                    }
                    this.mProcessStats.writeToParcel(this.mPendingWrite, 0);
                    this.mPendingWriteFile = new android.util.AtomicFile(getCurrentFile());
                    this.mPendingWriteCommitted = z2;
                }
                if (z2) {
                    this.mProcessStats.resetSafely();
                    updateFileLocked();
                    scheduleRequestPssAllProcs(true, false);
                }
                this.mLastWriteTime = android.os.SystemClock.uptimeMillis();
                final long uptimeMillis2 = android.os.SystemClock.uptimeMillis() - uptimeMillis;
                if (!z) {
                    com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.am.ProcessStatsService.2
                        @Override // java.lang.Runnable
                        public void run() {
                            com.android.server.am.ProcessStatsService.this.performWriteState(uptimeMillis2);
                        }
                    });
                } else {
                    performWriteState(uptimeMillis2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void scheduleRequestPssAllProcs(final boolean z, final boolean z2) {
        this.mAm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.ProcessStatsService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.ProcessStatsService.this.lambda$scheduleRequestPssAllProcs$0(z, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRequestPssAllProcs$0(boolean z, boolean z2) {
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mAm.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                this.mAm.mAppProfiler.requestPssAllProcsLPr(android.os.SystemClock.uptimeMillis(), z, z2);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateFileLocked() {
        this.mFileLock.lock();
        try {
            this.mFile = new android.util.AtomicFile(new java.io.File(this.mBaseDir, STATE_FILE_PREFIX + this.mProcessStats.mTimePeriodStartClockStr + STATE_FILE_SUFFIX));
            this.mFileLock.unlock();
            this.mLastWriteTime = android.os.SystemClock.uptimeMillis();
        } catch (java.lang.Throwable th) {
            this.mFileLock.unlock();
            throw th;
        }
    }

    private java.io.File getCurrentFile() {
        this.mFileLock.lock();
        try {
            return this.mFile.getBaseFile();
        } finally {
            this.mFileLock.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performWriteState(long j) {
        synchronized (this.mPendingWriteLock) {
            try {
                android.os.Parcel parcel = this.mPendingWrite;
                android.util.AtomicFile atomicFile = this.mPendingWriteFile;
                this.mPendingWriteCommitted = false;
                if (parcel == null) {
                    return;
                }
                java.io.FileOutputStream fileOutputStream = null;
                this.mPendingWrite = null;
                this.mPendingWriteFile = null;
                this.mFileLock.lock();
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                try {
                    try {
                        fileOutputStream = atomicFile.startWrite();
                        fileOutputStream.write(parcel.marshall());
                        fileOutputStream.flush();
                        atomicFile.finishWrite(fileOutputStream);
                        com.android.internal.logging.EventLogTags.writeCommitSysConfigFile("procstats", (android.os.SystemClock.uptimeMillis() - uptimeMillis) + j);
                    } catch (java.io.IOException e) {
                        android.util.Slog.w(TAG, "Error writing process statistics", e);
                        atomicFile.failWrite(fileOutputStream);
                    }
                } finally {
                    parcel.recycle();
                    trimHistoricStatesWriteLF();
                    this.mFileLock.unlock();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mFileLock"})
    private boolean readLF(com.android.internal.app.procstats.ProcessStats processStats, android.util.AtomicFile atomicFile) {
        try {
            java.io.FileInputStream openRead = atomicFile.openRead();
            processStats.read(openRead);
            openRead.close();
            if (processStats.mReadError != null) {
                android.util.Slog.w(TAG, "Ignoring existing stats; " + processStats.mReadError);
                return false;
            }
            return true;
        } catch (java.lang.Throwable th) {
            processStats.mReadError = "caught exception: " + th;
            android.util.Slog.e(TAG, "Error reading process statistics", th);
            return false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mFileLock"})
    private java.util.ArrayList<java.lang.String> getCommittedFilesLF(int i, boolean z, boolean z2) {
        java.io.File[] listFiles = this.mBaseDir.listFiles();
        if (listFiles == null || listFiles.length <= i) {
            return null;
        }
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>(listFiles.length);
        java.lang.String path = this.mFile.getBaseFile().getPath();
        for (java.io.File file : listFiles) {
            java.lang.String path2 = file.getPath();
            if (file.getName().startsWith(STATE_FILE_PREFIX) && ((z2 || !path2.endsWith(STATE_FILE_CHECKIN_SUFFIX)) && (z || !path2.equals(path)))) {
                arrayList.add(path2);
            }
        }
        java.util.Collections.sort(arrayList);
        return arrayList;
    }

    @com.android.internal.annotations.GuardedBy({"mFileLock"})
    private void trimHistoricStatesWriteLF() {
        java.io.File[] listFiles = this.mBaseDir.listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (!listFiles[i].getName().startsWith(STATE_FILE_PREFIX)) {
                    listFiles[i].delete();
                }
            }
        }
        java.util.ArrayList<java.lang.String> committedFilesLF = getCommittedFilesLF(8, false, true);
        if (committedFilesLF != null) {
            while (committedFilesLF.size() > 8) {
                java.lang.String remove = committedFilesLF.remove(0);
                android.util.Slog.i(TAG, "Pruning old procstats: " + remove);
                new java.io.File(remove).delete();
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean dumpFilteredProcessesCsvLocked(java.io.PrintWriter printWriter, java.lang.String str, boolean z, int[] iArr, boolean z2, int[] iArr2, boolean z3, int[] iArr3, long j, java.lang.String str2) {
        java.util.ArrayList collectProcessesLocked = this.mProcessStats.collectProcessesLocked(iArr, iArr2, iArr3, iArr3, j, str2, false);
        if (collectProcessesLocked.size() > 0) {
            if (str != null) {
                printWriter.println(str);
            }
            com.android.internal.app.procstats.DumpUtils.dumpProcessListCsv(printWriter, collectProcessesLocked, z, iArr, z2, iArr2, z3, iArr3, j);
            return true;
        }
        return false;
    }

    static int[] parseStateList(java.lang.String[] strArr, int i, java.lang.String str, boolean[] zArr, java.lang.String[] strArr2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 0;
        int i3 = 0;
        while (i2 <= str.length()) {
            char charAt = i2 < str.length() ? str.charAt(i2) : (char) 0;
            if (charAt == ',' || charAt == '+' || charAt == ' ' || charAt == 0) {
                boolean z = charAt == ',';
                if (i3 == 0) {
                    zArr[0] = z;
                } else if (charAt != 0 && zArr[0] != z) {
                    strArr2[0] = "inconsistent separators (can't mix ',' with '+')";
                    return null;
                }
                if (i3 < i2 - 1) {
                    java.lang.String substring = str.substring(i3, i2);
                    int i4 = 0;
                    while (true) {
                        if (i4 >= strArr.length) {
                            break;
                        }
                        if (!substring.equals(strArr[i4])) {
                            i4++;
                        } else {
                            arrayList.add(java.lang.Integer.valueOf(i4));
                            substring = null;
                            break;
                        }
                    }
                    if (substring != null) {
                        strArr2[0] = "invalid word \"" + substring + "\"";
                        return null;
                    }
                }
                i3 = i2 + 1;
            }
            i2++;
        }
        int[] iArr = new int[arrayList.size()];
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            iArr[i5] = ((java.lang.Integer) arrayList.get(i5)).intValue() * i;
        }
        return iArr;
    }

    static int parseSectionOptions(java.lang.String str) {
        java.lang.String[] split = str.split(",");
        if (split.length == 0) {
            return 31;
        }
        java.util.List asList = java.util.Arrays.asList(com.android.internal.app.procstats.ProcessStats.OPTIONS_STR);
        int i = 0;
        for (java.lang.String str2 : split) {
            int indexOf = asList.indexOf(str2);
            if (indexOf != -1) {
                i |= com.android.internal.app.procstats.ProcessStats.OPTIONS[indexOf];
            }
        }
        return i;
    }

    @android.annotation.EnforcePermission("android.permission.PACKAGE_USAGE_STATS")
    public byte[] getCurrentStats(java.util.List<android.os.ParcelFileDescriptor> list) {
        super.getCurrentStats_enforcePermission();
        android.os.Parcel obtain = android.os.Parcel.obtain();
        synchronized (this.mLock) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mProcessStats.mTimePeriodEndRealtime = android.os.SystemClock.elapsedRealtime();
            this.mProcessStats.mTimePeriodEndUptime = uptimeMillis;
            this.mProcessStats.writeToParcel(obtain, uptimeMillis, 0);
        }
        this.mFileLock.lock();
        if (list != null) {
            try {
                java.util.ArrayList<java.lang.String> committedFilesLF = getCommittedFilesLF(0, false, true);
                if (committedFilesLF != null) {
                    for (int size = committedFilesLF.size() - 1; size >= 0; size--) {
                        try {
                            list.add(android.os.ParcelFileDescriptor.open(new java.io.File(committedFilesLF.get(size)), 268435456));
                        } catch (java.io.IOException e) {
                            android.util.Slog.w(TAG, "Failure opening procstat file " + committedFilesLF.get(size), e);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                this.mFileLock.unlock();
                throw th;
            }
        }
        this.mFileLock.unlock();
        return obtain.marshall();
    }

    public long getCommittedStats(long j, int i, boolean z, java.util.List<android.os.ParcelFileDescriptor> list) {
        return getCommittedStatsMerged(j, i, z, list, new com.android.internal.app.procstats.ProcessStats(false));
    }

    @android.annotation.EnforcePermission("android.permission.PACKAGE_USAGE_STATS")
    public long getCommittedStatsMerged(long j, int i, boolean z, java.util.List<android.os.ParcelFileDescriptor> list, com.android.internal.app.procstats.ProcessStats processStats) {
        long j2;
        java.util.ArrayList<java.lang.String> committedFilesLF;
        super.getCommittedStatsMerged_enforcePermission();
        this.mFileLock.lock();
        try {
            try {
                committedFilesLF = getCommittedFilesLF(0, false, true);
            } catch (java.io.IOException e) {
                e = e;
                j2 = j;
            }
            if (committedFilesLF == null) {
                j2 = j;
                this.mFileLock.unlock();
                return j2;
            }
            j2 = j;
            try {
                java.lang.String charSequence = android.text.format.DateFormat.format("yyyy-MM-dd-HH-mm-ss", j2).toString();
                for (int size = committedFilesLF.size() - 1; size >= 0; size--) {
                    java.lang.String str = committedFilesLF.get(size);
                    try {
                        if (str.substring(str.lastIndexOf(STATE_FILE_PREFIX) + STATE_FILE_PREFIX.length(), str.lastIndexOf(STATE_FILE_SUFFIX)).compareToIgnoreCase(charSequence) > 0) {
                            android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(android.os.ParcelFileDescriptor.open(new java.io.File(str), 268435456));
                            com.android.internal.app.procstats.ProcessStats processStats2 = new com.android.internal.app.procstats.ProcessStats(false);
                            processStats2.read(autoCloseInputStream);
                            autoCloseInputStream.close();
                            if (processStats2.mTimePeriodStartClock > j2) {
                                j2 = processStats2.mTimePeriodStartClock;
                            }
                            if (z) {
                                processStats.add(processStats2);
                            } else if (list != null) {
                                list.add(protoToParcelFileDescriptor(processStats2, i));
                            }
                            if (processStats2.mReadError != null) {
                                android.util.Log.w(TAG, "Failure reading process stats: " + processStats2.mReadError);
                            }
                        }
                    } catch (java.io.IOException e2) {
                        android.util.Slog.w(TAG, "Failure opening procstat file " + str, e2);
                    } catch (java.lang.IndexOutOfBoundsException e3) {
                        android.util.Slog.w(TAG, "Failure to read and parse commit file " + str, e3);
                    }
                }
                if (z && list != null) {
                    list.add(protoToParcelFileDescriptor(processStats, i));
                }
                this.mFileLock.unlock();
                return j2;
            } catch (java.io.IOException e4) {
                e = e4;
                android.util.Slog.w(TAG, "Failure opening procstat file", e);
                this.mFileLock.unlock();
                return j2;
            }
        } catch (java.lang.Throwable th) {
            this.mFileLock.unlock();
            throw th;
        }
    }

    public long getMinAssociationDumpDuration() {
        com.android.server.am.ActivityManagerConstants activityManagerConstants = this.mAm.mConstants;
        return com.android.server.am.ActivityManagerConstants.MIN_ASSOC_LOG_DURATION;
    }

    private static android.os.ParcelFileDescriptor protoToParcelFileDescriptor(final com.android.internal.app.procstats.ProcessStats processStats, final int i) throws java.io.IOException {
        final android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
        new java.lang.Thread("ProcessStats pipe output") { // from class: com.android.server.am.ProcessStatsService.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(createPipe[1]);
                    android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(autoCloseOutputStream);
                    processStats.dumpDebug(protoOutputStream, processStats.mTimePeriodEndRealtime, i);
                    protoOutputStream.flush();
                    autoCloseOutputStream.close();
                } catch (java.io.IOException e) {
                    android.util.Slog.w(com.android.server.am.ProcessStatsService.TAG, "Failure writing pipe", e);
                }
            }
        }.start();
        return createPipe[0];
    }

    @android.annotation.EnforcePermission("android.permission.PACKAGE_USAGE_STATS")
    public android.os.ParcelFileDescriptor getStatsOverTime(long j) {
        long j2;
        super.getStatsOverTime_enforcePermission();
        android.os.Parcel obtain = android.os.Parcel.obtain();
        synchronized (this.mLock) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mProcessStats.mTimePeriodEndRealtime = android.os.SystemClock.elapsedRealtime();
            this.mProcessStats.mTimePeriodEndUptime = uptimeMillis;
            this.mProcessStats.writeToParcel(obtain, uptimeMillis, 0);
            j2 = this.mProcessStats.mTimePeriodEndRealtime - this.mProcessStats.mTimePeriodStartRealtime;
        }
        this.mFileLock.lock();
        if (j2 < j) {
            try {
                try {
                    java.util.ArrayList<java.lang.String> committedFilesLF = getCommittedFilesLF(0, false, true);
                    if (committedFilesLF != null && committedFilesLF.size() > 0) {
                        obtain.setDataPosition(0);
                        com.android.internal.app.procstats.ProcessStats processStats = (com.android.internal.app.procstats.ProcessStats) com.android.internal.app.procstats.ProcessStats.CREATOR.createFromParcel(obtain);
                        obtain.recycle();
                        int size = committedFilesLF.size() - 1;
                        while (size >= 0 && processStats.mTimePeriodEndRealtime - processStats.mTimePeriodStartRealtime < j) {
                            android.util.AtomicFile atomicFile = new android.util.AtomicFile(new java.io.File(committedFilesLF.get(size)));
                            size--;
                            com.android.internal.app.procstats.ProcessStats processStats2 = new com.android.internal.app.procstats.ProcessStats(false);
                            readLF(processStats2, atomicFile);
                            if (processStats2.mReadError == null) {
                                processStats.add(processStats2);
                                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                sb.append("Added stats: ");
                                sb.append(processStats2.mTimePeriodStartClockStr);
                                sb.append(", over ");
                                android.util.TimeUtils.formatDuration(processStats2.mTimePeriodEndRealtime - processStats2.mTimePeriodStartRealtime, sb);
                                android.util.Slog.i(TAG, sb.toString());
                            } else {
                                android.util.Slog.w(TAG, "Failure reading " + committedFilesLF.get(size + 1) + "; " + processStats2.mReadError);
                            }
                        }
                        obtain = android.os.Parcel.obtain();
                        processStats.writeToParcel(obtain, 0);
                    }
                } catch (java.io.IOException e) {
                    android.util.Slog.w(TAG, "Failed building output pipe", e);
                    this.mFileLock.unlock();
                    return null;
                }
            } catch (java.lang.Throwable th) {
                this.mFileLock.unlock();
                throw th;
            }
        }
        final byte[] marshall = obtain.marshall();
        obtain.recycle();
        final android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
        new java.lang.Thread("ProcessStats pipe output") { // from class: com.android.server.am.ProcessStatsService.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(createPipe[1]);
                try {
                    autoCloseOutputStream.write(marshall);
                    autoCloseOutputStream.close();
                } catch (java.io.IOException e2) {
                    android.util.Slog.w(com.android.server.am.ProcessStatsService.TAG, "Failure writing pipe", e2);
                }
            }
        }.start();
        android.os.ParcelFileDescriptor parcelFileDescriptor = createPipe[0];
        this.mFileLock.unlock();
        return parcelFileDescriptor;
    }

    public int getCurrentMemoryState() {
        int i;
        synchronized (this.mLock) {
            i = this.mLastMemOnlyState;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.util.SparseArray<long[]> getUidProcStateStatsOverTime(long j) {
        long j2;
        com.android.internal.app.procstats.ProcessStats processStats = new com.android.internal.app.procstats.ProcessStats();
        synchronized (this.mLock) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mProcessStats.mTimePeriodEndRealtime = android.os.SystemClock.elapsedRealtime();
            this.mProcessStats.mTimePeriodEndUptime = uptimeMillis;
            processStats.add(this.mProcessStats);
            j2 = this.mProcessStats.mTimePeriodEndRealtime - this.mProcessStats.mTimePeriodStartRealtime;
        }
        if (j2 < j) {
            try {
                this.mFileLock.lock();
                java.util.ArrayList<java.lang.String> committedFilesLF = getCommittedFilesLF(0, false, true);
                if (committedFilesLF != null && committedFilesLF.size() > 0) {
                    int size = committedFilesLF.size() - 1;
                    while (size >= 0) {
                        if (processStats.mTimePeriodEndRealtime - processStats.mTimePeriodStartRealtime >= j) {
                            break;
                        }
                        android.util.AtomicFile atomicFile = new android.util.AtomicFile(new java.io.File(committedFilesLF.get(size)));
                        size--;
                        com.android.internal.app.procstats.ProcessStats processStats2 = new com.android.internal.app.procstats.ProcessStats(false);
                        readLF(processStats2, atomicFile);
                        if (processStats2.mReadError == null) {
                            processStats.add(processStats2);
                        } else {
                            android.util.Slog.w(TAG, "Failure reading " + committedFilesLF.get(size + 1) + "; " + processStats2.mReadError);
                        }
                    }
                }
                this.mFileLock.unlock();
            } catch (java.lang.Throwable th) {
                this.mFileLock.unlock();
                throw th;
            }
        }
        android.util.SparseArray sparseArray = processStats.mUidStates;
        android.util.SparseArray<long[]> sparseArray2 = new android.util.SparseArray<>();
        int size2 = sparseArray.size();
        for (int i = 0; i < size2; i++) {
            sparseArray2.put(sparseArray.keyAt(i), ((com.android.internal.app.procstats.UidState) sparseArray.valueAt(i)).getAggregatedDurationsInStates());
        }
        return sparseArray2;
    }

    void publish() {
        com.android.server.LocalServices.addService(com.android.internal.app.procstats.ProcessStatsInternal.class, new com.android.server.am.ProcessStatsService.LocalService());
    }

    private final class LocalService extends com.android.internal.app.procstats.ProcessStatsInternal {
        private LocalService() {
        }

        public android.util.SparseArray<long[]> getUidProcStateStatsOverTime(long j) {
            return com.android.server.am.ProcessStatsService.this.getUidProcStateStatsOverTime(j);
        }
    }

    private void dumpAggregatedStats(java.io.PrintWriter printWriter, long j, long j2, java.lang.String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i) {
        android.os.ParcelFileDescriptor statsOverTime = getStatsOverTime((((j * 60) * 60) * 1000) - (com.android.internal.app.procstats.ProcessStats.COMMIT_PERIOD / 2));
        if (statsOverTime == null) {
            printWriter.println("Unable to build stats!");
            return;
        }
        com.android.internal.app.procstats.ProcessStats processStats = new com.android.internal.app.procstats.ProcessStats(false);
        processStats.read(new android.os.ParcelFileDescriptor.AutoCloseInputStream(statsOverTime));
        if (processStats.mReadError != null) {
            printWriter.print("Failure reading: ");
            printWriter.println(processStats.mReadError);
        } else if (z) {
            processStats.dumpCheckinLocked(printWriter, str, i);
        } else if (z2 || z3) {
            processStats.dumpLocked(printWriter, str, j2, !z3, z2, z4, z5, i);
        } else {
            processStats.dumpSummaryLocked(printWriter, str, j2, z5);
        }
    }

    private static void dumpHelp(java.io.PrintWriter printWriter) {
        printWriter.println("Process stats (procstats) dump options:");
        printWriter.println("    [--checkin|-c|--csv] [--csv-screen] [--csv-proc] [--csv-mem]");
        printWriter.println("    [--details] [--full-details] [--current] [--hours N] [--last N]");
        printWriter.println("    [--max N] --active] [--commit] [--reset] [--clear] [--write] [-h]");
        printWriter.println("    [--start-testing] [--stop-testing] ");
        printWriter.println("    [--pretend-screen-on] [--pretend-screen-off] [--stop-pretend-screen]");
        printWriter.println("    [<package.name>]");
        printWriter.println("  --checkin: perform a checkin: print and delete old committed states.");
        printWriter.println("  -c: print only state in checkin format.");
        printWriter.println("  --csv: output data suitable for putting in a spreadsheet.");
        printWriter.println("  --csv-screen: on, off.");
        printWriter.println("  --csv-mem: norm, mod, low, crit.");
        printWriter.println("  --csv-proc: pers, top, fore, vis, precept, backup,");
        printWriter.println("    service, home, prev, cached");
        printWriter.println("  --details: dump per-package details, not just summary.");
        printWriter.println("  --full-details: dump all timing and active state details.");
        printWriter.println("  --current: only dump current state.");
        printWriter.println("  --hours: aggregate over about N last hours.");
        printWriter.println("  --last: only show the last committed stats at index N (starting at 1).");
        printWriter.println("  --max: for -a, max num of historical batches to print.");
        printWriter.println("  --active: only show currently active processes/services.");
        printWriter.println("  --commit: commit current stats to disk and reset to start new stats.");
        printWriter.println("  --section: proc|pkg-proc|pkg-svc|pkg-asc|pkg-all|all ");
        printWriter.println("    options can be combined to select desired stats");
        printWriter.println("  --reset: reset current stats, without committing.");
        printWriter.println("  --clear: clear all stats; does both --reset and deletes old stats.");
        printWriter.println("  --write: write current in-memory stats to disk.");
        printWriter.println("  --read: replace current stats with last-written stats.");
        printWriter.println("  --start-testing: clear all stats and starting high frequency pss sampling.");
        printWriter.println("  --stop-testing: stop high frequency pss sampling.");
        printWriter.println("  --pretend-screen-on: pretend screen is on.");
        printWriter.println("  --pretend-screen-off: pretend screen is off.");
        printWriter.println("  --stop-pretend-screen: forget \"pretend screen\" and use the real state.");
        printWriter.println("  -a: print everything.");
        printWriter.println("  -h: print this help text.");
        printWriter.println("  <package.name>: optional name of package to filter output by.");
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpAndUsageStatsPermission(this.mAm.mContext, TAG, printWriter)) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (strArr.length > 0) {
                    if ("--proto".equals(strArr[0])) {
                        dumpProto(fileDescriptor);
                        return;
                    } else if ("--statsd".equals(strArr[0])) {
                        dumpProtoForStatsd(fileDescriptor);
                        return;
                    }
                }
                dumpInner(printWriter, strArr);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:355:0x070c  */
    /* JADX WARN: Removed duplicated region for block: B:389:0x07a1 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:424:0x06a6 A[Catch: all -> 0x06c4, TRY_LEAVE, TryCatch #4 {all -> 0x06c4, blocks: (B:422:0x06a1, B:424:0x06a6), top: B:421:0x06a1 }] */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v13 */
    /* JADX WARN: Type inference failed for: r12v7, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r31v0 */
    /* JADX WARN: Type inference failed for: r31v1 */
    /* JADX WARN: Type inference failed for: r31v2 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v6 */
    @dalvik.annotation.optimization.NeverCompile
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void dumpInner(java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        int[] iArr;
        int i;
        java.lang.String str;
        boolean z;
        int i2;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        int[] iArr2;
        int[] iArr3;
        int i3;
        boolean z12;
        java.lang.Object obj;
        java.lang.Object obj2;
        int size;
        int i4;
        java.util.ArrayList<java.lang.String> arrayList;
        int i5;
        java.lang.String str2;
        java.lang.Object obj3;
        java.lang.Object obj4;
        ?? r31;
        com.android.server.am.ProcessStatsService processStatsService = this;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        int i6 = 2;
        int i7 = 1;
        int i8 = 4;
        int[] iArr4 = {0, 4};
        int[] iArr5 = {3};
        int[] iArr6 = com.android.internal.app.procstats.ProcessStats.ALL_PROC_STATES;
        int i9 = 31;
        if (strArr != null) {
            int i10 = 0;
            boolean z13 = false;
            z4 = false;
            boolean z14 = false;
            int i11 = 0;
            int i12 = 0;
            z5 = false;
            z6 = false;
            z7 = false;
            z8 = false;
            z9 = false;
            z10 = false;
            boolean z15 = false;
            z11 = false;
            boolean z16 = true;
            java.lang.String str3 = null;
            while (i10 < strArr.length) {
                java.lang.String str4 = strArr[i10];
                if ("--checkin".equals(str4)) {
                    z10 = i7;
                } else if ("-c".equals(str4)) {
                    z5 = i7;
                } else if ("--csv".equals(str4)) {
                    z4 = i7;
                } else if ("--csv-screen".equals(str4)) {
                    i10++;
                    if (i10 >= strArr.length) {
                        printWriter.println("Error: argument required for --csv-screen");
                        dumpHelp(printWriter);
                        return;
                    }
                    boolean[] zArr = new boolean[i7];
                    java.lang.String[] strArr2 = new java.lang.String[i7];
                    int[] parseStateList = parseStateList(com.android.internal.app.procstats.DumpUtils.ADJ_SCREEN_NAMES_CSV, i8, strArr[i10], zArr, strArr2);
                    if (parseStateList == null) {
                        printWriter.println("Error in \"" + strArr[i10] + "\": " + strArr2[0]);
                        dumpHelp(printWriter);
                        return;
                    }
                    z13 = zArr[0];
                    iArr4 = parseStateList;
                } else if ("--csv-mem".equals(str4)) {
                    i10++;
                    if (i10 >= strArr.length) {
                        printWriter.println("Error: argument required for --csv-mem");
                        dumpHelp(printWriter);
                        return;
                    }
                    boolean[] zArr2 = new boolean[i7];
                    java.lang.String[] strArr3 = new java.lang.String[i7];
                    int[] parseStateList2 = parseStateList(com.android.internal.app.procstats.DumpUtils.ADJ_MEM_NAMES_CSV, i7, strArr[i10], zArr2, strArr3);
                    if (parseStateList2 == null) {
                        printWriter.println("Error in \"" + strArr[i10] + "\": " + strArr3[0]);
                        dumpHelp(printWriter);
                        return;
                    }
                    z15 = zArr2[0];
                    iArr5 = parseStateList2;
                } else if ("--csv-proc".equals(str4)) {
                    i10++;
                    if (i10 >= strArr.length) {
                        printWriter.println("Error: argument required for --csv-proc");
                        dumpHelp(printWriter);
                        return;
                    }
                    boolean[] zArr3 = new boolean[i7];
                    java.lang.String[] strArr4 = new java.lang.String[i7];
                    int[] parseStateList3 = parseStateList(com.android.internal.app.procstats.DumpUtils.STATE_NAMES_CSV, i7, strArr[i10], zArr3, strArr4);
                    if (parseStateList3 == null) {
                        printWriter.println("Error in \"" + strArr[i10] + "\": " + strArr4[0]);
                        dumpHelp(printWriter);
                        return;
                    }
                    z16 = zArr3[0];
                    iArr6 = parseStateList3;
                } else if ("--details".equals(str4)) {
                    z6 = i7;
                } else if ("--full-details".equals(str4)) {
                    z7 = i7;
                } else if ("--hours".equals(str4)) {
                    i10++;
                    if (i10 >= strArr.length) {
                        printWriter.println("Error: argument required for --hours");
                        dumpHelp(printWriter);
                        return;
                    }
                    try {
                        i11 = java.lang.Integer.parseInt(strArr[i10]);
                    } catch (java.lang.NumberFormatException e) {
                        printWriter.println("Error: --hours argument not an int -- " + strArr[i10]);
                        dumpHelp(printWriter);
                        return;
                    }
                } else if ("--last".equals(str4)) {
                    i10++;
                    if (i10 >= strArr.length) {
                        printWriter.println("Error: argument required for --last");
                        dumpHelp(printWriter);
                        return;
                    }
                    try {
                        i12 = java.lang.Integer.parseInt(strArr[i10]);
                    } catch (java.lang.NumberFormatException e2) {
                        printWriter.println("Error: --last argument not an int -- " + strArr[i10]);
                        dumpHelp(printWriter);
                        return;
                    }
                } else if ("--max".equals(str4)) {
                    i10++;
                    if (i10 >= strArr.length) {
                        printWriter.println("Error: argument required for --max");
                        dumpHelp(printWriter);
                        return;
                    }
                    try {
                        i6 = java.lang.Integer.parseInt(strArr[i10]);
                    } catch (java.lang.NumberFormatException e3) {
                        printWriter.println("Error: --max argument not an int -- " + strArr[i10]);
                        dumpHelp(printWriter);
                        return;
                    }
                } else if ("--active".equals(str4)) {
                    z9 = i7;
                    z11 = z9;
                } else if ("--current".equals(str4)) {
                    z11 = i7;
                } else if ("--commit".equals(str4)) {
                    synchronized (processStatsService.mLock) {
                        processStatsService.mProcessStats.mFlags |= i7;
                        processStatsService.writeStateLocked(i7, i7);
                        printWriter.println("Process stats committed.");
                    }
                    z14 = i7;
                } else if ("--section".equals(str4)) {
                    i10++;
                    if (i10 >= strArr.length) {
                        printWriter.println("Error: argument required for --section");
                        dumpHelp(printWriter);
                        return;
                    }
                    i9 = parseSectionOptions(strArr[i10]);
                } else if ("--clear".equals(str4)) {
                    synchronized (processStatsService.mLock) {
                        try {
                            processStatsService.mProcessStats.resetSafely();
                            processStatsService.scheduleRequestPssAllProcs(i7, false);
                            processStatsService.mFileLock.lock();
                            try {
                                java.util.ArrayList<java.lang.String> committedFilesLF = processStatsService.getCommittedFilesLF(0, i7, i7);
                                if (committedFilesLF != null) {
                                    for (int size2 = committedFilesLF.size() - i7; size2 >= 0; size2--) {
                                        new java.io.File(committedFilesLF.get(size2)).delete();
                                    }
                                }
                                processStatsService.mFileLock.unlock();
                                printWriter.println("All process stats cleared.");
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        } finally {
                        }
                    }
                    z14 = true;
                } else if ("--write".equals(str4)) {
                    synchronized (processStatsService.mLock) {
                        writeStateSyncLocked();
                        printWriter.println("Process stats written.");
                    }
                    z14 = true;
                } else if ("--read".equals(str4)) {
                    synchronized (processStatsService.mLock) {
                        try {
                            processStatsService.mFileLock.lock();
                            try {
                                processStatsService.readLF(processStatsService.mProcessStats, processStatsService.mFile);
                                printWriter.println("Process stats read.");
                            } catch (java.lang.Throwable th2) {
                                throw th2;
                            }
                        } finally {
                        }
                    }
                    z14 = true;
                } else if ("--start-testing".equals(str4)) {
                    processStatsService.mAm.mAppProfiler.setTestPssMode(true);
                    printWriter.println("Started high frequency sampling.");
                    z14 = true;
                } else if ("--stop-testing".equals(str4)) {
                    processStatsService.mAm.mAppProfiler.setTestPssMode(false);
                    printWriter.println("Stopped high frequency sampling.");
                    z14 = true;
                } else if ("--pretend-screen-on".equals(str4)) {
                    synchronized (processStatsService.mLock) {
                        processStatsService.mInjectedScreenState = true;
                    }
                    z14 = true;
                } else if ("--pretend-screen-off".equals(str4)) {
                    synchronized (processStatsService.mLock) {
                        processStatsService.mInjectedScreenState = false;
                    }
                    z14 = true;
                } else if ("--stop-pretend-screen".equals(str4)) {
                    synchronized (processStatsService.mLock) {
                        processStatsService.mInjectedScreenState = null;
                    }
                    z14 = true;
                } else {
                    if ("-h".equals(str4)) {
                        dumpHelp(printWriter);
                        return;
                    }
                    if ("-a".equals(str4)) {
                        z6 = true;
                        z8 = true;
                    } else {
                        if (str4.length() > 0 && str4.charAt(0) == '-') {
                            printWriter.println("Unknown option: " + str4);
                            dumpHelp(printWriter);
                            return;
                        }
                        str3 = str4;
                        z6 = true;
                    }
                }
                i10++;
                i7 = 1;
                i8 = 4;
            }
            i = i9;
            z = z14;
            i2 = i12;
            str = str3;
            z2 = z15;
            z3 = z16;
            iArr3 = iArr5;
            z12 = z13;
            iArr = iArr6;
            iArr2 = iArr4;
            i3 = i11;
        } else {
            iArr = iArr6;
            i = 31;
            str = null;
            z = false;
            i2 = 0;
            z2 = false;
            z3 = true;
            z4 = false;
            z5 = false;
            z6 = false;
            z7 = false;
            z8 = false;
            z9 = false;
            z10 = false;
            z11 = false;
            iArr2 = iArr4;
            iArr3 = iArr5;
            i3 = 0;
            z12 = false;
        }
        if (z) {
            return;
        }
        if (z4) {
            printWriter.print("Processes running summed over");
            if (!z12) {
                for (int i13 : iArr2) {
                    printWriter.print(" ");
                    com.android.internal.app.procstats.DumpUtils.printScreenLabelCsv(printWriter, i13);
                }
            }
            if (!z2) {
                for (int i14 : iArr3) {
                    printWriter.print(" ");
                    com.android.internal.app.procstats.DumpUtils.printMemLabelCsv(printWriter, i14);
                }
            }
            if (!z3) {
                for (int i15 : iArr) {
                    printWriter.print(" ");
                    printWriter.print(com.android.internal.app.procstats.DumpUtils.STATE_NAMES_CSV[i15]);
                }
            }
            printWriter.println();
            synchronized (processStatsService.mLock) {
                dumpFilteredProcessesCsvLocked(printWriter, null, z12, iArr2, z2, iArr3, z3, iArr, uptimeMillis, str);
            }
            return;
        }
        if (i3 != 0) {
            printWriter.print("AGGREGATED OVER LAST ");
            printWriter.print(i3);
            printWriter.println(" HOURS:");
            dumpAggregatedStats(printWriter, i3, uptimeMillis, str, z5, z6, z7, z8, z9, i);
            return;
        }
        if (i2 > 0) {
            printWriter.print("LAST STATS AT INDEX ");
            printWriter.print(i2);
            printWriter.println(":");
            processStatsService.mFileLock.lock();
            try {
                java.util.ArrayList<java.lang.String> committedFilesLF2 = processStatsService.getCommittedFilesLF(0, false, true);
                if (i2 >= committedFilesLF2.size()) {
                    printWriter.print("Only have ");
                    printWriter.print(committedFilesLF2.size());
                    printWriter.println(" data sets");
                    return;
                }
                android.util.AtomicFile atomicFile = new android.util.AtomicFile(new java.io.File(committedFilesLF2.get(i2)));
                com.android.internal.app.procstats.ProcessStats processStats = new com.android.internal.app.procstats.ProcessStats(false);
                processStatsService.readLF(processStats, atomicFile);
                processStatsService.mFileLock.unlock();
                if (processStats.mReadError != null) {
                    if (z10 || z5) {
                        printWriter.print("err,");
                    }
                    printWriter.print("Failure reading ");
                    printWriter.print(committedFilesLF2.get(i2));
                    printWriter.print("; ");
                    printWriter.println(processStats.mReadError);
                    return;
                }
                boolean endsWith = atomicFile.getBaseFile().getPath().endsWith(STATE_FILE_CHECKIN_SUFFIX);
                if (z10 || z5) {
                    processStats.dumpCheckinLocked(printWriter, str, i);
                    return;
                }
                printWriter.print("COMMITTED STATS FROM ");
                printWriter.print(processStats.mTimePeriodStartClockStr);
                if (endsWith) {
                    printWriter.print(" (checked in)");
                }
                printWriter.println(":");
                if (!z6 && !z7) {
                    processStats.dumpSummaryLocked(printWriter, str, uptimeMillis, z9);
                    return;
                }
                processStats.dumpLocked(printWriter, str, uptimeMillis, !z7, z6, z8, z9, i);
                if (z8) {
                    printWriter.print("  mFile=");
                    printWriter.println(getCurrentFile());
                    return;
                }
                return;
            } finally {
                processStatsService.mFileLock.unlock();
            }
        }
        ?? r11 = 1;
        if (!z8 && !z10) {
            obj = null;
        } else if (z11) {
            obj = null;
        } else {
            processStatsService.mFileLock.lock();
            try {
                java.util.ArrayList<java.lang.String> committedFilesLF3 = processStatsService.getCommittedFilesLF(0, false, !z10);
                if (committedFilesLF3 != null) {
                    if (z10) {
                        size = 0;
                    } else {
                        try {
                            size = committedFilesLF3.size() - i6;
                        } catch (java.lang.Throwable th3) {
                            th = th3;
                            processStatsService = this;
                            throw th;
                        }
                    }
                    if (size < 0) {
                        size = 0;
                    }
                    int i16 = size;
                    java.lang.Object obj5 = null;
                    while (i16 < committedFilesLF3.size()) {
                        try {
                            android.util.AtomicFile atomicFile2 = new android.util.AtomicFile(new java.io.File(committedFilesLF3.get(i16)));
                            try {
                                com.android.internal.app.procstats.ProcessStats processStats2 = new com.android.internal.app.procstats.ProcessStats(false);
                                processStatsService.readLF(processStats2, atomicFile2);
                                if (processStats2.mReadError != null) {
                                    if (z10 || z5) {
                                        printWriter.print("err,");
                                    }
                                    printWriter.print("Failure reading ");
                                    printWriter.print(committedFilesLF3.get(i16));
                                    printWriter.print("; ");
                                    printWriter.println(processStats2.mReadError);
                                    new java.io.File(committedFilesLF3.get(i16)).delete();
                                    i5 = i16;
                                    arrayList = committedFilesLF3;
                                } else {
                                    java.lang.String path = atomicFile2.getBaseFile().getPath();
                                    boolean endsWith2 = path.endsWith(STATE_FILE_CHECKIN_SUFFIX);
                                    try {
                                        if (z10) {
                                            str2 = path;
                                            i4 = i16;
                                            arrayList = committedFilesLF3;
                                        } else if (z5) {
                                            str2 = path;
                                            i4 = i16;
                                            arrayList = committedFilesLF3;
                                        } else {
                                            if (obj5 != null) {
                                                printWriter.println();
                                                obj3 = obj5;
                                            } else {
                                                obj3 = r11;
                                            }
                                            try {
                                                printWriter.print("COMMITTED STATS FROM ");
                                                printWriter.print(processStats2.mTimePeriodStartClockStr);
                                                if (endsWith2) {
                                                    printWriter.print(" (checked in)");
                                                }
                                                printWriter.println(":");
                                                if (z7) {
                                                    str2 = path;
                                                    i4 = i16;
                                                    arrayList = committedFilesLF3;
                                                    try {
                                                        processStats2.dumpLocked(printWriter, str, uptimeMillis, false, false, false, z9, i);
                                                    } catch (java.lang.Throwable th4) {
                                                        th = th4;
                                                        obj5 = obj3;
                                                        printWriter.print("**** FAILURE DUMPING STATE: ");
                                                        i5 = i4;
                                                        printWriter.println(arrayList.get(i5));
                                                        th.printStackTrace(printWriter);
                                                        i16 = i5 + 1;
                                                        r11 = 1;
                                                        committedFilesLF3 = arrayList;
                                                        processStatsService = this;
                                                    }
                                                } else {
                                                    str2 = path;
                                                    i4 = i16;
                                                    arrayList = committedFilesLF3;
                                                    processStats2.dumpSummaryLocked(printWriter, str, uptimeMillis, z9);
                                                }
                                                obj5 = obj3;
                                                if (z10) {
                                                    atomicFile2.getBaseFile().renameTo(new java.io.File(str2 + STATE_FILE_CHECKIN_SUFFIX));
                                                }
                                                i5 = i4;
                                            } catch (java.lang.Throwable th5) {
                                                th = th5;
                                                i4 = i16;
                                                arrayList = committedFilesLF3;
                                            }
                                        }
                                        processStats2.dumpCheckinLocked(printWriter, str, i);
                                        if (z10) {
                                        }
                                        i5 = i4;
                                    } catch (java.lang.Throwable th6) {
                                        th = th6;
                                        printWriter.print("**** FAILURE DUMPING STATE: ");
                                        i5 = i4;
                                        printWriter.println(arrayList.get(i5));
                                        th.printStackTrace(printWriter);
                                        i16 = i5 + 1;
                                        r11 = 1;
                                        committedFilesLF3 = arrayList;
                                        processStatsService = this;
                                    }
                                }
                            } catch (java.lang.Throwable th7) {
                                th = th7;
                                i4 = i16;
                                arrayList = committedFilesLF3;
                            }
                        } catch (java.lang.Throwable th8) {
                            th = th8;
                            i4 = i16;
                            arrayList = committedFilesLF3;
                        }
                        i16 = i5 + 1;
                        r11 = 1;
                        committedFilesLF3 = arrayList;
                        processStatsService = this;
                    }
                    obj = null;
                    obj2 = obj5;
                } else {
                    obj = null;
                    obj2 = null;
                }
                processStatsService = this;
                if (z10) {
                    java.lang.Object obj6 = processStatsService.mLock;
                    synchronized (obj6) {
                        try {
                            try {
                                if (z5) {
                                    processStatsService.mProcessStats.dumpCheckinLocked(printWriter, str, i);
                                    r31 = obj2;
                                    obj4 = obj6;
                                } else {
                                    if (obj2 != null) {
                                        printWriter.println();
                                    }
                                    printWriter.println("CURRENT STATS:");
                                    if (z6 || z7) {
                                        obj4 = obj6;
                                        processStatsService.mProcessStats.dumpLocked(printWriter, str, uptimeMillis, (boolean) (!z7 ? 1 : obj), z6, z8, z9, i);
                                        if (z8) {
                                            printWriter.print("  mFile=");
                                            printWriter.println(getCurrentFile());
                                        }
                                    } else {
                                        processStatsService.mProcessStats.dumpSummaryLocked(printWriter, str, uptimeMillis, z9);
                                        obj4 = obj6;
                                    }
                                    r31 = 1;
                                }
                                if (z11) {
                                    return;
                                }
                                if (r31 != 0) {
                                    printWriter.println();
                                }
                                printWriter.println("AGGREGATED OVER LAST 24 HOURS:");
                                boolean z17 = z5;
                                boolean z18 = z6;
                                boolean z19 = z7;
                                boolean z20 = z8;
                                int i17 = i;
                                boolean z21 = z9;
                                dumpAggregatedStats(printWriter, 24L, uptimeMillis, str, z17, z18, z19, z20, z21, i17);
                                printWriter.println();
                                printWriter.println("AGGREGATED OVER LAST 3 HOURS:");
                                dumpAggregatedStats(printWriter, 3L, uptimeMillis, str, z17, z18, z19, z20, z21, i17);
                                return;
                            } catch (java.lang.Throwable th9) {
                                th = th9;
                                obj2 = obj6;
                                throw th;
                            }
                        } catch (java.lang.Throwable th10) {
                            th = th10;
                            throw th;
                        }
                    }
                }
                return;
            } catch (java.lang.Throwable th11) {
                th = th11;
            }
        }
        obj2 = obj;
        if (z10) {
        }
    }

    private void dumpAggregatedStats(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i, long j2) {
        android.os.ParcelFileDescriptor statsOverTime = getStatsOverTime((((i * 60) * 60) * 1000) - (com.android.internal.app.procstats.ProcessStats.COMMIT_PERIOD / 2));
        if (statsOverTime == null) {
            return;
        }
        com.android.internal.app.procstats.ProcessStats processStats = new com.android.internal.app.procstats.ProcessStats(false);
        processStats.read(new android.os.ParcelFileDescriptor.AutoCloseInputStream(statsOverTime));
        if (processStats.mReadError != null) {
            return;
        }
        long start = protoOutputStream.start(j);
        processStats.dumpDebug(protoOutputStream, j2, 31);
        protoOutputStream.end(start);
    }

    private void dumpProto(java.io.FileDescriptor fileDescriptor) {
        long uptimeMillis;
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        synchronized (this.mLock) {
            uptimeMillis = android.os.SystemClock.uptimeMillis();
            long start = protoOutputStream.start(1146756268033L);
            this.mProcessStats.dumpDebug(protoOutputStream, uptimeMillis, 31);
            protoOutputStream.end(start);
        }
        dumpAggregatedStats(protoOutputStream, 1146756268034L, 3, uptimeMillis);
        dumpAggregatedStats(protoOutputStream, 1146756268035L, 24, uptimeMillis);
        protoOutputStream.flush();
    }

    private void dumpProtoForStatsd(java.io.FileDescriptor fileDescriptor) {
        android.util.proto.ProtoOutputStream[] protoOutputStreamArr = {new android.util.proto.ProtoOutputStream(fileDescriptor)};
        com.android.internal.app.procstats.ProcessStats processStats = new com.android.internal.app.procstats.ProcessStats(false);
        getCommittedStatsMerged(0L, 0, true, null, processStats);
        processStats.dumpAggregatedProtoForStatsd(protoOutputStreamArr, 999999L);
        protoOutputStreamArr[0].flush();
    }
}
