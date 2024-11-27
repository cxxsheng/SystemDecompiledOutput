package com.android.server.wm;

/* loaded from: classes3.dex */
class AnrController {
    private static final long PRE_DUMP_MIN_INTERVAL_MS = java.util.concurrent.TimeUnit.SECONDS.toMillis(20);
    private static final long PRE_DUMP_MONITOR_TIMEOUT_MS = java.util.concurrent.TimeUnit.SECONDS.toMillis(1);
    private volatile long mLastPreDumpTimeMs;
    private final com.android.server.wm.WindowManagerService mService;
    private final android.util.SparseArray<com.android.server.wm.ActivityRecord> mUnresponsiveAppByDisplay = new android.util.SparseArray<>();

    AnrController(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    void notifyAppUnresponsive(android.view.InputApplicationHandle inputApplicationHandle, com.android.internal.os.TimeoutRecord timeoutRecord) {
        com.android.server.wm.WindowState windowState;
        try {
            timeoutRecord.mLatencyTracker.notifyAppUnresponsiveStarted();
            timeoutRecord.mLatencyTracker.preDumpIfLockTooSlowStarted();
            preDumpIfLockTooSlow();
            timeoutRecord.mLatencyTracker.preDumpIfLockTooSlowEnded();
            timeoutRecord.mLatencyTracker.waitingOnGlobalLockStarted();
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    timeoutRecord.mLatencyTracker.waitingOnGlobalLockEnded();
                    com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(inputApplicationHandle.token);
                    if (forTokenLocked == null) {
                        android.util.Slog.e("WindowManager", "Unknown app appToken:" + inputApplicationHandle.name + ". Dropping notifyNoFocusedWindowAnr request");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        timeoutRecord.mLatencyTracker.notifyAppUnresponsiveEnded();
                        return;
                    }
                    if (forTokenLocked.mAppStopped) {
                        android.util.Slog.d("WindowManager", "App is in stopped state:" + inputApplicationHandle.name + ". Dropping notifyNoFocusedWindowAnr request");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        timeoutRecord.mLatencyTracker.notifyAppUnresponsiveEnded();
                        return;
                    }
                    com.android.server.wm.DisplayContent displayContent = this.mService.mRoot.getDisplayContent(forTokenLocked.getDisplayId());
                    android.os.IBinder iBinder = displayContent != null ? displayContent.getInputMonitor().mInputFocus : null;
                    com.android.server.wm.InputTarget inputTargetFromToken = this.mService.getInputTargetFromToken(iBinder);
                    boolean z = false;
                    if (inputTargetFromToken != null) {
                        windowState = inputTargetFromToken.getWindowState();
                        if (android.os.SystemClock.uptimeMillis() - displayContent.getInputMonitor().mInputFocusRequestTimeMillis >= com.android.server.wm.ActivityTaskManagerService.getInputDispatchingTimeoutMillisLocked(windowState.getActivityRecord())) {
                            z = true;
                        }
                    } else {
                        windowState = null;
                    }
                    if (!z) {
                        android.util.Slog.i("WindowManager", "ANR in " + forTokenLocked.getName() + ".  Reason: " + timeoutRecord.mReason);
                        this.mUnresponsiveAppByDisplay.put(forTokenLocked.getDisplayId(), forTokenLocked);
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    if (z && notifyWindowUnresponsive(iBinder, timeoutRecord)) {
                        android.util.Slog.i("WindowManager", "Blamed " + windowState.getName() + " using pending focus request. Focused activity: " + forTokenLocked.getName());
                    } else {
                        forTokenLocked.inputDispatchingTimedOut(timeoutRecord, -1);
                    }
                    if (!z) {
                        dumpAnrStateAsync(forTokenLocked, null, timeoutRecord.mReason);
                    }
                    timeoutRecord.mLatencyTracker.notifyAppUnresponsiveEnded();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (java.lang.Throwable th2) {
            timeoutRecord.mLatencyTracker.notifyAppUnresponsiveEnded();
            throw th2;
        }
    }

    void notifyWindowUnresponsive(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.util.OptionalInt optionalInt, @android.annotation.NonNull com.android.internal.os.TimeoutRecord timeoutRecord) {
        try {
            timeoutRecord.mLatencyTracker.notifyWindowUnresponsiveStarted();
            if (notifyWindowUnresponsive(iBinder, timeoutRecord)) {
                return;
            }
            if (optionalInt.isPresent()) {
                notifyWindowUnresponsive(optionalInt.getAsInt(), timeoutRecord);
                return;
            }
            android.util.Slog.w("WindowManager", "Failed to notify that window token=" + iBinder + " was unresponsive.");
        } finally {
            timeoutRecord.mLatencyTracker.notifyWindowUnresponsiveEnded();
        }
    }

    private boolean notifyWindowUnresponsive(@android.annotation.NonNull android.os.IBinder iBinder, com.android.internal.os.TimeoutRecord timeoutRecord) {
        timeoutRecord.mLatencyTracker.preDumpIfLockTooSlowStarted();
        preDumpIfLockTooSlow();
        timeoutRecord.mLatencyTracker.preDumpIfLockTooSlowEnded();
        timeoutRecord.mLatencyTracker.waitingOnGlobalLockStarted();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                timeoutRecord.mLatencyTracker.waitingOnGlobalLockEnded();
                com.android.server.wm.InputTarget inputTargetFromToken = this.mService.getInputTargetFromToken(iBinder);
                if (inputTargetFromToken == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                com.android.server.wm.WindowState windowState = inputTargetFromToken.getWindowState();
                int pid = inputTargetFromToken.getPid();
                com.android.server.wm.ActivityRecord activityRecord = windowState.mInputChannelToken == iBinder ? windowState.mActivityRecord : null;
                android.util.Slog.i("WindowManager", "ANR in " + inputTargetFromToken + ". Reason:" + timeoutRecord.mReason);
                boolean isWindowAboveSystem = isWindowAboveSystem(windowState);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                if (activityRecord != null) {
                    activityRecord.inputDispatchingTimedOut(timeoutRecord, pid);
                } else {
                    this.mService.mAmInternal.inputDispatchingTimedOut(pid, isWindowAboveSystem, timeoutRecord);
                }
                dumpAnrStateAsync(activityRecord, windowState, timeoutRecord.mReason);
                return true;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private void notifyWindowUnresponsive(int i, com.android.internal.os.TimeoutRecord timeoutRecord) {
        android.util.Slog.i("WindowManager", "ANR in input window owned by pid=" + i + ". Reason: " + timeoutRecord.mReason);
        this.mService.mAmInternal.inputDispatchingTimedOut(i, true, timeoutRecord);
        dumpAnrStateAsync(null, null, timeoutRecord.mReason);
    }

    void notifyWindowResponsive(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.util.OptionalInt optionalInt) {
        if (notifyWindowResponsive(iBinder)) {
            return;
        }
        if (!optionalInt.isPresent()) {
            android.util.Slog.w("WindowManager", "Failed to notify that window token=" + iBinder + " was responsive.");
            return;
        }
        notifyWindowResponsive(optionalInt.getAsInt());
    }

    private boolean notifyWindowResponsive(@android.annotation.NonNull android.os.IBinder iBinder) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.InputTarget inputTargetFromToken = this.mService.getInputTargetFromToken(iBinder);
                if (inputTargetFromToken == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                int pid = inputTargetFromToken.getPid();
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                this.mService.mAmInternal.inputDispatchingResumed(pid);
                return true;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private void notifyWindowResponsive(int i) {
        this.mService.mAmInternal.inputDispatchingResumed(i);
    }

    void onFocusChanged(com.android.server.wm.WindowState windowState) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord activityRecord = this.mUnresponsiveAppByDisplay.get(windowState.getDisplayId());
                if (activityRecord == null || activityRecord != windowState.mActivityRecord) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                this.mService.mAmInternal.inputDispatchingResumed(activityRecord.getPid());
                this.mUnresponsiveAppByDisplay.remove(windowState.getDisplayId());
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private void preDumpIfLockTooSlow() {
        long j;
        if (!android.os.Build.IS_DEBUGGABLE) {
            return;
        }
        final long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (this.mLastPreDumpTimeMs > 0 && uptimeMillis - this.mLastPreDumpTimeMs < PRE_DUMP_MIN_INTERVAL_MS) {
            return;
        }
        long j2 = 64;
        android.os.Trace.traceBegin(64L, "preDumpIfLockTooSlow()");
        try {
            final boolean[] zArr = {true};
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(2);
            final com.android.server.wm.WindowManagerService windowManagerService = this.mService;
            java.util.Objects.requireNonNull(windowManagerService);
            arrayMap.put("WindowManager", new java.lang.Runnable() { // from class: com.android.server.wm.AnrController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.WindowManagerService.this.monitor();
                }
            });
            final android.app.ActivityManagerInternal activityManagerInternal = this.mService.mAmInternal;
            java.util.Objects.requireNonNull(activityManagerInternal);
            arrayMap.put("ActivityManager", new java.lang.Runnable() { // from class: com.android.server.wm.AnrController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    activityManagerInternal.monitor();
                }
            });
            java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(arrayMap.size());
            int i = 0;
            while (i < arrayMap.size()) {
                final java.lang.String str = (java.lang.String) arrayMap.keyAt(i);
                final java.lang.Runnable runnable = (java.lang.Runnable) arrayMap.valueAt(i);
                final java.util.concurrent.CountDownLatch countDownLatch2 = countDownLatch;
                java.util.concurrent.CountDownLatch countDownLatch3 = countDownLatch;
                int i2 = i;
                j = j2;
                try {
                    new java.lang.Thread() { // from class: com.android.server.wm.AnrController.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            runnable.run();
                            countDownLatch2.countDown();
                            long uptimeMillis2 = android.os.SystemClock.uptimeMillis() - uptimeMillis;
                            if (uptimeMillis2 > com.android.server.wm.AnrController.PRE_DUMP_MONITOR_TIMEOUT_MS) {
                                android.util.Slog.i("WindowManager", "Pre-dump acquired " + str + " in " + uptimeMillis2 + "ms");
                                return;
                            }
                            if ("WindowManager".equals(str)) {
                                zArr[0] = false;
                            }
                        }
                    }.start();
                    i = i2 + 1;
                    countDownLatch = countDownLatch3;
                    j2 = j;
                } catch (java.lang.Throwable th) {
                    th = th;
                    android.os.Trace.traceEnd(j);
                    throw th;
                }
            }
            j = j2;
            try {
                if (countDownLatch.await(PRE_DUMP_MONITOR_TIMEOUT_MS, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                    android.os.Trace.traceEnd(j);
                    return;
                }
            } catch (java.lang.InterruptedException e) {
            }
            this.mLastPreDumpTimeMs = uptimeMillis;
            android.util.Slog.i("WindowManager", "Pre-dump for unresponsive");
            java.util.ArrayList arrayList = new java.util.ArrayList(1);
            arrayList.add(java.lang.Integer.valueOf(com.android.server.wm.WindowManagerService.MY_PID));
            java.util.ArrayList arrayList2 = null;
            int[] pidsForCommands = zArr[0] ? android.os.Process.getPidsForCommands(new java.lang.String[]{"/system/bin/surfaceflinger"}) : null;
            if (pidsForCommands != null) {
                arrayList2 = new java.util.ArrayList(1);
                for (int i3 : pidsForCommands) {
                    arrayList2.add(java.lang.Integer.valueOf(i3));
                }
            }
            java.io.File dumpStackTraces = com.android.server.am.StackTracesDumpHelper.dumpStackTraces(arrayList, null, null, java.util.concurrent.CompletableFuture.completedFuture(arrayList2), null, "Pre-dump", com.android.server.criticalevents.CriticalEventLog.getInstance().logLinesForSystemServerTraceFile(), new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), null);
            if (dumpStackTraces != null) {
                dumpStackTraces.renameTo(new java.io.File(dumpStackTraces.getParent(), dumpStackTraces.getName() + "_pre"));
            }
            android.os.Trace.traceEnd(j);
        } catch (java.lang.Throwable th2) {
            th = th2;
            j = j2;
        }
    }

    private void dumpAnrStateAsync(final com.android.server.wm.ActivityRecord activityRecord, final com.android.server.wm.WindowState windowState, final java.lang.String str) {
        com.android.server.FgThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.wm.AnrController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.AnrController.this.lambda$dumpAnrStateAsync$0(activityRecord, windowState, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpAnrStateAsync$0(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.WindowState windowState, java.lang.String str) {
        try {
            android.os.Trace.traceBegin(64L, "dumpAnrStateLocked()");
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mService.saveANRStateLocked(activityRecord, windowState, str);
                    this.mService.mAtmService.saveANRState(activityRecord, str);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Trace.traceEnd(64L);
        }
    }

    private boolean isWindowAboveSystem(@android.annotation.NonNull com.android.server.wm.WindowState windowState) {
        return windowState.mBaseLayer > this.mService.mPolicy.getWindowLayerFromTypeLw(2038, windowState.mOwnerCanAddInternalSystemWindow);
    }
}
