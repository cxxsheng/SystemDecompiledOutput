package com.android.server.wm;

/* loaded from: classes3.dex */
public class ActivityTaskSupervisor implements com.android.server.wm.RecentTasks.Callbacks {
    private static final int ACTIVITY_RESTRICTION_APPOP = 2;
    private static final int ACTIVITY_RESTRICTION_NONE = 0;
    private static final int ACTIVITY_RESTRICTION_PERMISSION = 1;
    static final boolean DEFER_RESUME = true;
    private static final int IDLE_NOW_MSG = 201;
    private static final int IDLE_TIMEOUT_MSG = 200;
    private static final int KILL_TASK_PROCESSES_TIMEOUT_MS = 1000;
    private static final int KILL_TASK_PROCESSES_TIMEOUT_MSG = 206;
    private static final int LAUNCH_TASK_BEHIND_COMPLETE = 212;
    private static final int LAUNCH_TIMEOUT_MSG = 204;
    private static final int MAX_TASK_IDS_PER_USER = 100000;
    static final boolean ON_TOP = true;
    static final boolean PRESERVE_WINDOWS = true;
    private static final int PROCESS_STOPPING_AND_FINISHING_MSG = 205;
    static final boolean REMOVE_FROM_RECENTS = true;
    private static final int REPORT_MULTI_WINDOW_MODE_CHANGED_MSG = 214;
    private static final int REPORT_PIP_MODE_CHANGED_MSG = 215;
    private static final int RESTART_ACTIVITY_PROCESS_TIMEOUT_MSG = 213;
    private static final int RESUME_TOP_ACTIVITY_MSG = 202;
    private static final int SCHEDULE_FINISHING_STOPPING_ACTIVITY_MS = 200;
    private static final int SLEEP_TIMEOUT_MSG = 203;
    private static final int START_HOME_MSG = 216;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private static final java.lang.String TAG_IDLE = "ActivityTaskManager";
    private static final java.lang.String TAG_PAUSE = "ActivityTaskManager";
    private static final java.lang.String TAG_RECENTS = "ActivityTaskManager";
    private static final java.lang.String TAG_ROOT_TASK = "ActivityTaskManager";
    private static final java.lang.String TAG_SWITCH = "ActivityTaskManager";
    static final java.lang.String TAG_TASKS = "ActivityTaskManager";
    private static final int TOP_RESUMED_STATE_LOSS_TIMEOUT = 500;
    private static final int TOP_RESUMED_STATE_LOSS_TIMEOUT_MSG = 217;
    private static final boolean VALIDATE_WAKE_LOCK_CALLER = false;
    private com.android.server.wm.ActivityMetricsLogger mActivityMetricsLogger;
    private android.app.AppOpsManager mAppOpsManager;
    boolean mAppVisibilitiesChangedSinceLastPause;
    com.android.server.wm.BackgroundActivityStartController mBalController;
    private int mDeferResumeCount;
    private boolean mDeferRootVisibilityUpdate;
    android.os.PowerManager.WakeLock mGoingToSleepWakeLock;
    private final com.android.server.wm.ActivityTaskSupervisor.ActivityTaskSupervisorHandler mHandler;
    private boolean mInitialized;
    private com.android.server.wm.KeyguardController mKeyguardController;
    private com.android.server.wm.LaunchParamsController mLaunchParamsController;
    com.android.server.wm.LaunchParamsPersister mLaunchParamsPersister;
    android.os.PowerManager.WakeLock mLaunchingActivityWakeLock;
    final android.os.Looper mLooper;
    com.android.server.wm.PersisterQueue mPersisterQueue;
    private android.graphics.Rect mPipModeChangedTargetRootTaskBounds;
    private android.os.PowerManager mPowerManager;
    com.android.server.wm.RecentTasks mRecentTasks;
    com.android.server.wm.RootWindowContainer mRootWindowContainer;
    private com.android.server.wm.RunningTasks mRunningTasks;
    final com.android.server.wm.ActivityTaskManagerService mService;
    private android.content.ComponentName mSystemChooserActivity;
    private com.android.server.wm.ActivityRecord mTopResumedActivity;
    private boolean mTopResumedActivityWaitingForPrev;
    private android.companion.virtual.VirtualDeviceManager mVirtualDeviceManager;
    private int mVisibilityTransactionDepth;
    private com.android.server.wm.WindowManagerService mWindowManager;
    private static final int IDLE_TIMEOUT = android.os.Build.HW_TIMEOUT_MULTIPLIER * 10000;
    private static final int SLEEP_TIMEOUT = android.os.Build.HW_TIMEOUT_MULTIPLIER * 5000;
    private static final int LAUNCH_TIMEOUT = android.os.Build.HW_TIMEOUT_MULTIPLIER * 10000;
    private static final android.util.ArrayMap<java.lang.String, java.lang.String> ACTION_TO_RUNTIME_PERMISSION = new android.util.ArrayMap<>();
    final com.android.server.wm.ActivityTaskSupervisor.TaskInfoHelper mTaskInfoHelper = new com.android.server.wm.ActivityTaskSupervisor.TaskInfoHelper();
    final com.android.server.wm.ActivityTaskSupervisor.OpaqueActivityHelper mOpaqueActivityHelper = new com.android.server.wm.ActivityTaskSupervisor.OpaqueActivityHelper();
    private final java.util.ArrayList<com.android.server.wm.WindowProcessController> mActivityStateChangedProcs = new java.util.ArrayList<>();
    private final android.util.SparseIntArray mCurTaskIdForUser = new android.util.SparseIntArray(20);
    private final java.util.ArrayList<com.android.server.wm.ActivityTaskSupervisor.WaitInfo> mWaitingActivityLaunched = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.wm.ActivityRecord> mStoppingActivities = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.wm.ActivityRecord> mFinishingActivities = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.wm.ActivityRecord> mNoHistoryActivities = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.ActivityRecord> mMultiWindowModeChangedActivities = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.ActivityRecord> mPipModeChangedActivities = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.wm.ActivityRecord> mNoAnimActivities = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.am.UserState> mStartingUsers = new java.util.ArrayList<>();
    boolean mUserLeaving = false;

    static {
        ACTION_TO_RUNTIME_PERMISSION.put("android.media.action.IMAGE_CAPTURE", "android.permission.CAMERA");
        ACTION_TO_RUNTIME_PERMISSION.put("android.media.action.VIDEO_CAPTURE", "android.permission.CAMERA");
        ACTION_TO_RUNTIME_PERMISSION.put("android.intent.action.CALL", "android.permission.CALL_PHONE");
    }

    boolean canPlaceEntityOnDisplay(int i, int i2, int i3, android.content.pm.ActivityInfo activityInfo) {
        return canPlaceEntityOnDisplay(i, i2, i3, null, activityInfo);
    }

    boolean canPlaceEntityOnDisplay(int i, int i2, int i3, com.android.server.wm.Task task) {
        return canPlaceEntityOnDisplay(i, i2, i3, task, null);
    }

    private boolean canPlaceEntityOnDisplay(int i, int i2, int i3, com.android.server.wm.Task task, android.content.pm.ActivityInfo activityInfo) {
        if (i == 0) {
            return true;
        }
        if (!this.mService.mSupportsMultiDisplay || !isCallerAllowedToLaunchOnDisplay(i2, i3, i, activityInfo)) {
            return false;
        }
        com.android.server.wm.DisplayContent displayContentOrCreate = this.mRootWindowContainer.getDisplayContentOrCreate(i);
        if (displayContentOrCreate == null) {
            return true;
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        if (activityInfo != null) {
            arrayList.add(activityInfo);
        }
        if (task != null) {
            task.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.ActivityTaskSupervisor.lambda$canPlaceEntityOnDisplay$0(arrayList, (com.android.server.wm.ActivityRecord) obj);
                }
            });
        }
        return displayContentOrCreate.mDwpcHelper.canContainActivities(arrayList, displayContentOrCreate.getWindowingMode());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$canPlaceEntityOnDisplay$0(java.util.ArrayList arrayList, com.android.server.wm.ActivityRecord activityRecord) {
        arrayList.add(activityRecord.info);
    }

    public ActivityTaskSupervisor(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, android.os.Looper looper) {
        this.mService = activityTaskManagerService;
        this.mLooper = looper;
        this.mHandler = new com.android.server.wm.ActivityTaskSupervisor.ActivityTaskSupervisorHandler(looper);
    }

    public void initialize() {
        if (this.mInitialized) {
            return;
        }
        this.mInitialized = true;
        setRunningTasks(new com.android.server.wm.RunningTasks());
        this.mActivityMetricsLogger = new com.android.server.wm.ActivityMetricsLogger(this, this.mHandler.getLooper());
        this.mKeyguardController = new com.android.server.wm.KeyguardController(this.mService, this);
        this.mPersisterQueue = new com.android.server.wm.PersisterQueue();
        this.mLaunchParamsPersister = new com.android.server.wm.LaunchParamsPersister(this.mPersisterQueue, this);
        this.mLaunchParamsController = new com.android.server.wm.LaunchParamsController(this.mService, this.mLaunchParamsPersister);
        this.mLaunchParamsController.registerDefaultModifiers(this);
        this.mBalController = new com.android.server.wm.BackgroundActivityStartController(this.mService, this);
    }

    void onSystemReady() {
        this.mLaunchParamsPersister.onSystemReady();
    }

    void onUserUnlocked(int i) {
        this.mPersisterQueue.startPersisting();
        this.mLaunchParamsPersister.onUnlockUser(i);
        scheduleStartHome("userUnlocked");
    }

    public com.android.server.wm.ActivityMetricsLogger getActivityMetricsLogger() {
        return this.mActivityMetricsLogger;
    }

    public com.android.server.wm.KeyguardController getKeyguardController() {
        return this.mKeyguardController;
    }

    android.content.ComponentName getSystemChooserActivity() {
        if (this.mSystemChooserActivity == null) {
            this.mSystemChooserActivity = android.content.ComponentName.unflattenFromString(this.mService.mContext.getResources().getString(android.R.string.config_bodyFontFamily));
        }
        return this.mSystemChooserActivity;
    }

    void setRecentTasks(com.android.server.wm.RecentTasks recentTasks) {
        if (this.mRecentTasks != null) {
            this.mRecentTasks.unregisterCallback(this);
        }
        this.mRecentTasks = recentTasks;
        this.mRecentTasks.registerCallback(this);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setRunningTasks(com.android.server.wm.RunningTasks runningTasks) {
        this.mRunningTasks = runningTasks;
    }

    com.android.server.wm.RunningTasks getRunningTasks() {
        return this.mRunningTasks;
    }

    void initPowerManagement() {
        this.mPowerManager = (android.os.PowerManager) this.mService.mContext.getSystemService(android.os.PowerManager.class);
        this.mGoingToSleepWakeLock = this.mPowerManager.newWakeLock(1, "ActivityManager-Sleep");
        this.mLaunchingActivityWakeLock = this.mPowerManager.newWakeLock(1, "*launch*");
        this.mLaunchingActivityWakeLock.setReferenceCounted(false);
    }

    void setWindowManager(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mWindowManager = windowManagerService;
        getKeyguardController().setWindowManager(windowManagerService);
    }

    void moveRecentsRootTaskToFront(java.lang.String str) {
        com.android.server.wm.Task rootTask = this.mRootWindowContainer.getDefaultTaskDisplayArea().getRootTask(0, 3);
        if (rootTask != null) {
            rootTask.moveToFront(str);
        }
    }

    void setNextTaskIdForUser(int i, int i2) {
        if (i > this.mCurTaskIdForUser.get(i2, -1)) {
            this.mCurTaskIdForUser.put(i2, i);
        }
    }

    void finishNoHistoryActivitiesIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        for (int size = this.mNoHistoryActivities.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord activityRecord2 = this.mNoHistoryActivities.get(size);
            if (!activityRecord2.finishing && activityRecord2 != activityRecord && activityRecord.occludesParent() && activityRecord2.getDisplayId() == activityRecord.getDisplayId()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 7803197981786977817L, 0, null, java.lang.String.valueOf(activityRecord2));
                activityRecord2.finishIfPossible("resume-no-history", false);
                this.mNoHistoryActivities.remove(activityRecord2);
            }
        }
    }

    private static int nextTaskIdForUser(int i, int i2) {
        int i3 = i + 1;
        if (i3 == (i2 + 1) * MAX_TASK_IDS_PER_USER) {
            return i3 - MAX_TASK_IDS_PER_USER;
        }
        return i3;
    }

    int getNextTaskIdForUser() {
        return getNextTaskIdForUser(this.mRootWindowContainer.mCurrentUser);
    }

    int getNextTaskIdForUser(int i) {
        int i2 = this.mCurTaskIdForUser.get(i, MAX_TASK_IDS_PER_USER * i);
        int nextTaskIdForUser = nextTaskIdForUser(i2, i);
        do {
            if (this.mRecentTasks.containsTaskId(nextTaskIdForUser, i) || this.mRootWindowContainer.anyTaskForId(nextTaskIdForUser, 1) != null) {
                nextTaskIdForUser = nextTaskIdForUser(nextTaskIdForUser, i);
            } else {
                this.mCurTaskIdForUser.put(i, nextTaskIdForUser);
                return nextTaskIdForUser;
            }
        } while (nextTaskIdForUser != i2);
        throw new java.lang.IllegalStateException("Cannot get an available task id. Reached limit of 100000 running tasks per user.");
    }

    void waitActivityVisibleOrLaunched(android.app.WaitResult waitResult, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityMetricsLogger.LaunchingState launchingState) {
        if (waitResult.result != 2 && waitResult.result != 0) {
            return;
        }
        com.android.server.wm.ActivityTaskSupervisor.WaitInfo waitInfo = new com.android.server.wm.ActivityTaskSupervisor.WaitInfo(waitResult, activityRecord.mActivityComponent, launchingState);
        this.mWaitingActivityLaunched.add(waitInfo);
        do {
            try {
                this.mService.mGlobalLock.wait();
            } catch (java.lang.InterruptedException e) {
            }
        } while (this.mWaitingActivityLaunched.contains(waitInfo));
    }

    void cleanupActivity(com.android.server.wm.ActivityRecord activityRecord) {
        this.mFinishingActivities.remove(activityRecord);
        stopWaitingForActivityVisible(activityRecord);
    }

    void stopWaitingForActivityVisible(com.android.server.wm.ActivityRecord activityRecord) {
        reportActivityLaunched(false, activityRecord, -1L, 0);
    }

    void reportActivityLaunched(boolean z, com.android.server.wm.ActivityRecord activityRecord, long j, int i) {
        boolean z2 = false;
        for (int size = this.mWaitingActivityLaunched.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityTaskSupervisor.WaitInfo waitInfo = this.mWaitingActivityLaunched.get(size);
            if (waitInfo.matches(activityRecord)) {
                android.app.WaitResult waitResult = waitInfo.mResult;
                waitResult.timeout = z;
                waitResult.who = activityRecord.mActivityComponent;
                waitResult.totalTime = j;
                waitResult.launchState = i;
                this.mWaitingActivityLaunched.remove(size);
                z2 = true;
            }
        }
        if (z2) {
            this.mService.mGlobalLock.notifyAll();
        }
    }

    void reportWaitingActivityLaunchedIfNeeded(com.android.server.wm.ActivityRecord activityRecord, int i) {
        if (this.mWaitingActivityLaunched.isEmpty()) {
            return;
        }
        if (i != 3 && i != 2) {
            return;
        }
        boolean z = false;
        for (int size = this.mWaitingActivityLaunched.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityTaskSupervisor.WaitInfo waitInfo = this.mWaitingActivityLaunched.get(size);
            if (waitInfo.matches(activityRecord)) {
                android.app.WaitResult waitResult = waitInfo.mResult;
                waitResult.result = i;
                if (i == 3) {
                    waitResult.who = activityRecord.mActivityComponent;
                    this.mWaitingActivityLaunched.remove(size);
                    z = true;
                }
            }
        }
        if (z) {
            this.mService.mGlobalLock.notifyAll();
        }
    }

    android.content.pm.ActivityInfo resolveActivity(android.content.Intent intent, android.content.pm.ResolveInfo resolveInfo, final int i, final android.app.ProfilerInfo profilerInfo) {
        final android.content.pm.ActivityInfo activityInfo = resolveInfo != null ? resolveInfo.activityInfo : null;
        if (activityInfo != null) {
            intent.setComponent(new android.content.ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name));
            boolean z = false;
            boolean z2 = (i & 14) != 0;
            boolean z3 = profilerInfo != null;
            if (z2 || z3) {
                if ((android.os.Build.IS_DEBUGGABLE || (activityInfo.applicationInfo.flags & 2) != 0) && !activityInfo.processName.equals("system")) {
                    z = true;
                }
                if ((z2 && !z) || (z3 && !z && !activityInfo.applicationInfo.isProfileableByShell())) {
                    android.util.Slog.w("ActivityTaskManager", "Ignore debugging for non-debuggable app: " + activityInfo.packageName);
                } else {
                    com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
                    com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            this.mService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda9
                                @Override // java.lang.Runnable
                                public final void run() {
                                    com.android.server.wm.ActivityTaskSupervisor.this.lambda$resolveActivity$1(activityInfo, i, profilerInfo);
                                }
                            });
                            try {
                                this.mService.mGlobalLock.wait();
                            } catch (java.lang.InterruptedException e) {
                            }
                        } catch (java.lang.Throwable th) {
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
            java.lang.String launchToken = intent.getLaunchToken();
            if (activityInfo.launchToken == null && launchToken != null) {
                activityInfo.launchToken = launchToken;
            }
        }
        return activityInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resolveActivity$1(android.content.pm.ActivityInfo activityInfo, int i, android.app.ProfilerInfo profilerInfo) {
        try {
            this.mService.mAmInternal.setDebugFlagsForStartingActivity(activityInfo, i, profilerInfo, this.mService.mGlobalLock);
        } finally {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003b A[Catch: all -> 0x001c, TryCatch #1 {all -> 0x001c, blocks: (B:3:0x0002, B:5:0x0013, B:8:0x0021, B:10:0x0028, B:13:0x0033, B:15:0x003b, B:16:0x003d, B:20:0x005a, B:26:0x0062, B:27:0x0065, B:29:0x001e, B:19:0x0043), top: B:2:0x0002, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    android.content.pm.ResolveInfo resolveIntent(android.content.Intent intent, java.lang.String str, int i, int i2, int i3, int i4) {
        int i5;
        long clearCallingIdentity;
        try {
            android.os.Trace.traceBegin(32L, "resolveIntent");
            int i6 = i2 | 65536 | 1024;
            try {
                if (!intent.isWebIntent()) {
                    if ((intent.getFlags() & 2048) != 0) {
                    }
                    if (!intent.isWebIntent() && (intent.getFlags() & 1024) != 0) {
                        i5 = 1;
                    } else {
                        i5 = 0;
                    }
                    if ((intent.getFlags() & 512) != 0) {
                        i5 |= 2;
                    }
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    android.content.pm.ResolveInfo resolveIntentExported = this.mService.getPackageManagerInternalLocked().resolveIntentExported(intent, str, i6, i5, i, true, i3, i4);
                    android.os.Trace.traceEnd(32L);
                    return resolveIntentExported;
                }
                android.content.pm.ResolveInfo resolveIntentExported2 = this.mService.getPackageManagerInternalLocked().resolveIntentExported(intent, str, i6, i5, i, true, i3, i4);
                android.os.Trace.traceEnd(32L);
                return resolveIntentExported2;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            i6 |= 8388608;
            if (!intent.isWebIntent()) {
            }
            i5 = 0;
            if ((intent.getFlags() & 512) != 0) {
            }
            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(32L);
            throw th;
        }
    }

    android.content.pm.ActivityInfo resolveActivity(android.content.Intent intent, java.lang.String str, int i, android.app.ProfilerInfo profilerInfo, int i2, int i3, int i4) {
        return resolveActivity(intent, resolveIntent(intent, str, i2, 0, i3, i4), i, profilerInfo);
    }

    boolean realStartActivityLocked(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.WindowProcessController windowProcessController, boolean z, boolean z2) throws android.os.RemoteException {
        java.util.ArrayList<android.app.ResultInfo> arrayList;
        java.util.ArrayList<com.android.internal.content.ReferrerIntent> arrayList2;
        com.android.server.wm.Task task;
        if (!this.mRootWindowContainer.allPausedActivitiesComplete()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 4094852138446437211L, 0, null, java.lang.String.valueOf(activityRecord));
            return false;
        }
        com.android.server.wm.Task task2 = activityRecord.getTask();
        if (z) {
            if (task2.pauseActivityIfNeeded(activityRecord, "realStart")) {
                return false;
            }
            com.android.server.wm.TaskFragment taskFragment = activityRecord.getTaskFragment();
            if (taskFragment != null && taskFragment.getResumedActivity() != null && taskFragment.startPausing(this.mUserLeaving, false, activityRecord, "realStart")) {
                return false;
            }
        }
        com.android.server.wm.Task rootTask = task2.getRootTask();
        beginDeferResume();
        windowProcessController.pauseConfigurationDispatch();
        try {
            activityRecord.startLaunchTickingLocked();
            activityRecord.lastLaunchTime = android.os.SystemClock.uptimeMillis();
            activityRecord.setProcess(windowProcessController);
            boolean z3 = (!z || activityRecord.canResumeByCompat()) ? z : false;
            activityRecord.notifyUnknownVisibilityLaunchedForKeyguardTransition();
            if (z2) {
                this.mRootWindowContainer.ensureVisibilityAndConfig(activityRecord, activityRecord.getDisplayId(), true);
            }
            if (this.mKeyguardController.checkKeyguardVisibility(activityRecord) && activityRecord.allowMoveToFront()) {
                activityRecord.setVisibility(true);
            }
            int i = activityRecord.info.applicationInfo != null ? activityRecord.info.applicationInfo.uid : -1;
            if (activityRecord.mUserId != windowProcessController.mUserId || activityRecord.info.applicationInfo.uid != i) {
                android.util.Slog.wtf("ActivityTaskManager", "User ID for activity changing for " + activityRecord + " appInfo.uid=" + activityRecord.info.applicationInfo.uid + " info.ai.uid=" + i + " old=" + activityRecord.app + " new=" + windowProcessController);
            }
            android.app.IActivityClientController iActivityClientController = windowProcessController.hasEverLaunchedActivity() ? null : this.mService.mActivityClientController;
            activityRecord.launchCount++;
            com.android.server.wm.LockTaskController lockTaskController = this.mService.getLockTaskController();
            if (task2.mLockTaskAuth == 2 || task2.mLockTaskAuth == 4 || (task2.mLockTaskAuth == 3 && lockTaskController.getLockTaskModeState() == 1)) {
                lockTaskController.startLockTaskMode(task2, false, 0);
            }
            try {
            } catch (android.os.RemoteException e) {
                e = e;
            }
            try {
                if (!windowProcessController.hasThread()) {
                    throw new android.os.RemoteException();
                }
                if (z3) {
                    java.util.ArrayList<android.app.ResultInfo> arrayList3 = activityRecord.results;
                    arrayList2 = activityRecord.newIntents;
                    arrayList = arrayList3;
                } else {
                    arrayList = null;
                    arrayList2 = null;
                }
                com.android.server.wm.EventLogTags.writeWmRestartActivity(activityRecord.mUserId, java.lang.System.identityHashCode(activityRecord), task2.mTaskId, activityRecord.shortComponentName);
                if (activityRecord.isActivityTypeHome()) {
                    updateHomeProcess(task2.getBottomMostActivity().app);
                }
                this.mService.getPackageManagerInternalLocked().notifyPackageUse(activityRecord.intent.getComponent().getPackageName(), 0);
                this.mService.getAppWarningsLocked().onStartActivity(activityRecord);
                android.content.res.Configuration prepareConfigurationForLaunchingActivity = windowProcessController.prepareConfigurationForLaunchingActivity();
                android.content.res.Configuration mergedOverrideConfiguration = activityRecord.getMergedOverrideConfiguration();
                activityRecord.setLastReportedConfiguration(prepareConfigurationForLaunchingActivity, mergedOverrideConfiguration);
                android.window.ActivityWindowInfo activityWindowInfo = activityRecord.getActivityWindowInfo();
                activityRecord.setLastReportedActivityWindowInfo(activityWindowInfo);
                logIfTransactionTooLarge(activityRecord.intent, activityRecord.getSavedState());
                com.android.server.wm.TaskFragment organizedTaskFragment = activityRecord.getOrganizedTaskFragment();
                if (organizedTaskFragment != null) {
                    this.mService.mTaskFragmentOrganizerController.dispatchPendingInfoChangedEvent(organizedTaskFragment);
                }
                boolean isTransitionForward = activityRecord.isTransitionForward();
                android.app.servertransaction.ClientTransactionItem obtain = android.app.servertransaction.LaunchActivityItem.obtain(activityRecord.token, activityRecord.intent, java.lang.System.identityHashCode(activityRecord), activityRecord.info, prepareConfigurationForLaunchingActivity, mergedOverrideConfiguration, getDeviceIdForDisplayId(activityRecord.getDisplayId()), activityRecord.getFilteredReferrer(activityRecord.launchedFromPackage), task2.voiceInteractor, windowProcessController.getReportedProcState(), activityRecord.getSavedState(), activityRecord.getPersistentSavedState(), arrayList, arrayList2, activityRecord.takeSceneTransitionInfo(), isTransitionForward, windowProcessController.createProfilerInfoIfNeeded(), activityRecord.assistToken, iActivityClientController, activityRecord.shareableActivityToken, activityRecord.getLaunchedFromBubble(), activityRecord.getTaskFragment().getFragmentToken(), activityRecord.initialCallerInfoAccessToken, activityWindowInfo);
                android.app.servertransaction.ResumeActivityItem obtain2 = z3 ? android.app.servertransaction.ResumeActivityItem.obtain(activityRecord.token, isTransitionForward, activityRecord.shouldSendCompatFakeFocus()) : android.app.servertransaction.PauseActivityItem.obtain(activityRecord.token);
                if (com.android.server.wm.ClientLifecycleManager.shouldDispatchCompatClientTransactionIndependently(activityRecord.mTargetSdk)) {
                    this.mService.getLifecycleManager().dispatchPendingTransaction(windowProcessController.getThread());
                }
                this.mService.getLifecycleManager().scheduleTransactionAndLifecycleItems(windowProcessController.getThread(), obtain, obtain2, true);
                if (prepareConfigurationForLaunchingActivity.seq > this.mRootWindowContainer.getConfiguration().seq) {
                    windowProcessController.setLastReportedConfiguration(prepareConfigurationForLaunchingActivity);
                }
                if ((windowProcessController.mInfo.privateFlags & 2) != 0 && this.mService.mHasHeavyWeightFeature && windowProcessController.mName.equals(windowProcessController.mInfo.packageName)) {
                    if (this.mService.mHeavyWeightProcess != null && this.mService.mHeavyWeightProcess != windowProcessController) {
                        android.util.Slog.w("ActivityTaskManager", "Starting new heavy weight process " + windowProcessController + " when already running " + this.mService.mHeavyWeightProcess);
                    }
                    this.mService.setHeavyWeightProcess(activityRecord);
                }
                endDeferResume();
                windowProcessController.resumeConfigurationDispatch();
                activityRecord.launchFailed = false;
                if (z3 && readyToResume()) {
                    task = rootTask;
                    task.minimalResumeActivityLocked(activityRecord);
                } else {
                    task = rootTask;
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 1045761390992110034L, 0, null, java.lang.String.valueOf(activityRecord));
                    activityRecord.setState(com.android.server.wm.ActivityRecord.State.PAUSED, "realStartActivityLocked");
                    this.mRootWindowContainer.executeAppTransitionForAllDisplay();
                }
                windowProcessController.onStartActivity(this.mService.mTopProcessState, activityRecord.info);
                if (this.mRootWindowContainer.isTopDisplayFocusedRootTask(task)) {
                    this.mService.getActivityStartController().startSetupActivity();
                }
                if (activityRecord.app == null) {
                    return true;
                }
                activityRecord.app.updateServiceConnectionActivities();
                return true;
            } catch (android.os.RemoteException e2) {
                e = e2;
                if (!activityRecord.launchFailed) {
                    activityRecord.launchFailed = true;
                    activityRecord.detachFromProcess();
                    throw e;
                }
                android.util.Slog.e("ActivityTaskManager", "Second failure launching " + activityRecord.intent.getComponent().flattenToShortString() + ", giving up", e);
                windowProcessController.appDied("2nd-crash");
                activityRecord.finishIfPossible("2nd-crash", false);
                endDeferResume();
                windowProcessController.resumeConfigurationDispatch();
                return false;
            }
        } catch (java.lang.Throwable th) {
            endDeferResume();
            windowProcessController.resumeConfigurationDispatch();
            throw th;
        }
    }

    void updateHomeProcess(com.android.server.wm.WindowProcessController windowProcessController) {
        if (windowProcessController != null && this.mService.mHomeProcess != windowProcessController) {
            scheduleStartHome("homeChanged");
            this.mService.mHomeProcess = windowProcessController;
        }
    }

    private void scheduleStartHome(java.lang.String str) {
        if (!this.mHandler.hasMessages(216)) {
            this.mHandler.obtainMessage(216, str).sendToTarget();
        }
    }

    private void logIfTransactionTooLarge(android.content.Intent intent, android.os.Bundle bundle) {
        int i;
        android.os.Bundle extras;
        if (intent != null && (extras = intent.getExtras()) != null) {
            i = extras.getSize();
        } else {
            i = 0;
        }
        int size = bundle != null ? bundle.getSize() : 0;
        if (i + size > 200000) {
            android.util.Slog.e("ActivityTaskManager", "Transaction too large, intent: " + intent + ", extras size: " + i + ", icicle size: " + size);
        }
    }

    void startSpecificActivity(com.android.server.wm.ActivityRecord activityRecord, boolean z, boolean z2) {
        boolean z3;
        com.android.server.wm.WindowProcessController processController = this.mService.getProcessController(activityRecord.processName, activityRecord.info.applicationInfo.uid);
        if (processController != null && processController.hasThread()) {
            try {
                realStartActivityLocked(activityRecord, processController, z, z2);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w("ActivityTaskManager", "Exception when starting activity " + activityRecord.intent.getComponent().flattenToShortString(), e);
                this.mService.mProcessNames.remove(processController.mName, processController.mUid);
                this.mService.mProcessMap.remove(processController.getPid());
                z3 = true;
            }
        } else {
            if (com.android.server.wm.ActivityTaskManagerService.isSdkSandboxActivityIntent(this.mService.mContext, activityRecord.intent)) {
                android.util.Slog.e("ActivityTaskManager", "Abort sandbox activity launching as no sandbox process to host it.");
                activityRecord.finishIfPossible("No sandbox process for the activity", false);
                activityRecord.launchFailed = true;
                activityRecord.detachFromProcess();
                return;
            }
            z3 = false;
        }
        activityRecord.notifyUnknownVisibilityLaunchedForKeyguardTransition();
        boolean z4 = z && activityRecord.isTopRunningActivity();
        this.mService.startProcessAsync(activityRecord, z3, z4, z4 ? com.android.server.am.HostingRecord.HOSTING_TYPE_TOP_ACTIVITY : com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY);
    }

    boolean checkStartAnyActivityPermission(android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, java.lang.String str, int i, int i2, int i3, java.lang.String str2, @android.annotation.Nullable java.lang.String str3, boolean z, boolean z2, com.android.server.wm.WindowProcessController windowProcessController, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.Task task) {
        java.lang.String str4;
        boolean z3 = this.mService.getRecentTasks() != null && this.mService.getRecentTasks().isCallerRecents(i3);
        if (com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.START_ANY_ACTIVITY", i2, i3) == 0 || (z3 && z2)) {
            return true;
        }
        int componentRestrictionForCallingPackage = getComponentRestrictionForCallingPackage(activityInfo, str2, str3, i2, i3, z);
        int actionRestrictionForCallingPackage = getActionRestrictionForCallingPackage(intent.getAction(), str2, str3, i2, i3);
        if (componentRestrictionForCallingPackage != 1 && actionRestrictionForCallingPackage != 1) {
            if (actionRestrictionForCallingPackage == 2) {
                android.util.Slog.w("ActivityTaskManager", "Appop Denial: starting " + intent.toString() + " from " + windowProcessController + " (pid=" + i2 + ", uid=" + i3 + ") requires " + android.app.AppOpsManager.permissionToOp(ACTION_TO_RUNTIME_PERMISSION.get(intent.getAction())));
                return false;
            }
            if (componentRestrictionForCallingPackage != 2) {
                return true;
            }
            android.util.Slog.w("ActivityTaskManager", "Appop Denial: starting " + intent.toString() + " from " + windowProcessController + " (pid=" + i2 + ", uid=" + i3 + ") requires appop " + android.app.AppOpsManager.permissionToOp(activityInfo.permission));
            return false;
        }
        if (activityRecord != null) {
            activityRecord.sendResult(-1, str, i, 0, null, null, null);
        }
        if (actionRestrictionForCallingPackage == 1) {
            str4 = "Permission Denial: starting " + intent.toString() + " from " + windowProcessController + " (pid=" + i2 + ", uid=" + i3 + ") with revoked permission " + ACTION_TO_RUNTIME_PERMISSION.get(intent.getAction());
        } else if (!activityInfo.exported) {
            str4 = "Permission Denial: starting " + intent.toString() + " from " + windowProcessController + " (pid=" + i2 + ", uid=" + i3 + ") not exported from uid " + activityInfo.applicationInfo.uid;
        } else {
            str4 = "Permission Denial: starting " + intent.toString() + " from " + windowProcessController + " (pid=" + i2 + ", uid=" + i3 + ") requires " + activityInfo.permission;
        }
        android.util.Slog.w("ActivityTaskManager", str4);
        throw new java.lang.SecurityException(str4);
    }

    boolean isCallerAllowedToLaunchOnTaskDisplayArea(int i, int i2, com.android.server.wm.TaskDisplayArea taskDisplayArea, android.content.pm.ActivityInfo activityInfo) {
        return isCallerAllowedToLaunchOnDisplay(i, i2, taskDisplayArea != null ? taskDisplayArea.getDisplayId() : 0, activityInfo);
    }

    boolean isCallerAllowedToLaunchOnDisplay(int i, int i2, int i3, android.content.pm.ActivityInfo activityInfo) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -8529426827020190143L, 21, null, java.lang.Long.valueOf(i3), java.lang.Long.valueOf(i), java.lang.Long.valueOf(i2));
        if (i == -1 && i2 == -1) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 9147909968067116569L, 0, null, null);
            return true;
        }
        com.android.server.wm.DisplayContent displayContentOrCreate = this.mRootWindowContainer.getDisplayContentOrCreate(i3);
        if (displayContentOrCreate == null || displayContentOrCreate.isRemoved()) {
            android.util.Slog.w("ActivityTaskManager", "Launch on display check: display not found");
            return false;
        }
        if ((displayContentOrCreate.mDisplay.getFlags() & 8192) != 0) {
            android.util.Slog.w("ActivityTaskManager", "Launch on display check: activity launch is not allowed on rear display");
            return false;
        }
        if (com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.INTERNAL_SYSTEM_WINDOW", i, i2) == 0) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 4781135167649953680L, 0, null, null);
            return true;
        }
        boolean isUidPresent = displayContentOrCreate.isUidPresent(i2);
        android.view.Display display = displayContentOrCreate.mDisplay;
        if (!display.isTrusted()) {
            if ((activityInfo.flags & Integer.MIN_VALUE) == 0) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 7828411869729995271L, 0, null, null);
                return false;
            }
            if (com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.ACTIVITY_EMBEDDING", i, i2) == -1 && !isUidPresent) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -2215878620906309682L, 0, null, null);
                return false;
            }
        }
        if (!displayContentOrCreate.isPrivate()) {
            int userId = android.os.UserHandle.getUserId(i2);
            int displayId = display.getDisplayId();
            boolean isUserVisible = this.mWindowManager.mUmInternal.isUserVisible(userId, displayId);
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 986565579776405555L, 20, null, java.lang.String.valueOf(isUserVisible ? "allow" : "disallow"), java.lang.Long.valueOf(userId), java.lang.Long.valueOf(displayId));
            return isUserVisible;
        }
        if (display.getOwnerUid() == i2) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -2201418325681949201L, 0, null, null);
            return true;
        }
        if (!isUidPresent) {
            android.util.Slog.w("ActivityTaskManager", "Launch on display check: denied");
            return false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -4258279435559028377L, 0, null, null);
        return true;
    }

    android.content.pm.UserInfo getUserInfo(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.os.UserManager.get(this.mService.mContext).getUserInfo(i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    int getDeviceIdForDisplayId(int i) {
        if (i == 0 || i == -1) {
            return 0;
        }
        if (this.mVirtualDeviceManager == null) {
            if (this.mService.mHasCompanionDeviceSetupFeature) {
                this.mVirtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) this.mService.mContext.getSystemService(android.companion.virtual.VirtualDeviceManager.class);
            }
            if (this.mVirtualDeviceManager == null) {
                return 0;
            }
        }
        return this.mVirtualDeviceManager.getDeviceIdForDisplayId(i);
    }

    private android.app.AppOpsManager getAppOpsManager() {
        if (this.mAppOpsManager == null) {
            this.mAppOpsManager = (android.app.AppOpsManager) this.mService.mContext.getSystemService(android.app.AppOpsManager.class);
        }
        return this.mAppOpsManager;
    }

    com.android.server.wm.BackgroundActivityStartController getBackgroundActivityLaunchController() {
        return this.mBalController;
    }

    private int getComponentRestrictionForCallingPackage(android.content.pm.ActivityInfo activityInfo, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i, int i2, boolean z) {
        int permissionToOpCode;
        if (z || com.android.server.wm.ActivityTaskManagerService.checkComponentPermission(activityInfo.permission, i, i2, activityInfo.applicationInfo.uid, activityInfo.exported) != -1) {
            return (activityInfo.permission == null || (permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(activityInfo.permission)) == -1 || getAppOpsManager().noteOpNoThrow(permissionToOpCode, i2, str, str2, "") == 0 || z) ? 0 : 2;
        }
        return 1;
    }

    private int getActionRestrictionForCallingPackage(java.lang.String str, java.lang.String str2, @android.annotation.Nullable java.lang.String str3, int i, int i2) {
        java.lang.String str4;
        if (str == null || (str4 = ACTION_TO_RUNTIME_PERMISSION.get(str)) == null) {
            return 0;
        }
        try {
            if (!com.android.internal.util.ArrayUtils.contains(this.mService.mContext.getPackageManager().getPackageInfoAsUser(str2, 4096, android.os.UserHandle.getUserId(i2)).requestedPermissions, str4)) {
                return 0;
            }
            if (com.android.server.wm.ActivityTaskManagerService.checkPermission(str4, i, i2) == -1) {
                return 1;
            }
            int permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(str4);
            if (permissionToOpCode == -1 || getAppOpsManager().noteOpNoThrow(permissionToOpCode, i2, str2, str3, "") == 0) {
                return 0;
            }
            if ("android.permission.CAMERA".equals(str4)) {
                android.hardware.SensorPrivacyManagerInternal sensorPrivacyManagerInternal = (android.hardware.SensorPrivacyManagerInternal) com.android.server.LocalServices.getService(android.hardware.SensorPrivacyManagerInternal.class);
                android.os.UserHandle userHandleForUid = android.os.UserHandle.getUserHandleForUid(i2);
                if (sensorPrivacyManagerInternal.isSensorPrivacyEnabled(userHandleForUid.getIdentifier(), 2) && ((android.app.AppOpsManagerInternal) com.android.server.LocalServices.getService(android.app.AppOpsManagerInternal.class)).getOpRestrictionCount(26, userHandleForUid, str2, (java.lang.String) null) == 1) {
                    return 0;
                }
            }
            return 2;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.i("ActivityTaskManager", "Cannot find package info for " + str2);
            return 0;
        }
    }

    void setLaunchSource(int i) {
        this.mLaunchingActivityWakeLock.setWorkSource(new android.os.WorkSource(i));
    }

    void acquireLaunchWakelock() {
        this.mLaunchingActivityWakeLock.acquire();
        if (!this.mHandler.hasMessages(204)) {
            this.mHandler.sendEmptyMessageDelayed(204, LAUNCH_TIMEOUT);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void checkFinishBootingLocked() {
        boolean isBooting = this.mService.isBooting();
        boolean z = false;
        this.mService.setBooting(false);
        if (!this.mService.isBooted()) {
            z = true;
            this.mService.setBooted(true);
        }
        if (isBooting || z) {
            this.mService.postFinishBooting(isBooting, z);
        }
    }

    void activityIdleInternal(com.android.server.wm.ActivityRecord activityRecord, boolean z, boolean z2, android.content.res.Configuration configuration) {
        if (activityRecord != null) {
            this.mHandler.removeMessages(200, activityRecord);
            activityRecord.finishLaunchTickingLocked();
            if (z) {
                reportActivityLaunched(z, activityRecord, -1L, -1);
            }
            if (configuration != null) {
                activityRecord.setLastReportedGlobalConfiguration(configuration);
            }
            activityRecord.idle = true;
            if ((this.mService.isBooting() && this.mRootWindowContainer.allResumedActivitiesIdle()) || z) {
                checkFinishBootingLocked();
            }
            activityRecord.mRelaunchReason = 0;
        }
        if (this.mRootWindowContainer.allResumedActivitiesIdle()) {
            if (activityRecord != null) {
                this.mService.scheduleAppGcsLocked();
                this.mRecentTasks.onActivityIdle(activityRecord);
            }
            if (this.mLaunchingActivityWakeLock.isHeld()) {
                this.mHandler.removeMessages(204);
                this.mLaunchingActivityWakeLock.release();
            }
            this.mRootWindowContainer.ensureActivitiesVisible();
        }
        processStoppingAndFinishingActivities(activityRecord, z2, "idle");
        if (!this.mStartingUsers.isEmpty()) {
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mStartingUsers);
            this.mStartingUsers.clear();
            for (int i = 0; i < arrayList.size(); i++) {
                com.android.server.am.UserState userState = (com.android.server.am.UserState) arrayList.get(i);
                com.android.server.utils.Slogf.i("ActivityTaskManager", "finishing switch of user %d", java.lang.Integer.valueOf(userState.mHandle.getIdentifier()));
                this.mService.mAmInternal.finishUserSwitch(userState);
            }
        }
        this.mService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityTaskSupervisor.this.lambda$activityIdleInternal$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$activityIdleInternal$2() {
        this.mService.mAmInternal.trimApplications();
    }

    void findTaskToMoveToFront(com.android.server.wm.Task task, int i, android.app.ActivityOptions activityOptions, java.lang.String str, boolean z) {
        boolean z2;
        java.lang.String str2;
        com.android.server.wm.Task task2;
        boolean z3;
        boolean z4;
        com.android.server.wm.Task rootTask = task.getRootTask();
        if (rootTask == null) {
            android.util.Slog.e("ActivityTaskManager", "findTaskToMoveToFront: can't move task=" + task + " to front. Root task is null");
            return;
        }
        if ((i & 2) == 0) {
            try {
                this.mUserLeaving = true;
            } catch (java.lang.Throwable th) {
                th = th;
                z2 = false;
                this.mUserLeaving = z2;
                this.mService.continueWindowLayout();
                throw th;
            }
        }
        this.mService.deferWindowLayout();
        com.android.server.wm.Transition createTransition = task.mTransitionController.isShellTransitionsEnabled() ? task.mTransitionController.isCollecting() ? null : task.mTransitionController.createTransition(3) : null;
        task.mTransitionController.collect(task);
        java.lang.String str3 = str + " findTaskToMoveToFront";
        if (task.isResizeable() && canUseActivityOptionsLaunchBounds(activityOptions)) {
            com.android.server.wm.Task orCreateRootTask = this.mRootWindowContainer.getOrCreateRootTask(null, activityOptions, task, true);
            if (orCreateRootTask != rootTask) {
                moveHomeRootTaskToFrontIfNeeded(i, orCreateRootTask.getDisplayArea(), str3);
                task.reparent(orCreateRootTask, true, 1, false, true, str3);
                str2 = str3;
                z4 = true;
                rootTask = orCreateRootTask;
            } else {
                str2 = str3;
                z4 = false;
            }
            task.setBounds(activityOptions.getLaunchBounds());
            boolean z5 = z4;
            task2 = rootTask;
            z3 = z5;
        } else {
            str2 = str3;
            task2 = rootTask;
            z3 = false;
        }
        if (!z3) {
            moveHomeRootTaskToFrontIfNeeded(i, task2.getDisplayArea(), str2);
        }
        com.android.server.wm.ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity();
        com.android.server.wm.Transition transition = createTransition;
        z2 = false;
        try {
            task2.moveTaskToFront(task, false, activityOptions, topNonFinishingActivity == null ? null : topNonFinishingActivity.appTimeTracker, str2);
            handleNonResizableTaskIfNeeded(task, 0, this.mRootWindowContainer.getDefaultTaskDisplayArea(), task2, z);
            if (topNonFinishingActivity != null && (activityOptions == null || !activityOptions.getDisableStartingWindow())) {
                topNonFinishingActivity.showStartingWindow(true);
            }
            if (transition != null) {
                task.mTransitionController.requestStartTransition(transition, task, activityOptions != null ? activityOptions.getRemoteTransition() : null, null);
            }
            this.mUserLeaving = false;
            this.mService.continueWindowLayout();
        } catch (java.lang.Throwable th2) {
            th = th2;
            this.mUserLeaving = z2;
            this.mService.continueWindowLayout();
            throw th;
        }
    }

    private void moveHomeRootTaskToFrontIfNeeded(int i, com.android.server.wm.TaskDisplayArea taskDisplayArea, java.lang.String str) {
        com.android.server.wm.Task focusedRootTask = taskDisplayArea.getFocusedRootTask();
        if ((taskDisplayArea.getWindowingMode() == 1 && (i & 1) != 0) || (focusedRootTask != null && focusedRootTask.isActivityTypeRecents())) {
            taskDisplayArea.moveHomeRootTaskToFront(str);
        }
    }

    boolean canUseActivityOptionsLaunchBounds(android.app.ActivityOptions activityOptions) {
        if (activityOptions == null || activityOptions.getLaunchBounds() == null) {
            return false;
        }
        return (this.mService.mSupportsPictureInPicture && activityOptions.getLaunchWindowingMode() == 2) || this.mService.mSupportsFreeformWindowManagement;
    }

    com.android.server.wm.LaunchParamsController getLaunchParamsController() {
        return this.mLaunchParamsController;
    }

    private void removePinnedRootTaskInSurfaceTransaction(com.android.server.wm.Task task) {
        task.mTransitionController.requestTransitionIfNeeded(4, 0, task, task.mDisplayContent, null, null);
        task.cancelAnimation();
        task.setForceHidden(1, true);
        task.ensureActivitiesVisible(null);
        activityIdleInternal(null, false, true, null);
        com.android.server.wm.DisplayContent displayContent = this.mRootWindowContainer.getDisplayContent(0);
        this.mService.deferWindowLayout();
        try {
            task.setWindowingMode(0);
            if (task.getWindowingMode() != 5) {
                task.setBounds(null);
            }
            displayContent.getDefaultTaskDisplayArea().positionTaskBehindHome(task);
            task.setForceHidden(1, false);
            this.mRootWindowContainer.ensureActivitiesVisible();
            this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            this.mService.continueWindowLayout();
        } catch (java.lang.Throwable th) {
            this.mService.continueWindowLayout();
            throw th;
        }
    }

    void removeRootTask(com.android.server.wm.Task task) {
        if (task.getWindowingMode() == 2) {
            removePinnedRootTaskInSurfaceTransaction(task);
        } else {
            task.forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.ActivityTaskSupervisor.this.lambda$removeRootTask$3((com.android.server.wm.Task) obj);
                }
            }, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeRootTask$3(com.android.server.wm.Task task) {
        removeTask(task, true, true, "remove-root-task");
    }

    boolean removeTaskById(int i, boolean z, boolean z2, java.lang.String str, int i2, int i3) {
        com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i, 1);
        if (anyTaskForId != null) {
            removeTask(anyTaskForId, z, z2, str, i2, i3, null);
            return true;
        }
        android.util.Slog.w("ActivityTaskManager", "Request to remove task ignored for non-existent task " + i);
        return false;
    }

    void removeTask(com.android.server.wm.Task task, boolean z, boolean z2, java.lang.String str) {
        removeTask(task, z, z2, str, 1000, -1, null);
    }

    void removeTask(com.android.server.wm.Task task, boolean z, boolean z2, java.lang.String str, int i, int i2, java.lang.String str2) {
        if (task.mInRemoveTask) {
            return;
        }
        task.mTransitionController.requestCloseTransitionIfNeeded(task);
        if (z) {
            java.util.ArrayList arrayList = null;
            for (int size = this.mStoppingActivities.size() - 1; size >= 0; size--) {
                com.android.server.wm.ActivityRecord activityRecord = this.mStoppingActivities.get(size);
                if (!activityRecord.finishing && activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED) && activityRecord.getTask() == task) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(activityRecord);
                    this.mStoppingActivities.remove(size);
                }
            }
            if (arrayList != null) {
                for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                    ((com.android.server.wm.ActivityRecord) arrayList.get(size2)).stopIfPossible();
                }
            }
        }
        task.mInRemoveTask = true;
        try {
            task.removeActivities(str, false);
            cleanUpRemovedTask(task, z, z2);
            this.mService.getLockTaskController().clearLockedTask(task);
            this.mService.getTaskChangeNotificationController().notifyTaskStackChanged();
            if (task.isPersistable) {
                this.mService.notifyTaskPersisterLocked(null, true);
            }
            this.mBalController.checkActivityAllowedToClearTask(task, i, i2, str2);
            task.mInRemoveTask = false;
        } catch (java.lang.Throwable th) {
            task.mInRemoveTask = false;
            throw th;
        }
    }

    static java.lang.CharSequence getApplicationLabel(android.content.pm.PackageManager packageManager, java.lang.String str) {
        try {
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, android.content.pm.PackageManager.ApplicationInfoFlags.of(0L)));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return str;
        }
    }

    private void cleanUpRemovedTask(com.android.server.wm.Task task, boolean z, boolean z2) {
        if (z2) {
            this.mRecentTasks.remove(task);
        }
        android.content.Intent baseIntent = task.getBaseIntent();
        android.content.ComponentName component = baseIntent != null ? baseIntent.getComponent() : null;
        if (component == null) {
            android.util.Slog.w("ActivityTaskManager", "No component for base intent of task: " + task);
            return;
        }
        this.mService.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda2
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                ((android.app.ActivityManagerInternal) obj).cleanUpServices(((java.lang.Integer) obj2).intValue(), (android.content.ComponentName) obj3, (android.content.Intent) obj4);
            }
        }, this.mService.mAmInternal, java.lang.Integer.valueOf(task.mUserId), component, new android.content.Intent(baseIntent)));
        if (!z) {
            return;
        }
        com.android.server.wm.ActivityRecord topMostActivity = task.getTopMostActivity();
        if (topMostActivity != null && topMostActivity.finishing && !topMostActivity.mAppStopped && topMostActivity.lastVisibleTime > 0 && !task.mKillProcessesOnDestroyed && topMostActivity.hasProcess()) {
            task.mKillProcessesOnDestroyed = true;
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(206, task), 1000L);
        } else {
            killTaskProcessesIfPossible(task);
        }
    }

    void removeTimeoutOfKillProcessesOnProcessDied(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull com.android.server.wm.Task task) {
        if (activityRecord.packageName.equals(task.getBasePackageName())) {
            task.mKillProcessesOnDestroyed = false;
            this.mHandler.removeMessages(206, task);
        }
    }

    void killTaskProcessesOnDestroyedIfNeeded(com.android.server.wm.Task task) {
        if (task == null || !task.mKillProcessesOnDestroyed) {
            return;
        }
        final int[] iArr = new int[1];
        task.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.ActivityTaskSupervisor.lambda$killTaskProcessesOnDestroyedIfNeeded$4(iArr, (com.android.server.wm.ActivityRecord) obj);
            }
        });
        if (iArr[0] > 1) {
            return;
        }
        this.mHandler.removeMessages(206, task);
        killTaskProcessesIfPossible(task);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$killTaskProcessesOnDestroyedIfNeeded$4(int[] iArr, com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.finishing && activityRecord.lastVisibleTime > 0 && activityRecord.attachedToProcess()) {
            iArr[0] = iArr[0] + 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void killTaskProcessesIfPossible(com.android.server.wm.Task task) {
        task.mKillProcessesOnDestroyed = false;
        java.lang.String basePackageName = task.getBasePackageName();
        android.util.ArrayMap map = this.mService.mProcessNames.getMap();
        java.util.ArrayList arrayList = null;
        for (int i = 0; i < map.size(); i++) {
            android.util.SparseArray sparseArray = (android.util.SparseArray) map.valueAt(i);
            for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                com.android.server.wm.WindowProcessController windowProcessController = (com.android.server.wm.WindowProcessController) sparseArray.valueAt(i2);
                if (windowProcessController.mUserId == task.mUserId && windowProcessController != this.mService.mHomeProcess && windowProcessController.containsPackage(basePackageName)) {
                    if (!windowProcessController.shouldKillProcessForRemovedTask(task) || windowProcessController.hasForegroundServices()) {
                        return;
                    }
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(windowProcessController);
                }
            }
        }
        if (arrayList == null) {
            return;
        }
        this.mService.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((android.app.ActivityManagerInternal) obj).killProcessesForRemovedTask((java.util.ArrayList) obj2);
            }
        }, this.mService.mAmInternal, arrayList));
    }

    boolean restoreRecentTaskLocked(com.android.server.wm.Task task, android.app.ActivityOptions activityOptions, boolean z) {
        com.android.server.wm.Task orCreateRootTask = this.mRootWindowContainer.getOrCreateRootTask(null, activityOptions, task, z);
        com.android.server.wm.WindowContainer parent = task.getParent();
        if (parent == orCreateRootTask || task == orCreateRootTask) {
            return true;
        }
        if (parent != null) {
            task.reparent(orCreateRootTask, Integer.MAX_VALUE, true, "restoreRecentTaskLocked");
            return true;
        }
        orCreateRootTask.addChild((com.android.server.wm.WindowContainer) task, z, true);
        return true;
    }

    @Override // com.android.server.wm.RecentTasks.Callbacks
    public void onRecentTaskAdded(com.android.server.wm.Task task) {
        task.touchActiveTime();
    }

    @Override // com.android.server.wm.RecentTasks.Callbacks
    public void onRecentTaskRemoved(com.android.server.wm.Task task, boolean z, boolean z2) {
        if (z) {
            removeTaskById(task.mTaskId, z2, false, "recent-task-trimmed", 1000, -1);
        }
        task.removedFromRecents();
    }

    com.android.server.wm.Task getReparentTargetRootTask(com.android.server.wm.Task task, com.android.server.wm.Task task2, boolean z) {
        com.android.server.wm.Task rootTask = task.getRootTask();
        int i = task2.mTaskId;
        boolean inMultiWindowMode = task2.inMultiWindowMode();
        if (rootTask != null && rootTask.mTaskId == i) {
            android.util.Slog.w("ActivityTaskManager", "Can not reparent to same root task, task=" + task + " already in rootTaskId=" + i + " by " + android.os.Debug.getCallers(8));
            return rootTask;
        }
        if (inMultiWindowMode && !this.mService.mSupportsMultiWindow) {
            throw new java.lang.IllegalArgumentException("Device doesn't support multi-window, can not reparent task=" + task + " to root-task=" + task2);
        }
        if (task2.getDisplayId() != 0 && !this.mService.mSupportsMultiDisplay) {
            throw new java.lang.IllegalArgumentException("Device doesn't support multi-display, can not reparent task=" + task + " to rootTaskId=" + i);
        }
        if (task2.getWindowingMode() == 5 && !this.mService.mSupportsFreeformWindowManagement) {
            throw new java.lang.IllegalArgumentException("Device doesn't support freeform, can not reparent task=" + task);
        }
        if (task2.inPinnedWindowingMode()) {
            throw new java.lang.IllegalArgumentException("No support to reparent to PIP, task=" + task);
        }
        if (inMultiWindowMode && !task.supportsMultiWindowInDisplayArea(task2.getDisplayArea())) {
            android.util.Slog.w("ActivityTaskManager", "Can not move unresizeable task=" + task + " to multi-window root task=" + task2 + " Moving to a fullscreen root task instead.");
            if (rootTask != null) {
                return rootTask;
            }
            return task2.getDisplayArea().createRootTask(1, task2.getActivityType(), z);
        }
        return task2;
    }

    void goingToSleepLocked() {
        scheduleSleepTimeout();
        if (!this.mGoingToSleepWakeLock.isHeld()) {
            this.mGoingToSleepWakeLock.acquire();
            if (this.mLaunchingActivityWakeLock.isHeld()) {
                this.mLaunchingActivityWakeLock.release();
                this.mHandler.removeMessages(204);
            }
        }
        this.mRootWindowContainer.applySleepTokens(false);
        checkReadyForSleepLocked(true);
    }

    boolean shutdownLocked(int i) {
        boolean z;
        goingToSleepLocked();
        long currentTimeMillis = java.lang.System.currentTimeMillis() + i;
        while (true) {
            z = true;
            if (this.mRootWindowContainer.putTasksToSleep(true, true)) {
                z = false;
                break;
            }
            long currentTimeMillis2 = currentTimeMillis - java.lang.System.currentTimeMillis();
            if (currentTimeMillis2 > 0) {
                try {
                    this.mService.mGlobalLock.wait(currentTimeMillis2);
                } catch (java.lang.InterruptedException e) {
                }
            } else {
                android.util.Slog.w("ActivityTaskManager", "Activity manager shutdown timed out");
                break;
            }
        }
        checkReadyForSleepLocked(false);
        return z;
    }

    void comeOutOfSleepIfNeededLocked() {
        removeSleepTimeouts();
        if (this.mGoingToSleepWakeLock.isHeld()) {
            this.mGoingToSleepWakeLock.release();
        }
    }

    void checkReadyForSleepLocked(boolean z) {
        if (!this.mService.isSleepingOrShuttingDownLocked() || !this.mRootWindowContainer.putTasksToSleep(z, false)) {
            return;
        }
        this.mService.endPowerMode(3);
        this.mRootWindowContainer.rankTaskLayers();
        removeSleepTimeouts();
        if (this.mGoingToSleepWakeLock.isHeld()) {
            this.mGoingToSleepWakeLock.release();
        }
        if (this.mService.mShuttingDown) {
            this.mService.mGlobalLock.notifyAll();
        }
    }

    boolean reportResumedActivityLocked(com.android.server.wm.ActivityRecord activityRecord) {
        this.mStoppingActivities.remove(activityRecord);
        if (activityRecord.getRootTask().getDisplayArea().allResumedActivitiesComplete()) {
            this.mRootWindowContainer.ensureActivitiesVisible();
            this.mRootWindowContainer.executeAppTransitionForAllDisplay();
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLaunchTaskBehindCompleteLocked(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.Task task = activityRecord.getTask();
        com.android.server.wm.Task rootTask = task.getRootTask();
        this.mRecentTasks.add(task);
        this.mService.getTaskChangeNotificationController().notifyTaskStackChanged();
        rootTask.ensureActivitiesVisible(null);
        com.android.server.wm.ActivityRecord topNonFinishingActivity = rootTask.getTopNonFinishingActivity();
        if (topNonFinishingActivity != null) {
            topNonFinishingActivity.getTask().touchActiveTime();
        }
    }

    void scheduleLaunchTaskBehindComplete(android.os.IBinder iBinder) {
        this.mHandler.obtainMessage(212, iBinder).sendToTarget();
    }

    void processStoppingAndFinishingActivities(com.android.server.wm.ActivityRecord activityRecord, boolean z, java.lang.String str) {
        java.util.ArrayList arrayList = null;
        int i = 0;
        boolean z2 = false;
        while (i < this.mStoppingActivities.size()) {
            com.android.server.wm.ActivityRecord activityRecord2 = this.mStoppingActivities.get(i);
            boolean z3 = (!activityRecord2.isInTransition() || activityRecord2.getTask() == null || activityRecord2.getTask().isForceHidden()) ? false : true;
            z2 |= activityRecord2.isDisplaySleepingAndSwapping();
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 1496536241884839051L, 60, null, java.lang.String.valueOf(activityRecord2), java.lang.Boolean.valueOf(activityRecord2.nowVisible), java.lang.Boolean.valueOf(z3), java.lang.String.valueOf(activityRecord2.finishing));
            if ((!z3 && !z2) || this.mService.mShuttingDown || activityRecord2.getRootTask().isForceHiddenForPinnedTask()) {
                if (!z && activityRecord2.isState(com.android.server.wm.ActivityRecord.State.PAUSING)) {
                    removeIdleTimeoutForActivity(activityRecord);
                    scheduleIdleTimeout(activityRecord);
                } else {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 5677125188685281770L, 0, null, java.lang.String.valueOf(activityRecord2));
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(activityRecord2);
                    this.mStoppingActivities.remove(i);
                    i--;
                }
            }
            i++;
        }
        if (z2) {
            this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.ActivityTaskSupervisor.this.lambda$processStoppingAndFinishingActivities$5();
                }
            }, 200L);
        }
        int size = arrayList == null ? 0 : arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.wm.ActivityRecord activityRecord3 = (com.android.server.wm.ActivityRecord) arrayList.get(i2);
            if (activityRecord3.isInHistory()) {
                if (activityRecord3.finishing) {
                    activityRecord3.destroyIfPossible(str);
                } else {
                    activityRecord3.stopIfPossible();
                }
            }
        }
        int size2 = this.mFinishingActivities.size();
        if (size2 == 0) {
            return;
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList(this.mFinishingActivities);
        this.mFinishingActivities.clear();
        for (int i3 = 0; i3 < size2; i3++) {
            com.android.server.wm.ActivityRecord activityRecord4 = (com.android.server.wm.ActivityRecord) arrayList2.get(i3);
            if (activityRecord4.isInHistory()) {
                activityRecord4.destroyImmediately("finish-" + str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processStoppingAndFinishingActivities$5() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                scheduleProcessStoppingAndFinishingActivitiesIfNeeded();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void removeHistoryRecords(com.android.server.wm.WindowProcessController windowProcessController) {
        removeHistoryRecords(this.mStoppingActivities, windowProcessController, "mStoppingActivities");
        removeHistoryRecords(this.mFinishingActivities, windowProcessController, "mFinishingActivities");
        removeHistoryRecords(this.mNoHistoryActivities, windowProcessController, "mNoHistoryActivities");
    }

    private void removeHistoryRecords(java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList, com.android.server.wm.WindowProcessController windowProcessController, java.lang.String str) {
        int size = arrayList.size();
        while (size > 0) {
            size--;
            com.android.server.wm.ActivityRecord activityRecord = arrayList.get(size);
            if (activityRecord.app == windowProcessController) {
                arrayList.remove(size);
                activityRecord.removeTimeouts();
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println();
        printWriter.println("ActivityTaskSupervisor state:");
        this.mRootWindowContainer.dump(printWriter, str, true);
        getKeyguardController().dump(printWriter, str);
        this.mService.getLockTaskController().dump(printWriter, str);
        printWriter.print(str);
        printWriter.println("mCurTaskIdForUser=" + this.mCurTaskIdForUser);
        printWriter.println(str + "mUserRootTaskInFront=" + this.mRootWindowContainer.mUserRootTaskInFront);
        printWriter.println(str + "mVisibilityTransactionDepth=" + this.mVisibilityTransactionDepth);
        printWriter.print(str);
        printWriter.print("isHomeRecentsComponent=");
        printWriter.println(this.mRecentTasks.isRecentsComponentHomeActivity(this.mRootWindowContainer.mCurrentUser));
        if (!this.mWaitingActivityLaunched.isEmpty()) {
            printWriter.println(str + "mWaitingActivityLaunched=");
            for (int size = this.mWaitingActivityLaunched.size() - 1; size >= 0; size += -1) {
                this.mWaitingActivityLaunched.get(size).dump(printWriter, str + "  ");
            }
        }
        printWriter.println(str + "mNoHistoryActivities=" + this.mNoHistoryActivities);
        printWriter.println();
    }

    static boolean printThisActivity(java.io.PrintWriter printWriter, com.android.server.wm.ActivityRecord activityRecord, java.lang.String str, boolean z, java.lang.String str2, java.lang.Runnable runnable) {
        return printThisActivity(printWriter, activityRecord, str, -1, z, str2, runnable);
    }

    static boolean printThisActivity(java.io.PrintWriter printWriter, com.android.server.wm.ActivityRecord activityRecord, java.lang.String str, int i, boolean z, java.lang.String str2, java.lang.Runnable runnable) {
        if (activityRecord != null) {
            if (i == -1 || i == activityRecord.getDisplayId()) {
                if (str == null || str.equals(activityRecord.packageName)) {
                    if (z) {
                        printWriter.println();
                    }
                    if (runnable != null) {
                        runnable.run();
                    }
                    printWriter.print(str2);
                    printWriter.println(activityRecord);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    static boolean dumpHistoryList(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.util.List<com.android.server.wm.ActivityRecord> list, java.lang.String str, java.lang.String str2, boolean z, boolean z2, boolean z3, java.lang.String str3, boolean z4, java.lang.Runnable runnable, com.android.server.wm.Task task) {
        int size = list.size() - 1;
        boolean z5 = z4;
        java.lang.Runnable runnable2 = runnable;
        com.android.server.wm.Task task2 = task;
        while (size >= 0) {
            com.android.server.wm.ActivityRecord activityRecord = list.get(size);
            com.android.server.wm.ActivityRecord.dumpActivity(fileDescriptor, printWriter, size, activityRecord, str, str2, z, z2, z3, str3, z5, runnable2, task2);
            task2 = activityRecord.getTask();
            z5 = z3 && activityRecord.attachedToProcess();
            size--;
            runnable2 = null;
        }
        return false;
    }

    void scheduleIdleTimeout(com.android.server.wm.ActivityRecord activityRecord) {
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(200, activityRecord), IDLE_TIMEOUT);
    }

    final void scheduleIdle() {
        if (!this.mHandler.hasMessages(201)) {
            this.mHandler.sendEmptyMessage(201);
        }
    }

    void updateTopResumedActivityIfNeeded(java.lang.String str) {
        com.android.server.wm.ActivityRecord activityRecord = this.mTopResumedActivity;
        com.android.server.wm.Task topDisplayFocusedRootTask = this.mRootWindowContainer.getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask == null || topDisplayFocusedRootTask.getTopResumedActivity() == activityRecord) {
            if (topDisplayFocusedRootTask == null) {
                scheduleTopResumedActivityStateLossIfNeeded();
            }
            if (this.mService.isSleepingLocked()) {
                this.mService.updateTopApp(null);
                return;
            }
            return;
        }
        scheduleTopResumedActivityStateLossIfNeeded();
        this.mTopResumedActivity = topDisplayFocusedRootTask.getTopResumedActivity();
        if (this.mTopResumedActivity != null && activityRecord != null) {
            if (this.mTopResumedActivity.app != null) {
                this.mTopResumedActivity.app.addToPendingTop();
            }
            this.mService.updateOomAdj();
        }
        if (this.mTopResumedActivity != null) {
            this.mService.setLastResumedActivityUncheckLocked(this.mTopResumedActivity, str);
        }
        scheduleTopResumedActivityStateIfNeeded();
        this.mService.updateTopApp(this.mTopResumedActivity);
    }

    private void scheduleTopResumedActivityStateLossIfNeeded() {
        if (this.mTopResumedActivity != null && !this.mTopResumedActivityWaitingForPrev && this.mTopResumedActivity.scheduleTopResumedActivityChanged(false)) {
            scheduleTopResumedStateLossTimeout(this.mTopResumedActivity);
            this.mTopResumedActivityWaitingForPrev = true;
            this.mTopResumedActivity = null;
        }
    }

    private void scheduleTopResumedActivityStateIfNeeded() {
        if (this.mTopResumedActivity != null && !this.mTopResumedActivityWaitingForPrev) {
            this.mTopResumedActivity.scheduleTopResumedActivityChanged(true);
        }
    }

    private void scheduleTopResumedStateLossTimeout(com.android.server.wm.ActivityRecord activityRecord) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(217);
        obtainMessage.obj = activityRecord;
        activityRecord.topResumedStateLossTime = android.os.SystemClock.uptimeMillis();
        this.mHandler.sendMessageDelayed(obtainMessage, 500L);
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 3604633008357193496L, 0, null, java.lang.String.valueOf(activityRecord));
    }

    void handleTopResumedStateReleased(boolean z) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STATES, 3997062844427155487L, 0, null, java.lang.String.valueOf(z ? "(due to timeout)" : "(transition complete)"));
        this.mHandler.removeMessages(217);
        if (!this.mTopResumedActivityWaitingForPrev) {
            return;
        }
        this.mTopResumedActivityWaitingForPrev = false;
        scheduleTopResumedActivityStateIfNeeded();
    }

    void removeIdleTimeoutForActivity(com.android.server.wm.ActivityRecord activityRecord) {
        this.mHandler.removeMessages(200, activityRecord);
    }

    final void scheduleResumeTopActivities() {
        if (!this.mHandler.hasMessages(202)) {
            this.mHandler.sendEmptyMessage(202);
        }
    }

    void scheduleProcessStoppingAndFinishingActivitiesIfNeeded() {
        if (this.mStoppingActivities.isEmpty() && this.mFinishingActivities.isEmpty()) {
            return;
        }
        if (this.mRootWindowContainer.allResumedActivitiesIdle()) {
            scheduleIdle();
        } else if (!this.mHandler.hasMessages(205) && this.mRootWindowContainer.allResumedActivitiesVisible()) {
            this.mHandler.sendEmptyMessage(205);
        }
    }

    void removeSleepTimeouts() {
        this.mHandler.removeMessages(203);
    }

    final void scheduleSleepTimeout() {
        removeSleepTimeouts();
        this.mHandler.sendEmptyMessageDelayed(203, SLEEP_TIMEOUT);
    }

    boolean hasScheduledRestartTimeouts(com.android.server.wm.ActivityRecord activityRecord) {
        return this.mHandler.hasMessages(213, activityRecord);
    }

    void removeRestartTimeouts(com.android.server.wm.ActivityRecord activityRecord) {
        this.mHandler.removeMessages(213, activityRecord);
    }

    final void scheduleRestartTimeout(com.android.server.wm.ActivityRecord activityRecord) {
        removeRestartTimeouts(activityRecord);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(213, activityRecord), 2000L);
    }

    void handleNonResizableTaskIfNeeded(com.android.server.wm.Task task, int i, com.android.server.wm.TaskDisplayArea taskDisplayArea, com.android.server.wm.Task task2) {
        handleNonResizableTaskIfNeeded(task, i, taskDisplayArea, task2, false);
    }

    void handleNonResizableTaskIfNeeded(com.android.server.wm.Task task, int i, com.android.server.wm.TaskDisplayArea taskDisplayArea, com.android.server.wm.Task task2, boolean z) {
        boolean z2 = (taskDisplayArea == null || taskDisplayArea.getDisplayId() == 0) ? false : true;
        if (!task.isActivityTypeStandardOrUndefined()) {
            return;
        }
        if (z2) {
            if (!task.canBeLaunchedOnDisplay(task.getDisplayId())) {
                throw new java.lang.IllegalStateException("Task resolved to incompatible display");
            }
            com.android.server.wm.DisplayContent displayContent = taskDisplayArea.mDisplayContent;
            if (displayContent != task.getDisplayContent()) {
                android.util.Slog.w("ActivityTaskManager", "Failed to put " + task + " on display " + displayContent.mDisplayId);
                this.mService.getTaskChangeNotificationController().notifyActivityLaunchOnSecondaryDisplayFailed(task.getTaskInfo(), displayContent.mDisplayId);
                return;
            }
            if (!z) {
                handleForcedResizableTaskIfNeeded(task, 2);
                return;
            }
            return;
        }
        if (!z) {
            handleForcedResizableTaskIfNeeded(task, 1);
        }
    }

    private void handleForcedResizableTaskIfNeeded(com.android.server.wm.Task task, int i) {
        com.android.server.wm.ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity();
        if (topNonFinishingActivity == null || topNonFinishingActivity.noDisplay || !topNonFinishingActivity.canForceResizeNonResizable(task.getWindowingMode())) {
            return;
        }
        this.mService.getTaskChangeNotificationController().notifyActivityForcedResizable(task.mTaskId, i, topNonFinishingActivity.info.applicationInfo.packageName);
    }

    void scheduleUpdateMultiWindowMode(com.android.server.wm.Task task) {
        task.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.ActivityTaskSupervisor.this.lambda$scheduleUpdateMultiWindowMode$6((com.android.server.wm.ActivityRecord) obj);
            }
        });
        if (!this.mHandler.hasMessages(214)) {
            this.mHandler.sendEmptyMessage(214);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleUpdateMultiWindowMode$6(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.attachedToProcess()) {
            this.mMultiWindowModeChangedActivities.add(activityRecord);
        }
    }

    void scheduleUpdatePictureInPictureModeIfNeeded(com.android.server.wm.Task task, com.android.server.wm.Task task2) {
        com.android.server.wm.Task rootTask = task.getRootTask();
        if (task2 != null) {
            if (task2 != rootTask && !task2.inPinnedWindowingMode() && !rootTask.inPinnedWindowingMode()) {
                return;
            }
            scheduleUpdatePictureInPictureModeIfNeeded(task, rootTask.getRequestedOverrideBounds());
        }
    }

    void scheduleUpdatePictureInPictureModeIfNeeded(com.android.server.wm.Task task, android.graphics.Rect rect) {
        task.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivityTaskSupervisor$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.ActivityTaskSupervisor.this.lambda$scheduleUpdatePictureInPictureModeIfNeeded$7((com.android.server.wm.ActivityRecord) obj);
            }
        });
        this.mPipModeChangedTargetRootTaskBounds = rect;
        if (!this.mHandler.hasMessages(215)) {
            this.mHandler.sendEmptyMessage(215);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleUpdatePictureInPictureModeIfNeeded$7(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.attachedToProcess()) {
            this.mPipModeChangedActivities.add(activityRecord);
            this.mMultiWindowModeChangedActivities.remove(activityRecord);
        }
    }

    void wakeUp(java.lang.String str) {
        this.mPowerManager.wakeUp(android.os.SystemClock.uptimeMillis(), 2, "android.server.am:TURN_ON:" + str);
    }

    void beginActivityVisibilityUpdate() {
        if (this.mVisibilityTransactionDepth == 0) {
            getKeyguardController().updateVisibility();
        }
        this.mVisibilityTransactionDepth++;
    }

    void endActivityVisibilityUpdate() {
        this.mVisibilityTransactionDepth--;
        if (this.mVisibilityTransactionDepth == 0) {
            computeProcessActivityStateBatch();
        }
    }

    boolean inActivityVisibilityUpdate() {
        return this.mVisibilityTransactionDepth > 0;
    }

    void setDeferRootVisibilityUpdate(boolean z) {
        this.mDeferRootVisibilityUpdate = z;
    }

    boolean isRootVisibilityUpdateDeferred() {
        return this.mDeferRootVisibilityUpdate;
    }

    void onProcessActivityStateChanged(com.android.server.wm.WindowProcessController windowProcessController, boolean z) {
        if (z || inActivityVisibilityUpdate()) {
            if (!this.mActivityStateChangedProcs.contains(windowProcessController)) {
                this.mActivityStateChangedProcs.add(windowProcessController);
                return;
            }
            return;
        }
        windowProcessController.computeProcessActivityState();
    }

    void computeProcessActivityStateBatch() {
        if (this.mActivityStateChangedProcs.isEmpty()) {
            return;
        }
        for (int size = this.mActivityStateChangedProcs.size() - 1; size >= 0; size--) {
            this.mActivityStateChangedProcs.get(size).computeProcessActivityState();
        }
        this.mActivityStateChangedProcs.clear();
    }

    void beginDeferResume() {
        this.mDeferResumeCount++;
    }

    void endDeferResume() {
        this.mDeferResumeCount--;
    }

    boolean readyToResume() {
        return this.mDeferResumeCount == 0;
    }

    private final class ActivityTaskSupervisorHandler extends android.os.Handler {
        ActivityTaskSupervisorHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            java.lang.String str;
            int i;
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.ActivityTaskSupervisor.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (handleMessageInner(message)) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    switch (message.what) {
                        case 213:
                            com.android.server.wm.ActivityRecord activityRecord = (com.android.server.wm.ActivityRecord) message.obj;
                            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = com.android.server.wm.ActivityTaskSupervisor.this.mService.mGlobalLock;
                            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock2) {
                                try {
                                    if (activityRecord.attachedToProcess() && activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESTARTING_PROCESS)) {
                                        str = activityRecord.app.mName;
                                        i = activityRecord.app.mUid;
                                    } else {
                                        str = null;
                                        i = 0;
                                    }
                                } catch (java.lang.Throwable th) {
                                    throw th;
                                }
                            }
                            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            if (str != null) {
                                com.android.server.wm.ActivityTaskSupervisor.this.mService.mAmInternal.killProcess(str, i, "restartActivityProcessTimeout");
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                } finally {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
        }

        private void activityIdleFromMessage(com.android.server.wm.ActivityRecord activityRecord, boolean z) {
            com.android.server.wm.ActivityTaskSupervisor.this.activityIdleInternal(activityRecord, z, z, null);
        }

        private boolean handleMessageInner(android.os.Message message) {
            switch (message.what) {
                case 200:
                    activityIdleFromMessage((com.android.server.wm.ActivityRecord) message.obj, true);
                    return true;
                case 201:
                    activityIdleFromMessage((com.android.server.wm.ActivityRecord) message.obj, false);
                    return true;
                case 202:
                    com.android.server.wm.ActivityTaskSupervisor.this.mRootWindowContainer.resumeFocusedTasksTopActivities();
                    return true;
                case 203:
                    if (com.android.server.wm.ActivityTaskSupervisor.this.mService.isSleepingOrShuttingDownLocked()) {
                        android.util.Slog.w("ActivityTaskManager", "Sleep timeout!  Sleeping now.");
                        com.android.server.wm.ActivityTaskSupervisor.this.checkReadyForSleepLocked(false);
                    }
                    return true;
                case 204:
                    if (com.android.server.wm.ActivityTaskSupervisor.this.mLaunchingActivityWakeLock.isHeld()) {
                        android.util.Slog.w("ActivityTaskManager", "Launch timeout has expired, giving up wake lock!");
                        com.android.server.wm.ActivityTaskSupervisor.this.mLaunchingActivityWakeLock.release();
                    }
                    return true;
                case 205:
                    com.android.server.wm.ActivityTaskSupervisor.this.processStoppingAndFinishingActivities(null, false, "transit");
                    return true;
                case 206:
                    com.android.server.wm.Task task = (com.android.server.wm.Task) message.obj;
                    if (task.mKillProcessesOnDestroyed && task.hasActivity()) {
                        android.util.Slog.i("ActivityTaskManager", "Destroy timeout of remove-task, attempt to kill " + task);
                        com.android.server.wm.ActivityTaskSupervisor.this.killTaskProcessesIfPossible(task);
                    }
                    return true;
                case 207:
                case 208:
                case 209:
                case 210:
                case com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__ROLE_HOLDER_UPDATER_UPDATE_RETRY /* 211 */:
                case 213:
                default:
                    return false;
                case 212:
                    com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked((android.os.IBinder) message.obj);
                    if (forTokenLocked != null) {
                        com.android.server.wm.ActivityTaskSupervisor.this.handleLaunchTaskBehindCompleteLocked(forTokenLocked);
                    }
                    return true;
                case 214:
                    for (int size = com.android.server.wm.ActivityTaskSupervisor.this.mMultiWindowModeChangedActivities.size() - 1; size >= 0; size--) {
                        ((com.android.server.wm.ActivityRecord) com.android.server.wm.ActivityTaskSupervisor.this.mMultiWindowModeChangedActivities.remove(size)).updateMultiWindowMode();
                    }
                    return true;
                case 215:
                    for (int size2 = com.android.server.wm.ActivityTaskSupervisor.this.mPipModeChangedActivities.size() - 1; size2 >= 0; size2--) {
                        ((com.android.server.wm.ActivityRecord) com.android.server.wm.ActivityTaskSupervisor.this.mPipModeChangedActivities.remove(size2)).updatePictureInPictureMode(com.android.server.wm.ActivityTaskSupervisor.this.mPipModeChangedTargetRootTaskBounds, false);
                    }
                    return true;
                case 216:
                    com.android.server.wm.ActivityTaskSupervisor.this.mHandler.removeMessages(216);
                    com.android.server.wm.ActivityTaskSupervisor.this.mRootWindowContainer.startHomeOnEmptyDisplays((java.lang.String) message.obj);
                    return true;
                case 217:
                    com.android.server.wm.ActivityRecord activityRecord = (com.android.server.wm.ActivityRecord) message.obj;
                    android.util.Slog.w("ActivityTaskManager", "Activity top resumed state loss timeout for " + activityRecord);
                    if (activityRecord.hasProcess()) {
                        com.android.server.wm.ActivityTaskSupervisor.this.mService.logAppTooSlow(activityRecord.app, activityRecord.topResumedStateLossTime, "top state loss for " + activityRecord);
                    }
                    com.android.server.wm.ActivityTaskSupervisor.this.handleTopResumedStateReleased(true);
                    return true;
            }
        }
    }

    int startActivityFromRecents(int i, int i2, int i3, com.android.server.wm.SafeActivityOptions safeActivityOptions) {
        boolean z;
        int i4;
        int i5 = i2;
        android.app.ActivityOptions options = safeActivityOptions != null ? safeActivityOptions.getOptions(this) : null;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                boolean isCallerRecents = this.mRecentTasks.isCallerRecents(i5);
                if (options != null) {
                    i4 = options.getLaunchActivityType();
                    if (options.freezeRecentTasksReordering()) {
                        if (!isCallerRecents) {
                            if (com.android.server.wm.ActivityTaskManagerService.checkPermission("android.permission.MANAGE_ACTIVITY_TASKS", i, i5) == 0) {
                            }
                        }
                        this.mRecentTasks.setFreezeTaskListReordering();
                    }
                    z = options.getLaunchRootTask() == null;
                } else {
                    z = true;
                    i4 = 0;
                }
                if (i4 == 2 || i4 == 3) {
                    throw new java.lang.IllegalArgumentException("startActivityFromRecents: Task " + i3 + " can't be launch in the home/recents root task.");
                }
                this.mService.deferWindowLayout();
                try {
                    com.android.server.wm.Task anyTaskForId = this.mRootWindowContainer.anyTaskForId(i3, 2, options, true);
                    if (anyTaskForId == null) {
                        this.mWindowManager.executeAppTransition();
                        throw new java.lang.IllegalArgumentException("startActivityFromRecents: Task " + i3 + " not found.");
                    }
                    if (z) {
                        this.mRootWindowContainer.getDefaultTaskDisplayArea().moveHomeRootTaskToFront("startActivityFromRecents");
                    }
                    if (!this.mService.mAmInternal.shouldConfirmCredentials(anyTaskForId.mUserId) && anyTaskForId.getRootActivity() != null) {
                        com.android.server.wm.ActivityRecord topNonFinishingActivity = anyTaskForId.getTopNonFinishingActivity();
                        this.mRootWindowContainer.startPowerModeLaunchIfNeeded(true, topNonFinishingActivity);
                        com.android.server.wm.ActivityMetricsLogger activityMetricsLogger = this.mActivityMetricsLogger;
                        android.content.Intent intent = anyTaskForId.intent;
                        if (isCallerRecents) {
                            i5 = -1;
                        }
                        com.android.server.wm.ActivityMetricsLogger.LaunchingState notifyActivityLaunching = activityMetricsLogger.notifyActivityLaunching(intent, null, i5);
                        try {
                            this.mService.moveTaskToFrontLocked(null, null, anyTaskForId.mTaskId, 0, safeActivityOptions);
                            if (options != null && options.getAnimationType() == 13) {
                                topNonFinishingActivity.mPendingRemoteAnimation = options.getRemoteAnimationAdapter();
                            }
                            topNonFinishingActivity.applyOptionsAnimation();
                            if (options != null && options.getLaunchCookie() != null) {
                                topNonFinishingActivity.mLaunchCookie = options.getLaunchCookie();
                            }
                            this.mActivityMetricsLogger.notifyActivityLaunched(notifyActivityLaunching, 2, false, topNonFinishingActivity, options);
                            this.mService.getActivityStartController().postStartActivityProcessingForLastStarter(anyTaskForId.getTopNonFinishingActivity(), 2, anyTaskForId.getRootTask());
                            this.mService.resumeAppSwitches();
                            this.mService.continueWindowLayout();
                            return 2;
                        } catch (java.lang.Throwable th) {
                            this.mActivityMetricsLogger.notifyActivityLaunched(notifyActivityLaunching, 2, false, topNonFinishingActivity, options);
                            throw th;
                        }
                    }
                    int i6 = anyTaskForId.mCallingUid;
                    java.lang.String str = anyTaskForId.mCallingPackage;
                    java.lang.String str2 = anyTaskForId.mCallingFeatureId;
                    android.content.Intent intent2 = anyTaskForId.intent;
                    intent2.addFlags(1048576);
                    int i7 = anyTaskForId.mUserId;
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    try {
                        com.android.server.pm.PackageManagerServiceUtils.DISABLE_ENFORCE_INTENTS_TO_MATCH_INTENT_FILTERS.set(true);
                        int startActivityInPackage = this.mService.getActivityStartController().startActivityInPackage(i6, i, i2, str, str2, intent2, null, null, null, 0, 0, safeActivityOptions, i7, anyTaskForId, "startActivityFromRecents", false, null, android.app.BackgroundStartPrivileges.NONE);
                        com.android.server.pm.PackageManagerServiceUtils.DISABLE_ENFORCE_INTENTS_TO_MATCH_INTENT_FILTERS.set(false);
                        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
                        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock2) {
                            try {
                                this.mService.continueWindowLayout();
                            } finally {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return startActivityInPackage;
                    } catch (java.lang.Throwable th2) {
                        com.android.server.pm.PackageManagerServiceUtils.DISABLE_ENFORCE_INTENTS_TO_MATCH_INTENT_FILTERS.set(false);
                        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock3 = this.mService.mGlobalLock;
                        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock3) {
                            try {
                                this.mService.continueWindowLayout();
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                throw th2;
                            } finally {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                    }
                } catch (java.lang.Throwable th3) {
                    this.mService.continueWindowLayout();
                    throw th3;
                }
            } catch (java.lang.Throwable th4) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th4;
            }
        }
    }

    static class OpaqueActivityHelper implements java.util.function.Predicate<com.android.server.wm.ActivityRecord> {
        private boolean mIgnoringKeyguard;
        private boolean mIncludeInvisibleAndFinishing;
        private com.android.server.wm.ActivityRecord mStarting;

        OpaqueActivityHelper() {
        }

        com.android.server.wm.ActivityRecord getOpaqueActivity(@android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer, boolean z) {
            this.mIncludeInvisibleAndFinishing = true;
            this.mIgnoringKeyguard = z;
            return windowContainer.getActivity(this, true, null);
        }

        com.android.server.wm.ActivityRecord getVisibleOpaqueActivity(@android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, boolean z) {
            this.mStarting = activityRecord;
            this.mIncludeInvisibleAndFinishing = false;
            this.mIgnoringKeyguard = z;
            com.android.server.wm.ActivityRecord activity = windowContainer.getActivity(this, true, null);
            this.mStarting = null;
            return activity;
        }

        @Override // java.util.function.Predicate
        public boolean test(com.android.server.wm.ActivityRecord activityRecord) {
            if (!this.mIncludeInvisibleAndFinishing && activityRecord != this.mStarting) {
                if (!this.mIgnoringKeyguard || activityRecord.visibleIgnoringKeyguard) {
                    if (!this.mIgnoringKeyguard && !activityRecord.isVisible()) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return activityRecord.occludesParent(this.mIncludeInvisibleAndFinishing);
        }
    }

    static class TaskInfoHelper implements java.util.function.Consumer<com.android.server.wm.ActivityRecord> {
        private android.app.TaskInfo mInfo;
        private com.android.server.wm.ActivityRecord mTopRunning;

        TaskInfoHelper() {
        }

        com.android.server.wm.ActivityRecord fillAndReturnTop(com.android.server.wm.Task task, android.app.TaskInfo taskInfo) {
            taskInfo.numActivities = 0;
            taskInfo.baseActivity = null;
            this.mInfo = taskInfo;
            task.forAllActivities(this);
            com.android.server.wm.ActivityRecord activityRecord = this.mTopRunning;
            this.mTopRunning = null;
            this.mInfo = null;
            return activityRecord;
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.wm.ActivityRecord activityRecord) {
            if (activityRecord.mLaunchCookie != null) {
                this.mInfo.addLaunchCookie(activityRecord.mLaunchCookie);
            }
            if (activityRecord.finishing) {
                return;
            }
            this.mInfo.numActivities++;
            this.mInfo.baseActivity = activityRecord.mActivityComponent;
            if (this.mTopRunning == null) {
                this.mTopRunning = activityRecord;
            }
        }
    }

    private static class WaitInfo {
        final com.android.server.wm.ActivityMetricsLogger.LaunchingState mLaunchingState;
        final android.app.WaitResult mResult;
        final android.content.ComponentName mTargetComponent;

        WaitInfo(android.app.WaitResult waitResult, android.content.ComponentName componentName, com.android.server.wm.ActivityMetricsLogger.LaunchingState launchingState) {
            this.mResult = waitResult;
            this.mTargetComponent = componentName;
            this.mLaunchingState = launchingState;
        }

        boolean matches(com.android.server.wm.ActivityRecord activityRecord) {
            if (!this.mLaunchingState.hasActiveTransitionInfo()) {
                return this.mTargetComponent.equals(activityRecord.mActivityComponent);
            }
            return this.mLaunchingState.contains(activityRecord);
        }

        void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.println(str + "WaitInfo:");
            printWriter.println(str + "  mTargetComponent=" + this.mTargetComponent);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(str);
            sb.append("  mResult=");
            printWriter.println(sb.toString());
            this.mResult.dump(printWriter, str + "    ");
        }
    }
}
