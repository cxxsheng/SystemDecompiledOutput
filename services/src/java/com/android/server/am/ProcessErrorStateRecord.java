package com.android.server.am;

/* loaded from: classes.dex */
class ProcessErrorStateRecord {
    private static final java.time.format.DateTimeFormatter DROPBOX_TIME_FORMATTER = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSZ");

    @com.android.internal.annotations.GuardedBy({"mService"})
    private java.lang.String mAnrAnnotation;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private com.android.server.am.AppNotRespondingDialog.Data mAnrData;
    final com.android.server.am.ProcessRecord mApp;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mBad;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private java.lang.Runnable mCrashHandler;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mCrashing;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private android.app.ActivityManager.ProcessErrorStateInfo mCrashingReport;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private final com.android.server.am.ErrorDialogController mDialogController;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private android.content.ComponentName mErrorReportReceiver;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mForceCrashReport;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private boolean mNotResponding;

    @com.android.internal.annotations.CompositeRWLock({"mService", "mProcLock"})
    private android.app.ActivityManager.ProcessErrorStateInfo mNotRespondingReport;
    private final com.android.server.am.ActivityManagerGlobalLock mProcLock;
    private final com.android.server.am.ActivityManagerService mService;

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isBad() {
        return this.mBad;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setBad(boolean z) {
        this.mBad = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isCrashing() {
        return this.mCrashing;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setCrashing(boolean z) {
        this.mCrashing = z;
        this.mApp.getWindowProcessController().setCrashing(z);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isForceCrashReport() {
        return this.mForceCrashReport;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setForceCrashReport(boolean z) {
        this.mForceCrashReport = z;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    boolean isNotResponding() {
        return this.mNotResponding;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setNotResponding(boolean z) {
        this.mNotResponding = z;
        this.mApp.getWindowProcessController().setNotResponding(z);
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    java.lang.Runnable getCrashHandler() {
        return this.mCrashHandler;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setCrashHandler(java.lang.Runnable runnable) {
        this.mCrashHandler = runnable;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    android.app.ActivityManager.ProcessErrorStateInfo getCrashingReport() {
        return this.mCrashingReport;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setCrashingReport(android.app.ActivityManager.ProcessErrorStateInfo processErrorStateInfo) {
        this.mCrashingReport = processErrorStateInfo;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    java.lang.String getAnrAnnotation() {
        return this.mAnrAnnotation;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void setAnrAnnotation(java.lang.String str) {
        this.mAnrAnnotation = str;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    android.app.ActivityManager.ProcessErrorStateInfo getNotRespondingReport() {
        return this.mNotRespondingReport;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setNotRespondingReport(android.app.ActivityManager.ProcessErrorStateInfo processErrorStateInfo) {
        this.mNotRespondingReport = processErrorStateInfo;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    android.content.ComponentName getErrorReportReceiver() {
        return this.mErrorReportReceiver;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setErrorReportReceiver(android.content.ComponentName componentName) {
        this.mErrorReportReceiver = componentName;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.ErrorDialogController getDialogController() {
        return this.mDialogController;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void setAnrData(com.android.server.am.AppNotRespondingDialog.Data data) {
        this.mAnrData = data;
    }

    @com.android.internal.annotations.GuardedBy(anyOf = {"mService", "mProcLock"})
    com.android.server.am.AppNotRespondingDialog.Data getAnrData() {
        return this.mAnrData;
    }

    ProcessErrorStateRecord(com.android.server.am.ProcessRecord processRecord) {
        this.mApp = processRecord;
        this.mService = processRecord.mService;
        this.mProcLock = this.mService.mProcLock;
        this.mDialogController = new com.android.server.am.ErrorDialogController(processRecord);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean skipAnrLocked(java.lang.String str) {
        if (this.mService.mAtmInternal.isShuttingDown()) {
            android.util.Slog.i("ActivityManager", "During shutdown skipping ANR: " + this + " " + str);
            return true;
        }
        if (isNotResponding()) {
            android.util.Slog.i("ActivityManager", "Skipping duplicate ANR: " + this + " " + str);
            return true;
        }
        if (isCrashing()) {
            android.util.Slog.i("ActivityManager", "Crashing app skipping ANR: " + this + " " + str);
            return true;
        }
        if (this.mApp.isKilledByAm()) {
            android.util.Slog.i("ActivityManager", "App already killed by AM skipping ANR: " + this + " " + str);
            return true;
        }
        if (this.mApp.isKilled()) {
            android.util.Slog.i("ActivityManager", "Skipping died app ANR: " + this + " " + str);
            return true;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x043e  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0458  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0469  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0477  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x047e  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x048a  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0492  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x04aa  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x04b5  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x04c0  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x04cb  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x04d6  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x04e1  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x04ec  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x04f7  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0513  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x056c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x056d  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0519  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x04fe  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x04f3  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x04e8  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x04dd  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x04d2  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x04c7  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x04bc  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x04b1  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0499  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x048f  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0485  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x047a  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0472  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x045b  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x044e  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0435  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0416  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x042f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void appNotResponding(java.lang.String str, android.content.pm.ApplicationInfo applicationInfo, java.lang.String str2, com.android.server.wm.WindowProcessController windowProcessController, boolean z, com.android.internal.os.TimeoutRecord timeoutRecord, java.util.concurrent.ExecutorService executorService, final boolean z2, boolean z3, java.util.concurrent.Future<java.io.File> future) {
        java.util.concurrent.Future<?> future2;
        com.android.server.am.ActivityManagerService.VolatileDropboxEntryStates withProcessFrozenState;
        java.util.UUID uuid;
        long j;
        long j2;
        android.os.incremental.IncrementalMetrics incrementalMetrics;
        int i;
        int i2;
        java.lang.String str3;
        final int i3;
        final java.lang.String str4 = timeoutRecord.mReason;
        final com.android.internal.os.anr.AnrLatencyTracker anrLatencyTracker = timeoutRecord.mLatencyTracker;
        final java.util.ArrayList arrayList = new java.util.ArrayList(5);
        final android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray(20);
        if (this.mApp.isDebugging()) {
            android.util.Slog.i("ActivityManager", "Skipping debugged app ANR: " + this + " " + str4);
            return;
        }
        this.mApp.getWindowProcessController().appEarlyNotResponding(str4, new java.lang.Runnable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.ProcessErrorStateRecord.this.lambda$appNotResponding$0(anrLatencyTracker, str4);
            }
        });
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (isMonitorCpuUsage()) {
            future2 = executorService.submit(new java.lang.Runnable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.ProcessErrorStateRecord.this.lambda$appNotResponding$1(anrLatencyTracker);
                }
            });
        } else {
            future2 = null;
        }
        anrLatencyTracker.waitingOnAMSLockStarted();
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                anrLatencyTracker.waitingOnAMSLockEnded();
                final int pid = this.mApp.getPid();
                setAnrAnnotation(str4);
                com.android.modules.expresslog.Counter.logIncrement("stability_anr.value_total_anrs");
                if (skipAnrLocked(str4)) {
                    anrLatencyTracker.anrSkippedProcessErrorStateRecordAppNotResponding();
                    com.android.modules.expresslog.Counter.logIncrement("stability_anr.value_skipped_anrs");
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                anrLatencyTracker.waitingOnProcLockStarted();
                com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        anrLatencyTracker.waitingOnProcLockEnded();
                        setNotResponding(true);
                        withProcessFrozenState = com.android.server.am.ActivityManagerService.VolatileDropboxEntryStates.withProcessFrozenState(this.mApp.mOptRecord.isFrozen());
                    } finally {
                        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                    }
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.AM_ANR, java.lang.Integer.valueOf(this.mApp.userId), java.lang.Integer.valueOf(pid), this.mApp.processName, java.lang.Integer.valueOf(this.mApp.info.flags), str4);
                if (this.mService.mTraceErrorLogger != null && this.mService.mTraceErrorLogger.isAddErrorIdEnabled()) {
                    java.util.UUID generateErrorId = this.mService.mTraceErrorLogger.generateErrorId();
                    this.mService.mTraceErrorLogger.addProcessInfoAndErrorIdToTrace(this.mApp.processName, pid, generateErrorId);
                    this.mService.mTraceErrorLogger.addSubjectToTrace(str4, generateErrorId);
                    uuid = generateErrorId;
                } else {
                    uuid = null;
                }
                com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.ANR_OCCURRED_PROCESSING_STARTED, this.mApp.processName);
                arrayList.add(java.lang.Integer.valueOf(pid));
                final boolean isSilentAnr = isSilentAnr();
                if (!isSilentAnr && !z2) {
                    if (windowProcessController != null && windowProcessController.getPid() > 0) {
                        i3 = windowProcessController.getPid();
                    } else {
                        i3 = pid;
                    }
                    if (i3 != pid) {
                        arrayList.add(java.lang.Integer.valueOf(i3));
                    }
                    if (com.android.server.am.ActivityManagerService.MY_PID != pid && com.android.server.am.ActivityManagerService.MY_PID != i3) {
                        arrayList.add(java.lang.Integer.valueOf(com.android.server.am.ActivityManagerService.MY_PID));
                    }
                    this.mService.mProcessList.forEachLruProcessesLOSP(false, new java.util.function.Consumer() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.am.ProcessErrorStateRecord.lambda$appNotResponding$2(pid, i3, arrayList, sparseBooleanArray, (com.android.server.am.ProcessRecord) obj);
                        }
                    });
                }
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                java.lang.String buildMemoryHeadersFor = buildMemoryHeadersFor(pid);
                anrLatencyTracker.criticalEventLogStarted();
                java.lang.String logLinesForTraceFile = com.android.server.criticalevents.CriticalEventLog.getInstance().logLinesForTraceFile(this.mApp.getProcessClassEnum(), this.mApp.processName, this.mApp.uid);
                anrLatencyTracker.criticalEventLogEnded();
                com.android.server.criticalevents.CriticalEventLog.getInstance().logAnr(str4, this.mApp.getProcessClassEnum(), this.mApp.processName, this.mApp.uid, this.mApp.mPid);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.setLength(0);
                sb.append("ANR in ");
                sb.append(this.mApp.processName);
                if (str != null) {
                    sb.append(" (");
                    sb.append(str);
                    sb.append(")");
                }
                sb.append("\n");
                sb.append("PID: ");
                sb.append(pid);
                sb.append("\n");
                if (str4 != null) {
                    sb.append("Reason: ");
                    sb.append(str4);
                    sb.append("\n");
                }
                if (str2 != null && str2.equals(str)) {
                    sb.append("Parent: ");
                    sb.append(str2);
                    sb.append("\n");
                }
                if (uuid != null) {
                    sb.append("ErrorId: ");
                    sb.append(uuid.toString());
                    sb.append("\n");
                }
                sb.append("Frozen: ");
                sb.append(this.mApp.mOptRecord.isFrozen());
                sb.append("\n");
                if (timeoutRecord.mEndUptimeMillis > 0) {
                    java.lang.String format = DROPBOX_TIME_FORMATTER.format(java.time.Instant.now().minusMillis(uptimeMillis - timeoutRecord.mEndUptimeMillis).atZone(java.time.ZoneId.systemDefault()));
                    sb.append("Timestamp: ");
                    sb.append(format);
                    sb.append("\n");
                }
                android.app.AnrController anrController = this.mService.mActivityTaskManager.getAnrController(applicationInfo);
                if (anrController == null) {
                    j = uptimeMillis;
                    j2 = 0;
                } else {
                    java.lang.String str5 = applicationInfo.packageName;
                    int i4 = applicationInfo.uid;
                    j2 = anrController.getAnrDelayMillis(str5, i4);
                    anrController.onAnrDelayStarted(str5, i4);
                    j = uptimeMillis;
                    android.util.Slog.i("ActivityManager", "ANR delay of " + j2 + "ms started for " + str5);
                }
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                anrLatencyTracker.currentPsiStateCalled();
                java.lang.String currentPsiState = com.android.server.ResourcePressureUtil.currentPsiState();
                anrLatencyTracker.currentPsiStateReturned();
                sb2.append(currentPsiState);
                com.android.internal.os.ProcessCpuTracker processCpuTracker = new com.android.internal.os.ProcessCpuTracker(true);
                long j3 = j;
                java.util.concurrent.Future submit = executorService.submit(new java.util.concurrent.Callable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda3
                    @Override // java.util.concurrent.Callable
                    public final java.lang.Object call() {
                        java.util.ArrayList lambda$appNotResponding$3;
                        lambda$appNotResponding$3 = com.android.server.am.ProcessErrorStateRecord.this.lambda$appNotResponding$3(anrLatencyTracker, isSilentAnr, z2);
                        return lambda$appNotResponding$3;
                    }
                });
                java.io.StringWriter stringWriter = new java.io.StringWriter();
                long j4 = j2;
                java.util.concurrent.atomic.AtomicLong atomicLong = new java.util.concurrent.atomic.AtomicLong(-1L);
                java.io.File dumpStackTraces = com.android.server.am.StackTracesDumpHelper.dumpStackTraces(arrayList, isSilentAnr ? null : processCpuTracker, isSilentAnr ? null : sparseBooleanArray, submit, stringWriter, atomicLong, str4, logLinesForTraceFile, buildMemoryHeadersFor, executorService, future, anrLatencyTracker);
                if (isMonitorCpuUsage()) {
                    try {
                        future2.get();
                    } catch (java.lang.InterruptedException e) {
                        android.util.Slog.w("ActivityManager", "Interrupted while updating the CPU stats", e);
                    } catch (java.util.concurrent.ExecutionException e2) {
                        android.util.Slog.w("ActivityManager", "Failed to update the CPU stats", e2.getCause());
                    }
                    this.mService.updateCpuStatsNow();
                    this.mService.mAppProfiler.printCurrentCpuState(sb2, j3);
                    synchronized (processCpuTracker) {
                        sb.append(processCpuTracker.printCurrentLoad());
                    }
                    sb.append((java.lang.CharSequence) sb2);
                }
                sb2.append(stringWriter.getBuffer());
                synchronized (processCpuTracker) {
                    sb.append(processCpuTracker.printCurrentState(j3));
                }
                android.util.Slog.e("ActivityManager", sb.toString());
                if (dumpStackTraces == null) {
                    android.os.Process.sendSignal(pid, 3);
                } else if (atomicLong.get() > 0) {
                    this.mService.mProcessList.mAppExitInfoTracker.scheduleLogAnrTrace(pid, this.mApp.uid, this.mApp.getPackageList(), dumpStackTraces, 0L, atomicLong.get());
                }
                android.content.pm.PackageManagerInternal packageManagerInternal = this.mService.getPackageManagerInternal();
                if (this.mApp.info != null && this.mApp.info.packageName != null && packageManagerInternal != null) {
                    android.content.pm.IncrementalStatesInfo incrementalStatesInfo = packageManagerInternal.getIncrementalStatesInfo(this.mApp.info.packageName, 1000, this.mApp.userId);
                    r5 = incrementalStatesInfo != null ? incrementalStatesInfo.getProgress() : 1.0f;
                    java.lang.String codePath = this.mApp.info.getCodePath();
                    if (codePath != null && !codePath.isEmpty() && android.os.incremental.IncrementalManager.isIncrementalPath(codePath)) {
                        android.util.Slog.e("ActivityManager", "App ANR on incremental package " + this.mApp.info.packageName + " which is " + ((int) (r5 * 100.0f)) + "% loaded.");
                        android.os.IBinder service = android.os.ServiceManager.getService("incremental");
                        if (service != null) {
                            incrementalMetrics = new android.os.incremental.IncrementalManager(android.os.incremental.IIncrementalService.Stub.asInterface(service)).getMetrics(codePath);
                            if (incrementalMetrics != null) {
                                sb.append("Package is ");
                                sb.append((int) (100.0f * r5));
                                sb.append("% loaded.\n");
                            }
                            int i5 = this.mApp.uid;
                            java.lang.String str6 = this.mApp.processName;
                            java.lang.String str7 = str != null ? "unknown" : str;
                            if (this.mApp.info == null) {
                                if (this.mApp.info.isInstantApp()) {
                                    i = 2;
                                } else {
                                    i = 1;
                                }
                            } else {
                                i = 0;
                            }
                            if (!this.mApp.isInterestingToUserLocked()) {
                                i2 = 2;
                            } else {
                                i2 = 1;
                            }
                            com.android.internal.util.FrameworkStatsLog.write(79, i5, str6, str7, str4, i, i2, this.mApp.getProcessClassEnum(), this.mApp.info == null ? this.mApp.info.packageName : "", incrementalMetrics == null, r5, incrementalMetrics == null ? incrementalMetrics.getMillisSinceOldestPendingRead() : -1L, incrementalMetrics == null ? incrementalMetrics.getStorageHealthStatusCode() : -1, incrementalMetrics == null ? incrementalMetrics.getDataLoaderStatusCode() : -1, incrementalMetrics == null && incrementalMetrics.getReadLogsEnabled(), incrementalMetrics == null ? incrementalMetrics.getMillisSinceLastDataLoaderBind() : -1L, incrementalMetrics == null ? incrementalMetrics.getDataLoaderBindDelayMillis() : -1L, incrementalMetrics == null ? incrementalMetrics.getTotalDelayedReads() : -1, incrementalMetrics == null ? incrementalMetrics.getTotalFailedReads() : -1, incrementalMetrics == null ? incrementalMetrics.getLastReadErrorUid() : -1, incrementalMetrics == null ? incrementalMetrics.getMillisSinceLastReadError() : -1L, incrementalMetrics == null ? incrementalMetrics.getLastReadErrorNumber() : 0, incrementalMetrics == null ? incrementalMetrics.getTotalDelayedReadsDurationMillis() : -1L);
                            this.mService.addErrorToDropBox("anr", this.mApp, this.mApp.processName, str, str2, windowProcessController == null ? (com.android.server.am.ProcessRecord) windowProcessController.mOwner : null, null, sb2.toString(), dumpStackTraces, null, new java.lang.Float(r5), incrementalMetrics, uuid, withProcessFrozenState);
                            if (!this.mApp.getWindowProcessController().appNotResponding(sb.toString(), new java.lang.Runnable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    com.android.server.am.ProcessErrorStateRecord.this.lambda$appNotResponding$4();
                                }
                            }, new java.lang.Runnable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    com.android.server.am.ProcessErrorStateRecord.this.lambda$appNotResponding$5();
                                }
                            })) {
                                return;
                            }
                            com.android.server.am.ActivityManagerService activityManagerService2 = this.mService;
                            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
                            synchronized (activityManagerService2) {
                                try {
                                    if (this.mService.mBatteryStatsService != null) {
                                        this.mService.mBatteryStatsService.noteProcessAnr(this.mApp.processName, this.mApp.uid);
                                    }
                                    if (isSilentAnr() && !this.mApp.isDebugging()) {
                                        this.mApp.killLocked("bg anr", 6, true);
                                        return;
                                    }
                                    com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock2 = this.mProcLock;
                                    com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
                                    synchronized (activityManagerGlobalLock2) {
                                        if (str4 != null) {
                                            try {
                                                str3 = "ANR " + str4;
                                            } catch (java.lang.Throwable th) {
                                                throw th;
                                            }
                                        } else {
                                            str3 = "ANR";
                                        }
                                        makeAppNotRespondingLSP(str, str3, sb.toString());
                                        this.mDialogController.setAnrController(anrController);
                                    }
                                    com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                                    if (this.mService.mUiHandler != null) {
                                        android.os.Message obtain = android.os.Message.obtain();
                                        obtain.what = 2;
                                        obtain.obj = new com.android.server.am.AppNotRespondingDialog.Data(this.mApp, applicationInfo, z, z3);
                                        this.mService.mUiHandler.sendMessageDelayed(obtain, j4);
                                    }
                                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                    return;
                                } catch (java.lang.Throwable th2) {
                                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                                    throw th2;
                                }
                            }
                        }
                    }
                }
                incrementalMetrics = null;
                if (incrementalMetrics != null) {
                }
                int i52 = this.mApp.uid;
                java.lang.String str62 = this.mApp.processName;
                if (str != null) {
                }
                if (this.mApp.info == null) {
                }
                if (!this.mApp.isInterestingToUserLocked()) {
                }
                com.android.internal.util.FrameworkStatsLog.write(79, i52, str62, str7, str4, i, i2, this.mApp.getProcessClassEnum(), this.mApp.info == null ? this.mApp.info.packageName : "", incrementalMetrics == null, r5, incrementalMetrics == null ? incrementalMetrics.getMillisSinceOldestPendingRead() : -1L, incrementalMetrics == null ? incrementalMetrics.getStorageHealthStatusCode() : -1, incrementalMetrics == null ? incrementalMetrics.getDataLoaderStatusCode() : -1, incrementalMetrics == null && incrementalMetrics.getReadLogsEnabled(), incrementalMetrics == null ? incrementalMetrics.getMillisSinceLastDataLoaderBind() : -1L, incrementalMetrics == null ? incrementalMetrics.getDataLoaderBindDelayMillis() : -1L, incrementalMetrics == null ? incrementalMetrics.getTotalDelayedReads() : -1, incrementalMetrics == null ? incrementalMetrics.getTotalFailedReads() : -1, incrementalMetrics == null ? incrementalMetrics.getLastReadErrorUid() : -1, incrementalMetrics == null ? incrementalMetrics.getMillisSinceLastReadError() : -1L, incrementalMetrics == null ? incrementalMetrics.getLastReadErrorNumber() : 0, incrementalMetrics == null ? incrementalMetrics.getTotalDelayedReadsDurationMillis() : -1L);
                this.mService.addErrorToDropBox("anr", this.mApp, this.mApp.processName, str, str2, windowProcessController == null ? (com.android.server.am.ProcessRecord) windowProcessController.mOwner : null, null, sb2.toString(), dumpStackTraces, null, new java.lang.Float(r5), incrementalMetrics, uuid, withProcessFrozenState);
                if (!this.mApp.getWindowProcessController().appNotResponding(sb.toString(), new java.lang.Runnable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.ProcessErrorStateRecord.this.lambda$appNotResponding$4();
                    }
                }, new java.lang.Runnable() { // from class: com.android.server.am.ProcessErrorStateRecord$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.ProcessErrorStateRecord.this.lambda$appNotResponding$5();
                    }
                })) {
                }
            } finally {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$appNotResponding$0(com.android.internal.os.anr.AnrLatencyTracker anrLatencyTracker, java.lang.String str) {
        anrLatencyTracker.waitingOnAMSLockStarted();
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                anrLatencyTracker.waitingOnAMSLockEnded();
                setAnrAnnotation(str);
                this.mApp.killLocked("anr", 6, true);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$appNotResponding$1(com.android.internal.os.anr.AnrLatencyTracker anrLatencyTracker) {
        anrLatencyTracker.updateCpuStatsNowCalled();
        this.mService.updateCpuStatsNow();
        anrLatencyTracker.updateCpuStatsNowReturned();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$appNotResponding$2(int i, int i2, java.util.ArrayList arrayList, android.util.SparseBooleanArray sparseBooleanArray, com.android.server.am.ProcessRecord processRecord) {
        int pid;
        if (processRecord != null && processRecord.getThread() != null && (pid = processRecord.getPid()) > 0 && pid != i && pid != i2 && pid != com.android.server.am.ActivityManagerService.MY_PID) {
            if (processRecord.isPersistent()) {
                arrayList.add(java.lang.Integer.valueOf(pid));
            } else if (processRecord.mServices.isTreatedLikeActivity()) {
                arrayList.add(java.lang.Integer.valueOf(pid));
            } else {
                sparseBooleanArray.put(pid, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.ArrayList lambda$appNotResponding$3(com.android.internal.os.anr.AnrLatencyTracker anrLatencyTracker, boolean z, boolean z2) throws java.lang.Exception {
        java.lang.String[] strArr;
        anrLatencyTracker.nativePidCollectionStarted();
        java.util.ArrayList arrayList = null;
        if (!(this.mApp.info.isSystemApp() || this.mApp.info.isSystemExt()) || z || z2) {
            int i = 0;
            while (true) {
                if (i >= com.android.server.Watchdog.NATIVE_STACKS_OF_INTEREST.length) {
                    strArr = null;
                    break;
                }
                if (!com.android.server.Watchdog.NATIVE_STACKS_OF_INTEREST[i].equals(this.mApp.processName)) {
                    i++;
                } else {
                    strArr = new java.lang.String[]{this.mApp.processName};
                    break;
                }
            }
        } else {
            strArr = com.android.server.Watchdog.NATIVE_STACKS_OF_INTEREST;
        }
        int[] pidsForCommands = strArr == null ? null : android.os.Process.getPidsForCommands(strArr);
        if (pidsForCommands != null) {
            arrayList = new java.util.ArrayList(pidsForCommands.length);
            for (int i2 : pidsForCommands) {
                arrayList.add(java.lang.Integer.valueOf(i2));
            }
        }
        anrLatencyTracker.nativePidCollectionEnded();
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$appNotResponding$4() {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mApp.killLocked("anr", 6, true);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$appNotResponding$5() {
        com.android.server.am.ActivityManagerService activityManagerService = this.mService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                this.mService.mServices.scheduleServiceTimeoutLocked(this.mApp);
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private void makeAppNotRespondingLSP(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        setNotResponding(true);
        if (this.mService.mAppErrors != null) {
            this.mNotRespondingReport = this.mService.mAppErrors.generateProcessError(this.mApp, 2, str, str2, str3, null);
        }
        startAppProblemLSP();
        this.mApp.getWindowProcessController().stopFreezingActivities();
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void startAppProblemLSP() {
        this.mErrorReportReceiver = null;
        for (int i : this.mService.mUserController.getCurrentProfileIds()) {
            if (this.mApp.userId == i) {
                this.mErrorReportReceiver = android.app.ApplicationErrorReport.getErrorReportReceiver(this.mService.mContext, this.mApp.info.packageName, this.mApp.info.flags);
            }
        }
        for (com.android.server.am.BroadcastQueue broadcastQueue : this.mService.mBroadcastQueues) {
            broadcastQueue.onApplicationProblemLocked(this.mApp);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private boolean isInterestingForBackgroundTraces() {
        if (this.mApp.getPid() == com.android.server.am.ActivityManagerService.MY_PID || this.mApp.isInterestingToUserLocked()) {
            return true;
        }
        return (this.mApp.info != null && "com.android.systemui".equals(this.mApp.info.packageName)) || this.mApp.mState.hasTopUi() || this.mApp.mState.hasOverlayUi();
    }

    private boolean getShowBackground() {
        android.content.ContentResolver contentResolver = this.mService.mContext.getContentResolver();
        return android.provider.Settings.Secure.getIntForUser(contentResolver, "anr_show_background", 0, contentResolver.getUserId()) != 0;
    }

    @android.annotation.Nullable
    private java.lang.String buildMemoryHeadersFor(int i) {
        if (i <= 0) {
            android.util.Slog.i("ActivityManager", "Memory header requested with invalid pid: " + i);
            return null;
        }
        com.android.server.stats.pull.ProcfsMemoryUtil.MemorySnapshot readMemorySnapshotFromProcfs = com.android.server.stats.pull.ProcfsMemoryUtil.readMemorySnapshotFromProcfs(i);
        if (readMemorySnapshotFromProcfs == null) {
            android.util.Slog.i("ActivityManager", "Failed to get memory snapshot for pid:" + i);
            return null;
        }
        return "RssHwmKb: " + readMemorySnapshotFromProcfs.rssHighWaterMarkInKilobytes + "\nRssKb: " + readMemorySnapshotFromProcfs.rssInKilobytes + "\nRssAnonKb: " + readMemorySnapshotFromProcfs.anonRssInKilobytes + "\nRssShmemKb: " + readMemorySnapshotFromProcfs.rssShmemKilobytes + "\nVmSwapKb: " + readMemorySnapshotFromProcfs.swapInKilobytes + "\n";
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    @com.android.internal.annotations.VisibleForTesting
    boolean isSilentAnr() {
        return (getShowBackground() || isInterestingForBackgroundTraces()) ? false : true;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isMonitorCpuUsage() {
        com.android.server.am.AppProfiler appProfiler = this.mService.mAppProfiler;
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    void onCleanupApplicationRecordLSP() {
        getDialogController().clearAllErrorDialogs();
        setCrashing(false);
        setNotResponding(false);
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str, long j) {
        com.android.server.am.ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        com.android.server.am.ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                if (!this.mCrashing && !this.mDialogController.hasCrashDialogs() && !this.mNotResponding) {
                    if (this.mDialogController.hasAnrDialogs() || this.mBad) {
                    }
                }
                printWriter.print(str);
                printWriter.print(" mCrashing=" + this.mCrashing);
                printWriter.print(" " + this.mDialogController.getCrashDialogs());
                printWriter.print(" mNotResponding=" + this.mNotResponding);
                printWriter.print(" " + this.mDialogController.getAnrDialogs());
                printWriter.print(" bad=" + this.mBad);
                if (this.mErrorReportReceiver != null) {
                    printWriter.print(" errorReportReceiver=");
                    printWriter.print(this.mErrorReportReceiver.flattenToShortString());
                }
                printWriter.println();
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterProcLockedSection();
    }
}
