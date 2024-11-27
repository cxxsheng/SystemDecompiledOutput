package com.android.server.wm;

/* loaded from: classes3.dex */
class ActivityClientController extends android.app.IActivityClientController.Stub {
    public static final long ACCESS_SHARED_IDENTITY = 259743961;
    private static final int SET_PIP_ASPECT_RATIO_LIMIT = 60;
    private static final long SET_PIP_ASPECT_RATIO_TIME_WINDOW_MS = 60000;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private com.android.internal.app.AssistUtils mAssistUtils;
    private final android.content.Context mContext;
    private final com.android.server.wm.WindowManagerGlobalLock mGlobalLock;
    private final com.android.server.wm.ActivityTaskManagerService mService;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.utils.quota.CountQuotaTracker mSetPipAspectRatioQuotaTracker;
    private final com.android.server.wm.ActivityTaskSupervisor mTaskSupervisor;

    ActivityClientController(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        this.mService = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
        this.mTaskSupervisor = activityTaskManagerService.mTaskSupervisor;
        this.mContext = activityTaskManagerService.mContext;
    }

    void onSystemReady() {
        this.mAssistUtils = new com.android.internal.app.AssistUtils(this.mContext);
    }

    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (java.lang.RuntimeException e) {
            throw com.android.server.wm.ActivityTaskManagerService.logAndRethrowRuntimeExceptionOnTransact("ActivityClientController", e);
        }
    }

    public void activityIdle(android.os.IBinder iBinder, android.content.res.Configuration configuration, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    android.os.Trace.traceBegin(32L, "activityIdle");
                    com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                    if (forTokenLocked == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    this.mTaskSupervisor.activityIdleInternal(forTokenLocked, false, false, configuration);
                    if (z && forTokenLocked.hasProcess()) {
                        forTokenLocked.app.clearProfilerIfNeeded();
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Trace.traceEnd(32L);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void activityResumed(android.os.IBinder iBinder, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord.activityResumedLocked(iBinder, z);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void activityRefreshed(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord.activityRefreshedLocked(iBinder);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void activityTopResumedStateLost() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mTaskSupervisor.handleTopResumedStateReleased(false);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void activityPaused(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                android.os.Trace.traceBegin(32L, "activityPaused");
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    forTokenLocked.activityPaused(false);
                }
                android.os.Trace.traceEnd(32L);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void activityStopped(android.os.IBinder iBinder, android.os.Bundle bundle, android.os.PersistableBundle persistableBundle, java.lang.CharSequence charSequence) {
        com.android.server.wm.ActivityRecord isInRootTaskLocked;
        java.lang.String str;
        int i;
        if (bundle != null && bundle.hasFileDescriptors()) {
            throw new java.lang.IllegalArgumentException("File descriptors passed in Bundle");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                android.os.Trace.traceBegin(32L, "activityStopped");
                isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                str = null;
                i = 0;
                if (isInRootTaskLocked != null) {
                    if (!isInRootTaskLocked.isState(com.android.server.wm.ActivityRecord.State.STOPPING, com.android.server.wm.ActivityRecord.State.RESTARTING_PROCESS) && this.mTaskSupervisor.hasScheduledRestartTimeouts(isInRootTaskLocked)) {
                        isInRootTaskLocked.setState(com.android.server.wm.ActivityRecord.State.RESTARTING_PROCESS, "continue-restart");
                    }
                    if (isInRootTaskLocked.attachedToProcess() && isInRootTaskLocked.isState(com.android.server.wm.ActivityRecord.State.RESTARTING_PROCESS)) {
                        str = isInRootTaskLocked.app.mName;
                        i = isInRootTaskLocked.app.mUid;
                    }
                    isInRootTaskLocked.activityStopped(bundle, persistableBundle, charSequence);
                }
                android.os.Trace.traceEnd(32L);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        if (str != null) {
            this.mTaskSupervisor.removeRestartTimeouts(isInRootTaskLocked);
            this.mService.mAmInternal.killProcess(str, i, "restartActivityProcess");
        }
        this.mService.mAmInternal.trimApplications();
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void activityDestroyed(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                android.os.Trace.traceBegin(32L, "activityDestroyed");
                try {
                    com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                    if (forTokenLocked != null) {
                        forTokenLocked.destroyed("activityDestroyed");
                    }
                } finally {
                    android.os.Trace.traceEnd(32L);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void activityLocalRelaunch(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    forTokenLocked.startRelaunching();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void activityRelaunched(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    forTokenLocked.finishRelaunching();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void reportSizeConfigurations(android.os.IBinder iBinder, android.window.SizeConfigurationBuckets sizeConfigurationBuckets) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONFIGURATION, -4921282642721622589L, 0, null, java.lang.String.valueOf(iBinder), java.lang.String.valueOf(sizeConfigurationBuckets));
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null) {
                    isInRootTaskLocked.setSizeConfigurations(sizeConfigurationBuckets);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public boolean moveActivityTaskToBack(android.os.IBinder iBinder, boolean z) {
        com.android.server.wm.ActivityTaskManagerService.enforceNotIsolatedCaller("moveActivityTaskToBack");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.Task anyTaskForId = this.mService.mRootWindowContainer.anyTaskForId(com.android.server.wm.ActivityRecord.getTaskForActivityLocked(iBinder, !z));
                    if (anyTaskForId == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    boolean moveTaskToBack = com.android.server.wm.ActivityRecord.getRootTask(iBinder).moveTaskToBack(anyTaskForId);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return moveTaskToBack;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean shouldUpRecreateTask(android.os.IBinder iBinder, java.lang.String str) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    boolean shouldUpRecreateTaskLocked = forTokenLocked.getRootTask().shouldUpRecreateTaskLocked(forTokenLocked, str);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return shouldUpRecreateTaskLocked;
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return false;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    public boolean navigateUpTo(android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str, int i, android.content.Intent intent2) {
        boolean navigateUpTo;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                com.android.server.uri.NeededUriGrants collectGrants = this.mService.collectGrants(intent, isInRootTaskLocked);
                com.android.server.uri.NeededUriGrants collectGrants2 = this.mService.collectGrants(intent2, isInRootTaskLocked.resultTo);
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        navigateUpTo = isInRootTaskLocked.getRootTask().navigateUpTo(isInRootTaskLocked, intent, str, collectGrants, i, intent2, collectGrants2);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return navigateUpTo;
            } finally {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    public boolean releaseActivityInstance(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null && isInRootTaskLocked.isDestroyable()) {
                        isInRootTaskLocked.destroyImmediately("app-req");
                        boolean isState = isInRootTaskLocked.isState(com.android.server.wm.ActivityRecord.State.DESTROYING, com.android.server.wm.ActivityRecord.State.DESTROYED);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return isState;
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean finishActivity(android.os.IBinder iBinder, int i, android.content.Intent intent, int i2) {
        long j;
        com.android.server.wm.ActivityRecord activityRecord;
        boolean z;
        if (intent != null && intent.hasFileDescriptors()) {
            throw new java.lang.IllegalArgumentException("File descriptors passed in Intent");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                boolean z2 = true;
                if (isInRootTaskLocked == null) {
                    return true;
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                com.android.server.uri.NeededUriGrants collectGrants = this.mService.collectGrants(intent, isInRootTaskLocked.resultTo);
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        if (!isInRootTaskLocked.isInHistory()) {
                            return true;
                        }
                        com.android.server.wm.Task task = isInRootTaskLocked.getTask();
                        com.android.server.wm.ActivityRecord rootActivity = task.getRootActivity();
                        if (rootActivity == null) {
                            android.util.Slog.w(TAG, "Finishing task with all activities already finished");
                        }
                        if (this.mService.getLockTaskController().activityBlockedFromFinish(isInRootTaskLocked)) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return false;
                        }
                        if (this.mService.mController != null && (activityRecord = isInRootTaskLocked.getRootTask().topRunningActivity(iBinder, -1)) != null) {
                            try {
                                z = this.mService.mController.activityResuming(activityRecord.packageName);
                            } catch (android.os.RemoteException e) {
                                this.mService.mController = null;
                                com.android.server.Watchdog.getInstance().setActivityController(null);
                                z = true;
                            }
                            if (!z) {
                                android.util.Slog.i(TAG, "Not finishing activity because controller resumed");
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                return false;
                            }
                        }
                        if (isInRootTaskLocked.app != null) {
                            isInRootTaskLocked.app.setLastActivityFinishTimeIfNeeded(android.os.SystemClock.uptimeMillis());
                        }
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        android.os.Trace.traceBegin(32L, "finishActivity");
                        boolean z3 = i2 == 1;
                        try {
                            this.mTaskSupervisor.getBackgroundActivityLaunchController().onActivityRequestedFinishing(isInRootTaskLocked);
                            if (i2 == 2 || (z3 && isInRootTaskLocked == rootActivity)) {
                                j = 32;
                                try {
                                    this.mTaskSupervisor.removeTask(task, false, z3, "finish-activity", isInRootTaskLocked.getUid(), isInRootTaskLocked.getPid(), isInRootTaskLocked.info.name);
                                    isInRootTaskLocked.mRelaunchReason = 0;
                                } catch (java.lang.Throwable th) {
                                    th = th;
                                    android.os.Trace.traceEnd(j);
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    throw th;
                                }
                            } else {
                                isInRootTaskLocked.finishIfPossible(i, intent, collectGrants, "app-request", true);
                                z2 = isInRootTaskLocked.finishing;
                                if (!z2) {
                                    android.util.Slog.i(TAG, "Failed to finish by app-request");
                                }
                                j = 32;
                            }
                            android.os.Trace.traceEnd(j);
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            return z2;
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            j = 32;
                        }
                    } finally {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
            } finally {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
    }

    public boolean finishActivityAffinity(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    final com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    if (this.mService.getLockTaskController().activityBlockedFromFinish(isInRootTaskLocked)) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    isInRootTaskLocked.getTask().forAllActivities(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityClientController$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean finishIfSameAffinity;
                            finishIfSameAffinity = com.android.server.wm.ActivityRecord.this.finishIfSameAffinity((com.android.server.wm.ActivityRecord) obj);
                            return finishIfSameAffinity;
                        }
                    }, isInRootTaskLocked, true, true);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return true;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void finishSubActivity(android.os.IBinder iBinder, final java.lang.String str, final int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    final com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    isInRootTaskLocked.getRootTask().forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityClientController$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.server.wm.ActivityRecord) obj).finishIfSubActivity(com.android.server.wm.ActivityRecord.this, str, i);
                        }
                    }, true);
                    this.mService.updateOomAdj();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setForceSendResultForMediaProjection(android.os.IBinder iBinder) {
        this.mService.mAmInternal.enforceCallingPermission("android.permission.MANAGE_MEDIA_PROJECTION", "setForceSendResultForMediaProjection");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked == null || !isInRootTaskLocked.isInHistory()) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    isInRootTaskLocked.setForceSendResultForMediaProjection();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public boolean isTopOfTask(android.os.IBinder iBinder) {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                z = isInRootTaskLocked != null && isInRootTaskLocked.getTask().getTopNonFinishingActivity() == isInRootTaskLocked;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }

    public boolean willActivityBeVisible(android.os.IBinder iBinder) {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task rootTask = com.android.server.wm.ActivityRecord.getRootTask(iBinder);
                z = rootTask != null && rootTask.willActivityBeVisible(iBinder);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }

    public int getDisplayId(android.os.IBinder iBinder) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task rootTask = com.android.server.wm.ActivityRecord.getRootTask(iBinder);
                if (rootTask == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return 0;
                }
                int displayId = rootTask.getDisplayId();
                int i = displayId != -1 ? displayId : 0;
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return i;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public int getTaskForActivity(android.os.IBinder iBinder, boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return -1;
                }
                com.android.server.wm.Task task = forTokenLocked.getTask();
                if (z) {
                    int i = task.getRootActivity() == forTokenLocked ? task.mTaskId : -1;
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return i;
                }
                int i2 = task.mTaskId;
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return i2;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public android.content.res.Configuration getTaskConfiguration(android.os.IBinder iBinder) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInAnyTask = com.android.server.wm.ActivityRecord.isInAnyTask(iBinder);
                if (isInAnyTask == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                android.content.res.Configuration configuration = isInAnyTask.getTask().getConfiguration();
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return configuration;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public android.os.IBinder getActivityTokenBelow(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInAnyTask = com.android.server.wm.ActivityRecord.isInAnyTask(iBinder);
                    if (isInAnyTask == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    com.android.server.wm.ActivityRecord activity = isInAnyTask.getTask().getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityClientController$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$getActivityTokenBelow$2;
                            lambda$getActivityTokenBelow$2 = com.android.server.wm.ActivityClientController.lambda$getActivityTokenBelow$2((com.android.server.wm.ActivityRecord) obj);
                            return lambda$getActivityTokenBelow$2;
                        }
                    }, isInAnyTask, false, true);
                    if (activity == null || activity.getUid() != isInAnyTask.getUid()) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                    android.os.IBinder iBinder2 = activity.token;
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return iBinder2;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getActivityTokenBelow$2(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.finishing;
    }

    public android.content.ComponentName getCallingActivity(android.os.IBinder iBinder) {
        android.content.ComponentName component;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord callingRecord = getCallingRecord(iBinder);
                component = callingRecord != null ? callingRecord.intent.getComponent() : null;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return component;
    }

    public java.lang.String getCallingPackage(android.os.IBinder iBinder) {
        java.lang.String str;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord callingRecord = getCallingRecord(iBinder);
                str = callingRecord != null ? callingRecord.info.packageName : null;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return str;
    }

    private static com.android.server.wm.ActivityRecord getCallingRecord(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
        if (isInRootTaskLocked != null) {
            return isInRootTaskLocked.resultTo;
        }
        return null;
    }

    public int getLaunchedFromUid(android.os.IBinder iBinder) {
        return getUid(iBinder, null, false);
    }

    public java.lang.String getLaunchedFromPackage(android.os.IBinder iBinder) {
        return getPackage(iBinder, null, false);
    }

    public int getActivityCallerUid(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        return getUid(iBinder, iBinder2, true);
    }

    public java.lang.String getActivityCallerPackage(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        return getPackage(iBinder, iBinder2, true);
    }

    private int getUid(android.os.IBinder iBinder, android.os.IBinder iBinder2, boolean z) {
        int callingUid = android.os.Binder.getCallingUid();
        boolean isInternalCallerGetLaunchedFrom = isInternalCallerGetLaunchedFrom(callingUid);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked == null || !((isInternalCallerGetLaunchedFrom || canGetLaunchedFromLocked(callingUid, forTokenLocked, iBinder2, z)) && isValidCaller(forTokenLocked, iBinder2, z))) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return -1;
                }
                int callerUid = z ? forTokenLocked.getCallerUid(iBinder2) : forTokenLocked.launchedFromUid;
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return callerUid;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private java.lang.String getPackage(android.os.IBinder iBinder, android.os.IBinder iBinder2, boolean z) {
        int callingUid = android.os.Binder.getCallingUid();
        boolean isInternalCallerGetLaunchedFrom = isInternalCallerGetLaunchedFrom(callingUid);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null && ((isInternalCallerGetLaunchedFrom || canGetLaunchedFromLocked(callingUid, forTokenLocked, iBinder2, z)) && isValidCaller(forTokenLocked, iBinder2, z))) {
                    java.lang.String callerPackage = z ? forTokenLocked.getCallerPackage(iBinder2) : forTokenLocked.launchedFromPackage;
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return callerPackage;
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return null;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private boolean isValidCaller(com.android.server.wm.ActivityRecord activityRecord, android.os.IBinder iBinder, boolean z) {
        return z ? activityRecord.hasCaller(iBinder) : iBinder == null;
    }

    public int checkActivityCallerContentUriPermission(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.net.Uri uri, int i, int i2) {
        com.android.server.uri.GrantUri grantUri = new com.android.server.uri.GrantUri(i2, uri, i);
        if (!this.mService.mUgmInternal.checkUriPermission(grantUri, android.os.Binder.getCallingUid(), i, true)) {
            throw new java.lang.SecurityException("You don't have access to the content URI, hence can't check if the caller has access to it: " + uri);
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return -1;
                }
                int i3 = forTokenLocked.checkContentUriPermission(iBinder2, grantUri, i) ? 0 : -1;
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return i3;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private boolean isInternalCallerGetLaunchedFrom(int i) {
        if (android.os.UserHandle.getAppId(i) == 1000) {
            return true;
        }
        android.content.pm.PackageManagerInternal packageManagerInternal = this.mService.mWindowManager.mPmInternal;
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageManagerInternal.getPackage(i);
        if (androidPackage == null) {
            return false;
        }
        if (androidPackage.isSignedWithPlatformKey()) {
            return true;
        }
        java.lang.String[] knownPackageNames = packageManagerInternal.getKnownPackageNames(2, android.os.UserHandle.getUserId(i));
        return knownPackageNames.length > 0 && androidPackage.getPackageName().equals(knownPackageNames[0]);
    }

    private static boolean canGetLaunchedFromLocked(int i, com.android.server.wm.ActivityRecord activityRecord, android.os.IBinder iBinder, boolean z) {
        if (android.app.compat.CompatChanges.isChangeEnabled(ACCESS_SHARED_IDENTITY, i)) {
            return (z ? activityRecord.isCallerShareIdentityEnabled(iBinder) : activityRecord.mShareIdentity) || (z ? activityRecord.getCallerUid(iBinder) : activityRecord.launchedFromUid) == i;
        }
        return false;
    }

    public void setRequestedOrientation(android.os.IBinder iBinder, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        com.android.server.wm.EventLogTags.writeWmSetRequestedOrientation(i, isInRootTaskLocked.shortComponentName);
                        isInRootTaskLocked.setRequestedOrientation(i);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getRequestedOrientation(android.os.IBinder iBinder) {
        int i;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null) {
                    i = isInRootTaskLocked.getOverrideOrientation();
                } else {
                    i = -1;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return i;
    }

    /* JADX WARN: Finally extract failed */
    public boolean convertFromTranslucent(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        com.android.server.wm.Transition createTransition = (!isInRootTaskLocked.mTransitionController.isShellTransitionsEnabled() || isInRootTaskLocked.mTransitionController.isCollecting()) ? null : isInRootTaskLocked.mTransitionController.createTransition(4);
                        boolean occludesParent = isInRootTaskLocked.setOccludesParent(true);
                        if (createTransition != null) {
                            if (occludesParent) {
                                createTransition.setOverrideAnimation(android.window.TransitionInfo.AnimationOptions.makeSceneTransitionAnimOptions(), null, null);
                                isInRootTaskLocked.mTransitionController.requestStartTransition(createTransition, null, null, null);
                                isInRootTaskLocked.mTransitionController.setReady(isInRootTaskLocked.getDisplayContent());
                            } else {
                                createTransition.abort();
                            }
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return occludesParent;
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } catch (java.lang.Throwable th2) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public boolean convertToTranslucent(android.os.IBinder iBinder, android.os.Bundle bundle) {
        com.android.server.wm.Transition transition;
        com.android.server.wm.SafeActivityOptions fromBundle = com.android.server.wm.SafeActivityOptions.fromBundle(bundle);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        com.android.server.wm.ActivityRecord activityBelow = isInRootTaskLocked.getTask().getActivityBelow(isInRootTaskLocked);
                        if (activityBelow != null) {
                            activityBelow.returningOptions = fromBundle != null ? fromBundle.getOptions(isInRootTaskLocked) : null;
                        }
                        if (!isInRootTaskLocked.mTransitionController.isShellTransitionsEnabled() || isInRootTaskLocked.mTransitionController.isCollecting()) {
                            transition = null;
                        } else {
                            transition = isInRootTaskLocked.mTransitionController.createTransition(3);
                        }
                        boolean occludesParent = isInRootTaskLocked.setOccludesParent(false);
                        if (transition != null) {
                            if (occludesParent) {
                                isInRootTaskLocked.mTransitionController.requestStartTransition(transition, null, null, null);
                                isInRootTaskLocked.mTransitionController.setReady(isInRootTaskLocked.getDisplayContent());
                                if (activityBelow != null && activityBelow.returningOptions != null && activityBelow.returningOptions.getAnimationType() == 5) {
                                    transition.setOverrideAnimation(android.window.TransitionInfo.AnimationOptions.makeSceneTransitionAnimOptions(), null, null);
                                }
                            } else {
                                transition.abort();
                            }
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return occludesParent;
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isImmersive(android.os.IBinder iBinder) {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked == null) {
                    throw new java.lang.IllegalArgumentException();
                }
                z = isInRootTaskLocked.immersive;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }

    public void setImmersive(android.os.IBinder iBinder, boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked == null) {
                    throw new java.lang.IllegalArgumentException();
                }
                isInRootTaskLocked.immersive = z;
                if (isInRootTaskLocked.isFocusedActivityOnDisplay()) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IMMERSIVE, -1597980207704427048L, 0, null, java.lang.String.valueOf(isInRootTaskLocked));
                    this.mService.applyUpdateLockStateLocked(isInRootTaskLocked);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public boolean enterPictureInPictureMode(android.os.IBinder iBinder, android.app.PictureInPictureParams pictureInPictureParams) {
        boolean enterPictureInPictureMode;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            ensureSetPipAspectRatioQuotaTracker();
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    enterPictureInPictureMode = this.mService.enterPictureInPictureMode(ensureValidPictureInPictureActivityParams("enterPictureInPictureMode", iBinder, pictureInPictureParams), pictureInPictureParams, true);
                } finally {
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            return enterPictureInPictureMode;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setPictureInPictureParams(android.os.IBinder iBinder, android.app.PictureInPictureParams pictureInPictureParams) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            ensureSetPipAspectRatioQuotaTracker();
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ensureValidPictureInPictureActivityParams("setPictureInPictureParams", iBinder, pictureInPictureParams).setPictureInPictureParams(pictureInPictureParams);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setShouldDockBigOverlays(android.os.IBinder iBinder, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord.forTokenLocked(iBinder).setShouldDockBigOverlays(z);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void splashScreenAttached(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord.splashScreenAttachedLocked(iBinder);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void requestCompatCameraControl(android.os.IBinder iBinder, boolean z, boolean z2, android.app.ICompatCameraControlCallback iCompatCameraControlCallback) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.updateCameraCompatState(z, z2, iCompatCameraControlCallback);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void ensureSetPipAspectRatioQuotaTracker() {
        if (this.mSetPipAspectRatioQuotaTracker == null) {
            this.mSetPipAspectRatioQuotaTracker = new com.android.server.utils.quota.CountQuotaTracker(this.mContext, com.android.server.utils.quota.Categorizer.SINGLE_CATEGORIZER);
            this.mSetPipAspectRatioQuotaTracker.setCountLimit(com.android.server.utils.quota.Category.SINGLE_CATEGORY, 60, 60000L);
        }
    }

    private com.android.server.wm.ActivityRecord ensureValidPictureInPictureActivityParams(java.lang.String str, android.os.IBinder iBinder, android.app.PictureInPictureParams pictureInPictureParams) {
        if (!this.mService.mSupportsPictureInPicture) {
            throw new java.lang.IllegalStateException(str + ": Device doesn't support picture-in-picture mode.");
        }
        com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
        if (forTokenLocked == null) {
            throw new java.lang.IllegalStateException(str + ": Can't find activity for token=" + iBinder);
        }
        if (!forTokenLocked.supportsPictureInPicture()) {
            throw new java.lang.IllegalStateException(str + ": Current activity does not support picture-in-picture.");
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (forTokenLocked.pictureInPictureArgs.hasSetAspectRatio() && pictureInPictureParams.hasSetAspectRatio() && !forTokenLocked.pictureInPictureArgs.getAspectRatio().equals(pictureInPictureParams.getAspectRatio()) && !this.mSetPipAspectRatioQuotaTracker.noteEvent(callingUserId, forTokenLocked.packageName, "setPipAspectRatio")) {
            throw new java.lang.IllegalStateException(str + ": Too many PiP aspect ratio change requests from " + forTokenLocked.packageName);
        }
        float f = this.mContext.getResources().getFloat(android.R.dimen.config_letterboxVerticalPositionMultiplier);
        float f2 = this.mContext.getResources().getFloat(android.R.dimen.config_letterboxThinLetterboxWidthDp);
        if (pictureInPictureParams.hasSetAspectRatio() && !this.mService.mWindowManager.isValidPictureInPictureAspectRatio(forTokenLocked.mDisplayContent, pictureInPictureParams.getAspectRatioFloat())) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format(str + ": Aspect ratio is too extreme (must be between %f and %f).", java.lang.Float.valueOf(f), java.lang.Float.valueOf(f2)));
        }
        if (this.mService.mSupportsExpandedPictureInPicture && pictureInPictureParams.hasSetExpandedAspectRatio() && !this.mService.mWindowManager.isValidExpandedPictureInPictureAspectRatio(forTokenLocked.mDisplayContent, pictureInPictureParams.getExpandedAspectRatioFloat())) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format(str + ": Expanded aspect ratio is not extreme enough (must not be between %f and %f).", java.lang.Float.valueOf(f), java.lang.Float.valueOf(f2)));
        }
        pictureInPictureParams.truncateActions(android.app.ActivityTaskManager.getMaxNumPictureInPictureActions(this.mContext));
        return forTokenLocked;
    }

    boolean requestPictureInPictureMode(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.inPinnedWindowingMode() || !activityRecord.checkEnterPictureInPictureState("requestPictureInPictureMode", false)) {
            return false;
        }
        if (activityRecord.pictureInPictureArgs.isAutoEnterEnabled()) {
            return this.mService.enterPictureInPictureMode(activityRecord, activityRecord.pictureInPictureArgs, false);
        }
        try {
            this.mService.getLifecycleManager().scheduleTransactionItem(activityRecord.app.getThread(), android.app.servertransaction.EnterPipRequestedItem.obtain(activityRecord.token));
            return true;
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Failed to send enter pip requested item: " + activityRecord.intent.getComponent(), e);
            return false;
        }
    }

    void onPictureInPictureUiStateChanged(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, android.app.PictureInPictureUiState pictureInPictureUiState) {
        try {
            this.mService.getLifecycleManager().scheduleTransactionItem(activityRecord.app.getThread(), android.app.servertransaction.PipStateTransactionItem.obtain(activityRecord.token, pictureInPictureUiState));
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Failed to send pip state transaction item: " + activityRecord.intent.getComponent(), e);
        }
    }

    public void toggleFreeformWindowingMode(android.os.IBinder iBinder) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                    if (forTokenLocked == null) {
                        throw new java.lang.IllegalArgumentException("toggleFreeformWindowingMode: No activity record matching token=" + iBinder);
                    }
                    com.android.server.wm.Task rootTask = forTokenLocked.getRootTask();
                    if (rootTask == null) {
                        throw new java.lang.IllegalStateException("toggleFreeformWindowingMode: the activity doesn't have a root task");
                    }
                    if (!rootTask.inFreeformWindowingMode() && rootTask.getWindowingMode() != 1) {
                        throw new java.lang.IllegalStateException("toggleFreeformWindowingMode: You can only toggle between fullscreen and freeform.");
                    }
                    if (rootTask.inFreeformWindowingMode()) {
                        rootTask.setWindowingMode(1);
                        rootTask.setBounds(null);
                    } else {
                        if (!forTokenLocked.supportsFreeform()) {
                            throw new java.lang.IllegalStateException("This activity is currently not freeform-enabled");
                        }
                        if (rootTask.getParent().inFreeformWindowingMode()) {
                            rootTask.setWindowingMode(0);
                        } else {
                            rootTask.setWindowingMode(5);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.app.FullscreenRequestHandler.RequestResult
    private int validateMultiwindowFullscreenRequestLocked(com.android.server.wm.Task task, int i, com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.getWindowingMode() == 2) {
            return 0;
        }
        if (activityRecord != task.getTopMostActivity()) {
            return 2;
        }
        return (i != 0 || (task.getWindowingMode() == 1 && task.mMultiWindowRestoreWindowingMode != -1)) ? 0 : 1;
    }

    public void requestMultiwindowFullscreen(android.os.IBinder iBinder, int i, android.os.IRemoteCallback iRemoteCallback) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    requestMultiwindowFullscreenLocked(iBinder, i, iRemoteCallback);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void requestMultiwindowFullscreenLocked(android.os.IBinder iBinder, final int i, final android.os.IRemoteCallback iRemoteCallback) {
        final com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
        if (forTokenLocked == null) {
            return;
        }
        com.android.server.wm.TransitionController transitionController = forTokenLocked.mTransitionController;
        if (!transitionController.isShellTransitionsEnabled()) {
            com.android.server.wm.Task topDisplayFocusedRootTask = this.mService.getTopDisplayFocusedRootTask();
            int validateMultiwindowFullscreenRequestLocked = validateMultiwindowFullscreenRequestLocked(topDisplayFocusedRootTask, i, forTokenLocked);
            reportMultiwindowFullscreenRequestValidatingResult(iRemoteCallback, validateMultiwindowFullscreenRequestLocked);
            if (validateMultiwindowFullscreenRequestLocked == 0) {
                executeMultiWindowFullscreenRequest(i, topDisplayFocusedRootTask);
                return;
            }
            return;
        }
        final com.android.server.wm.Transition transition = new com.android.server.wm.Transition(6, 0, transitionController, this.mService.mWindowManager.mSyncEngine);
        forTokenLocked.mTransitionController.startCollectOrQueue(transition, new com.android.server.wm.TransitionController.OnStartCollect() { // from class: com.android.server.wm.ActivityClientController$$ExternalSyntheticLambda4
            @Override // com.android.server.wm.TransitionController.OnStartCollect
            public final void onCollectStarted(boolean z) {
                com.android.server.wm.ActivityClientController.this.lambda$requestMultiwindowFullscreenLocked$3(i, iRemoteCallback, forTokenLocked, transition, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: executeFullscreenRequestTransition, reason: merged with bridge method [inline-methods] */
    public void lambda$requestMultiwindowFullscreenLocked$3(int i, android.os.IRemoteCallback iRemoteCallback, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.Transition transition, boolean z) {
        int validateMultiwindowFullscreenRequestLocked = validateMultiwindowFullscreenRequestLocked(this.mService.getTopDisplayFocusedRootTask(), i, activityRecord);
        reportMultiwindowFullscreenRequestValidatingResult(iRemoteCallback, validateMultiwindowFullscreenRequestLocked);
        if (validateMultiwindowFullscreenRequestLocked != 0) {
            transition.abort();
            return;
        }
        com.android.server.wm.Task task = activityRecord.getTask();
        transition.collect(task);
        executeMultiWindowFullscreenRequest(i, task);
        activityRecord.mTransitionController.requestStartTransition(transition, task, null, null);
        transition.setReady(task, true);
    }

    private static void reportMultiwindowFullscreenRequestValidatingResult(android.os.IRemoteCallback iRemoteCallback, @android.app.FullscreenRequestHandler.RequestResult int i) {
        if (iRemoteCallback == null) {
            return;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("result", i);
        try {
            iRemoteCallback.sendResult(bundle);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "client throws an exception back to the server, ignore it");
        }
    }

    private static void executeMultiWindowFullscreenRequest(int i, com.android.server.wm.Task task) {
        int i2;
        if (i == 1) {
            task.mMultiWindowRestoreWindowingMode = task.getRequestedOverrideWindowingMode();
            i2 = 1;
        } else {
            i2 = task.mMultiWindowRestoreWindowingMode;
            task.mMultiWindowRestoreWindowingMode = -1;
        }
        task.setWindowingMode(i2);
        if (i2 == 1) {
            task.setBounds(null);
        }
    }

    public void startLockTaskModeByToken(android.os.IBinder iBinder) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    this.mService.startLockTaskMode(forTokenLocked.getTask(), false);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void stopLockTaskModeByToken(android.os.IBinder iBinder) {
        this.mService.stopLockTaskModeInternal(iBinder, false);
    }

    public void showLockTaskEscapeMessage(android.os.IBinder iBinder) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (com.android.server.wm.ActivityRecord.forTokenLocked(iBinder) != null) {
                    this.mService.getLockTaskController().showLockTaskToast();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void setTaskDescription(android.os.IBinder iBinder, android.app.ActivityManager.TaskDescription taskDescription) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null) {
                    isInRootTaskLocked.setTaskDescription(taskDescription);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public boolean showAssistFromActivity(android.os.IBinder iBinder, android.os.Bundle bundle) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                    com.android.server.wm.Task topDisplayFocusedRootTask = this.mService.getTopDisplayFocusedRootTask();
                    com.android.server.wm.ActivityRecord topNonFinishingActivity = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getTopNonFinishingActivity() : null;
                    if (topNonFinishingActivity != forTokenLocked) {
                        android.util.Slog.w(TAG, "showAssistFromActivity failed: caller " + forTokenLocked + " is not current top " + topNonFinishingActivity);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    if (topNonFinishingActivity.nowVisible) {
                        java.lang.String str = topNonFinishingActivity.launchedFromFeatureId;
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return this.mAssistUtils.showSessionForActiveService(bundle, 8, str, (com.android.internal.app.IVoiceInteractionSessionShowCallback) null, iBinder);
                    }
                    android.util.Slog.w(TAG, "showAssistFromActivity failed: caller " + forTokenLocked + " is not visible");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isRootVoiceInteraction(android.os.IBinder iBinder) {
        boolean z;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                z = isInRootTaskLocked != null && isInRootTaskLocked.rootVoiceInteraction;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return z;
    }

    public void startLocalVoiceInteraction(android.os.IBinder iBinder, android.os.Bundle bundle) {
        android.util.Slog.i(TAG, "Activity tried to startLocalVoiceInteraction");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task topDisplayFocusedRootTask = this.mService.getTopDisplayFocusedRootTask();
                com.android.server.wm.ActivityRecord topNonFinishingActivity = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getTopNonFinishingActivity() : null;
                if (com.android.server.wm.ActivityRecord.forTokenLocked(iBinder) != topNonFinishingActivity) {
                    throw new java.lang.SecurityException("Only focused activity can call startVoiceInteraction");
                }
                if (this.mService.mRunningVoice != null || topNonFinishingActivity.getTask().voiceSession != null || topNonFinishingActivity.voiceSession != null) {
                    android.util.Slog.w(TAG, "Already in a voice interaction, cannot start new voice interaction");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } else if (topNonFinishingActivity.pendingVoiceInteractionStart) {
                    android.util.Slog.w(TAG, "Pending start of voice interaction already.");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    topNonFinishingActivity.pendingVoiceInteractionStart = true;
                    java.lang.String str = topNonFinishingActivity.launchedFromFeatureId;
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    ((android.service.voice.VoiceInteractionManagerInternal) com.android.server.LocalServices.getService(android.service.voice.VoiceInteractionManagerInternal.class)).startLocalVoiceInteraction(iBinder, str, bundle);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void stopLocalVoiceInteraction(android.os.IBinder iBinder) {
        ((android.service.voice.VoiceInteractionManagerInternal) com.android.server.LocalServices.getService(android.service.voice.VoiceInteractionManagerInternal.class)).stopLocalVoiceInteraction(iBinder);
    }

    public void setShowWhenLocked(android.os.IBinder iBinder, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.setShowWhenLocked(z);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setInheritShowWhenLocked(android.os.IBinder iBinder, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.setInheritShowWhenLocked(z);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setTurnScreenOn(android.os.IBinder iBinder, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.setTurnScreenOn(z);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setAllowCrossUidActivitySwitchFromBelow(android.os.IBinder iBinder, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.setAllowCrossUidActivitySwitchFromBelow(z);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void reportActivityFullyDrawn(android.os.IBinder iBinder, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        this.mTaskSupervisor.getActivityMetricsLogger().notifyFullyDrawn(isInRootTaskLocked, z);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void overrideActivityTransition(android.os.IBinder iBinder, boolean z, int i, int i2, int i3) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null) {
                    isInRootTaskLocked.overrideCustomTransition(z, i, i2, i3);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void clearOverrideActivityTransition(android.os.IBinder iBinder, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null) {
                    isInRootTaskLocked.clearCustomTransition(z);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public void overridePendingTransition(android.os.IBinder iBinder, java.lang.String str, int i, int i2, int i3) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null && isInRootTaskLocked.isState(com.android.server.wm.ActivityRecord.State.RESUMED, com.android.server.wm.ActivityRecord.State.PAUSING)) {
                    isInRootTaskLocked.mDisplayContent.mAppTransition.overridePendingAppTransition(str, i, i2, i3, null, null, isInRootTaskLocked.mOverrideTaskTransition);
                    isInRootTaskLocked.mTransitionController.setOverrideAnimation(android.window.TransitionInfo.AnimationOptions.makeCustomAnimOptions(str, i, i2, i3, isInRootTaskLocked.mOverrideTaskTransition), null, null);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    /* JADX WARN: Finally extract failed */
    public int setVrMode(android.os.IBinder iBinder, boolean z, android.content.ComponentName componentName) {
        com.android.server.wm.ActivityRecord isInRootTaskLocked;
        this.mService.enforceSystemHasVrFeature();
        com.android.server.vr.VrManagerInternal vrManagerInternal = (com.android.server.vr.VrManagerInternal) com.android.server.LocalServices.getService(com.android.server.vr.VrManagerInternal.class);
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
            } finally {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        if (isInRootTaskLocked == null) {
            throw new java.lang.IllegalArgumentException();
        }
        int hasVrPackage = vrManagerInternal.hasVrPackage(componentName, isInRootTaskLocked.mUserId);
        if (hasVrPackage != 0) {
            return hasVrPackage;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                if (!z) {
                    componentName = null;
                }
                try {
                    isInRootTaskLocked.requestedVrComponent = componentName;
                    if (isInRootTaskLocked.isFocusedActivityOnDisplay()) {
                        this.mService.applyUpdateVrModeLocked(isInRootTaskLocked);
                    }
                } finally {
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void setRecentsScreenshotEnabled(android.os.IBinder iBinder, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.setRecentsScreenshotEnabled(z);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void restartActivityProcessIfVisible(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("restartActivityProcess");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.restartProcessIfVisible();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void invalidateHomeTaskSnapshot(android.os.IBinder iBinder) {
        com.android.server.wm.ActivityRecord isInRootTaskLocked;
        if (iBinder == null) {
            com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("invalidateHomeTaskSnapshot");
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (iBinder == null) {
                    com.android.server.wm.Task rootHomeTask = this.mService.mRootWindowContainer.getDefaultTaskDisplayArea().getRootHomeTask();
                    isInRootTaskLocked = rootHomeTask != null ? rootHomeTask.topRunningActivity() : null;
                } else {
                    isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                }
                if (isInRootTaskLocked != null && isInRootTaskLocked.isActivityTypeHome()) {
                    this.mService.mWindowManager.mTaskSnapshotController.removeSnapshotCache(isInRootTaskLocked.getTask().mTaskId);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void dismissKeyguard(android.os.IBinder iBinder, com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) {
        if (charSequence != null) {
            this.mService.mAmInternal.enforceCallingPermission("android.permission.SHOW_KEYGUARD_MESSAGE", "dismissKeyguard");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mService.mKeyguardController.dismissKeyguard(iBinder, iKeyguardDismissCallback, charSequence);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerRemoteAnimations(android.os.IBinder iBinder, android.view.RemoteAnimationDefinition remoteAnimationDefinition) {
        this.mService.mAmInternal.enforceCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "registerRemoteAnimations");
        remoteAnimationDefinition.setCallingPidUid(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.registerRemoteAnimations(remoteAnimationDefinition);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void unregisterRemoteAnimations(android.os.IBinder iBinder) {
        this.mService.mAmInternal.enforceCallingPermission("android.permission.CONTROL_REMOTE_APP_TRANSITION_ANIMATIONS", "unregisterRemoteAnimations");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked != null) {
                        isInRootTaskLocked.unregisterRemoteAnimations();
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static boolean isRelativeTaskRootActivity(final com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2) {
        com.android.server.wm.TaskFragment taskFragment = activityRecord.getTaskFragment();
        return activityRecord == taskFragment.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityClientController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isRelativeTaskRootActivity$4;
                lambda$isRelativeTaskRootActivity$4 = com.android.server.wm.ActivityClientController.lambda$isRelativeTaskRootActivity$4(com.android.server.wm.ActivityRecord.this, (com.android.server.wm.ActivityRecord) obj);
                return lambda$isRelativeTaskRootActivity$4;
            }
        }, false) && activityRecord2.getTaskFragment().getCompanionTaskFragment() == taskFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isRelativeTaskRootActivity$4(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2) {
        return !activityRecord2.finishing || activityRecord2 == activityRecord;
    }

    private static boolean isTopActivityInTaskFragment(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.getTaskFragment().topRunningActivity() == activityRecord;
    }

    private void requestCallbackFinish(android.app.IRequestFinishCallback iRequestFinishCallback) {
        try {
            iRequestFinishCallback.requestFinish();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to invoke request finish callback", e);
        }
    }

    public void onBackPressed(android.os.IBinder iBinder, android.app.IRequestFinishCallback iRequestFinishCallback) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                    if (isInRootTaskLocked == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.ActivityRecord rootActivity = isInRootTaskLocked.getTask().getRootActivity(false, true);
                    if (isInRootTaskLocked == rootActivity && this.mService.mWindowOrganizerController.mTaskOrganizerController.handleInterceptBackPressedOnTaskRoot(isInRootTaskLocked.getRootTask())) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    } else if (shouldMoveTaskToBack(isInRootTaskLocked, rootActivity)) {
                        moveActivityTaskToBack(iBinder, true);
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    } else {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        requestCallbackFinish(iRequestFinishCallback);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    static boolean shouldMoveTaskToBack(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2) {
        if (activityRecord != activityRecord2 && !isRelativeTaskRootActivity(activityRecord, activityRecord2)) {
            return false;
        }
        android.content.Intent intent = activityRecord2.mActivityComponent.equals(activityRecord.getTask().realActivity) ? activityRecord2.intent : null;
        return intent != null && isTopActivityInTaskFragment(activityRecord) && activityRecord2.isLaunchSourceType(2) && com.android.server.wm.ActivityRecord.isMainIntent(intent);
    }

    public void enableTaskLocaleOverride(android.os.IBinder iBinder) {
        if (android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) != 1000) {
            return;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    forTokenLocked.getTask().mAlignActivityLocaleWithTask = true;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public boolean isRequestedToLaunchInTaskFragment(android.os.IBinder iBinder, android.os.IBinder iBinder2) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord isInRootTaskLocked = com.android.server.wm.ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return false;
                }
                boolean z = isInRootTaskLocked.mRequestedLaunchingTaskFragmentToken == iBinder2;
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return z;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void setActivityRecordInputSinkEnabled(android.os.IBinder iBinder, boolean z) {
        if (!com.android.window.flags.Flags.allowDisableActivityRecordInputSink()) {
            return;
        }
        this.mService.mAmInternal.enforceCallingPermission("android.permission.INTERNAL_SYSTEM_WINDOW", "setActivityRecordInputSinkEnabled");
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
                if (forTokenLocked != null) {
                    forTokenLocked.mActivityRecordInputSinkEnabled = z;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }
}
