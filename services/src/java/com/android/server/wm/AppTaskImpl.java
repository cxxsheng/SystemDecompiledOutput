package com.android.server.wm;

/* loaded from: classes3.dex */
class AppTaskImpl extends android.app.IAppTask.Stub {
    private static final java.lang.String TAG = "AppTaskImpl";
    private final int mCallingUid;
    private final com.android.server.wm.ActivityTaskManagerService mService;
    private final int mTaskId;

    public AppTaskImpl(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, int i, int i2) {
        this.mService = activityTaskManagerService;
        this.mTaskId = i;
        this.mCallingUid = i2;
    }

    private void checkCallerOrSystemOrRoot() {
        if (this.mCallingUid != android.os.Binder.getCallingUid() && 1000 != android.os.Binder.getCallingUid() && android.os.Binder.getCallingUid() != 0) {
            throw new java.lang.SecurityException("Caller " + this.mCallingUid + " does not match caller of getAppTasks(): " + android.os.Binder.getCallingUid());
        }
    }

    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (java.lang.RuntimeException e) {
            throw com.android.server.wm.ActivityTaskManagerService.logAndRethrowRuntimeExceptionOnTransact(TAG, e);
        }
    }

    public void finishAndRemoveTask() {
        checkCallerOrSystemOrRoot();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int callingUid = android.os.Binder.getCallingUid();
                int callingPid = android.os.Binder.getCallingPid();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    if (!this.mService.mTaskSupervisor.removeTaskById(this.mTaskId, false, true, "finish-and-remove-task", callingUid, callingPid)) {
                        throw new java.lang.IllegalArgumentException("Unable to find task ID " + this.mTaskId);
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public android.app.ActivityManager.RecentTaskInfo getTaskInfo() {
        android.app.ActivityManager.RecentTaskInfo createRecentTaskInfo;
        checkCallerOrSystemOrRoot();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.wm.Task anyTaskForId = this.mService.mRootWindowContainer.anyTaskForId(this.mTaskId, 1);
                    if (anyTaskForId == null) {
                        throw new java.lang.IllegalArgumentException("Unable to find task ID " + this.mTaskId);
                    }
                    createRecentTaskInfo = this.mService.getRecentTasks().createRecentTaskInfo(anyTaskForId, false, true);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return createRecentTaskInfo;
    }

    public void moveToFront(android.app.IApplicationThread iApplicationThread, java.lang.String str) {
        com.android.server.wm.WindowProcessController processController;
        checkCallerOrSystemOrRoot();
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        this.mService.assertPackageMatchesCallingUid(str);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (iApplicationThread != null) {
                        try {
                            processController = this.mService.getProcessController(iApplicationThread);
                        } catch (java.lang.Throwable th) {
                            th = th;
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    } else {
                        processController = null;
                    }
                    com.android.server.wm.BackgroundActivityStartController.BalVerdict checkBackgroundActivityStart = this.mService.mTaskSupervisor.getBackgroundActivityLaunchController().checkBackgroundActivityStart(callingUid, callingPid, str, -1, -1, processController, null, android.app.BackgroundStartPrivileges.NONE, null, null, null);
                    if (!checkBackgroundActivityStart.blocks() || this.mService.isBackgroundActivityStartsEnabled()) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        this.mService.mTaskSupervisor.startActivityFromRecents(callingPid, callingUid, this.mTaskId, null);
                        return;
                    }
                    android.util.Slog.w(TAG, "moveTaskToFront blocked: : " + checkBackgroundActivityStart);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th2) {
                    th = th2;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int startActivity(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3, android.os.Bundle bundle) {
        com.android.server.wm.Task anyTaskForId;
        android.app.IApplicationThread asInterface;
        checkCallerOrSystemOrRoot();
        this.mService.assertPackageMatchesCallingUid(str);
        int callingUserId = android.os.UserHandle.getCallingUserId();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                anyTaskForId = this.mService.mRootWindowContainer.anyTaskForId(this.mTaskId, 1);
                if (anyTaskForId == null) {
                    throw new java.lang.IllegalArgumentException("Unable to find task ID " + this.mTaskId);
                }
                asInterface = android.app.IApplicationThread.Stub.asInterface(iBinder);
                if (asInterface == null) {
                    throw new java.lang.IllegalArgumentException("Bad app thread " + asInterface);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return this.mService.getActivityStartController().obtainStarter(intent, TAG).setCaller(asInterface).setCallingPackage(str).setCallingFeatureId(str2).setResolvedType(str3).setActivityOptions(bundle).setUserId(callingUserId).setInTask(anyTaskForId).execute();
    }

    public void setExcludeFromRecents(boolean z) {
        checkCallerOrSystemOrRoot();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.wm.Task anyTaskForId = this.mService.mRootWindowContainer.anyTaskForId(this.mTaskId, 1);
                    if (anyTaskForId == null) {
                        throw new java.lang.IllegalArgumentException("Unable to find task ID " + this.mTaskId);
                    }
                    android.content.Intent baseIntent = anyTaskForId.getBaseIntent();
                    if (z) {
                        baseIntent.addFlags(8388608);
                    } else {
                        baseIntent.setFlags(baseIntent.getFlags() & (-8388609));
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th2;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }
}
