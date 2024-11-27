package com.android.server.wm;

/* loaded from: classes3.dex */
class RecentsAnimation implements com.android.server.wm.RecentsAnimationController.RecentsAnimationCallbacks, com.android.server.wm.TaskDisplayArea.OnRootTaskOrderChangedListener {
    private static final java.lang.String TAG = com.android.server.wm.RecentsAnimation.class.getSimpleName();
    private final com.android.server.wm.ActivityStartController mActivityStartController;

    @android.annotation.Nullable
    private final com.android.server.wm.WindowProcessController mCaller;
    private final com.android.server.wm.TaskDisplayArea mDefaultTaskDisplayArea;
    private com.android.server.wm.ActivityRecord mLaunchedTargetActivity;
    private final android.content.ComponentName mRecentsComponent;

    @android.annotation.Nullable
    private final java.lang.String mRecentsFeatureId;
    private final int mRecentsUid;
    private com.android.server.wm.Task mRestoreTargetBehindRootTask;
    private final com.android.server.wm.ActivityTaskManagerService mService;
    private final int mTargetActivityType;
    private final android.content.Intent mTargetIntent;
    private final com.android.server.wm.ActivityTaskSupervisor mTaskSupervisor;
    private final int mUserId;
    private final com.android.server.wm.WindowManagerService mWindowManager;

    RecentsAnimation(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor, com.android.server.wm.ActivityStartController activityStartController, com.android.server.wm.WindowManagerService windowManagerService, android.content.Intent intent, android.content.ComponentName componentName, @android.annotation.Nullable java.lang.String str, int i, @android.annotation.Nullable com.android.server.wm.WindowProcessController windowProcessController) {
        int i2;
        this.mService = activityTaskManagerService;
        this.mTaskSupervisor = activityTaskSupervisor;
        this.mDefaultTaskDisplayArea = this.mService.mRootWindowContainer.getDefaultTaskDisplayArea();
        this.mActivityStartController = activityStartController;
        this.mWindowManager = windowManagerService;
        this.mTargetIntent = intent;
        this.mRecentsComponent = componentName;
        this.mRecentsFeatureId = str;
        this.mRecentsUid = i;
        this.mCaller = windowProcessController;
        this.mUserId = activityTaskManagerService.getCurrentUserId();
        if (intent.getComponent() != null && componentName.equals(intent.getComponent())) {
            i2 = 3;
        } else {
            i2 = 2;
        }
        this.mTargetActivityType = i2;
    }

    void preloadRecentsActivity() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -3758280623533049031L, 0, null, java.lang.String.valueOf(this.mTargetIntent));
        com.android.server.wm.ActivityRecord targetActivity = getTargetActivity(this.mDefaultTaskDisplayArea.getRootTask(0, this.mTargetActivityType));
        if (targetActivity != null) {
            if (targetActivity.isVisibleRequested() || targetActivity.isTopRunningActivity()) {
                return;
            }
            if (targetActivity.attachedToProcess()) {
                if (targetActivity.app.getCurrentProcState() >= 16) {
                    android.util.Slog.v(TAG, "Skip preload recents for cached proc " + targetActivity.app);
                    return;
                }
                targetActivity.ensureActivityConfiguration(true);
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -3365656764099317101L, 0, null, java.lang.String.valueOf(targetActivity.getConfiguration()));
            }
        } else {
            if (this.mDefaultTaskDisplayArea.getActivity(new com.android.server.wm.LetterboxUiController$$ExternalSyntheticLambda11(), false) == null) {
                return;
            }
            startRecentsActivityInBackground("preloadRecents");
            targetActivity = getTargetActivity(this.mDefaultTaskDisplayArea.getRootTask(0, this.mTargetActivityType));
            if (targetActivity == null) {
                android.util.Slog.w(TAG, "Cannot start " + this.mTargetIntent);
                return;
            }
        }
        if (!targetActivity.attachedToProcess()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -7165162073742035900L, 0, null, null);
            this.mTaskSupervisor.startSpecificActivity(targetActivity, false, false);
            if (targetActivity.getDisplayContent() != null) {
                targetActivity.getDisplayContent().mUnknownAppVisibilityController.appRemovedOrHidden(targetActivity);
            }
        }
        if (!targetActivity.finishing && targetActivity.isAttached() && !targetActivity.isState(com.android.server.wm.ActivityRecord.State.STOPPING, com.android.server.wm.ActivityRecord.State.STOPPED)) {
            targetActivity.addToStopping(true, true, "preloadRecents");
        }
    }

    void startRecentsActivity(android.view.IRecentsAnimationRunner iRecentsAnimationRunner, long j) {
        com.android.server.wm.ActivityRecord activityRecord;
        android.app.ActivityOptions activityOptions;
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -3403665718306852375L, 0, null, java.lang.String.valueOf(this.mTargetIntent));
        android.os.Trace.traceBegin(32L, "RecentsAnimation#startRecentsActivity");
        if (this.mWindowManager.getRecentsAnimationController() != null) {
            this.mWindowManager.getRecentsAnimationController().forceCancelAnimation(2, "startRecentsActivity");
        }
        com.android.server.wm.Task rootTask = this.mDefaultTaskDisplayArea.getRootTask(0, this.mTargetActivityType);
        com.android.server.wm.ActivityRecord targetActivity = getTargetActivity(rootTask);
        boolean z = targetActivity != null;
        if (z) {
            this.mRestoreTargetBehindRootTask = com.android.server.wm.TaskDisplayArea.getRootTaskAbove(rootTask);
            if (this.mRestoreTargetBehindRootTask == null && rootTask.getTopMostTask() == targetActivity.getTask()) {
                notifyAnimationCancelBeforeStart(iRecentsAnimationRunner);
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -8325607672707336373L, 0, null, java.lang.String.valueOf(rootTask));
                return;
            }
        }
        if (targetActivity == null || !targetActivity.isVisibleRequested()) {
            this.mService.mRootWindowContainer.startPowerModeLaunchIfNeeded(true, targetActivity);
        }
        com.android.server.wm.ActivityMetricsLogger.LaunchingState notifyActivityLaunching = this.mTaskSupervisor.getActivityMetricsLogger().notifyActivityLaunching(this.mTargetIntent);
        setProcessAnimating(true);
        this.mService.deferWindowLayout();
        try {
            try {
                if (!z) {
                    startRecentsActivityInBackground("startRecentsActivity_noTargetActivity");
                    com.android.server.wm.Task rootTask2 = this.mDefaultTaskDisplayArea.getRootTask(0, this.mTargetActivityType);
                    com.android.server.wm.ActivityRecord targetActivity2 = getTargetActivity(rootTask2);
                    this.mDefaultTaskDisplayArea.moveRootTaskBehindBottomMostVisibleRootTask(rootTask2);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -7278356485797757819L, 0, null, java.lang.String.valueOf(rootTask2), java.lang.String.valueOf(com.android.server.wm.TaskDisplayArea.getRootTaskAbove(rootTask2)));
                    this.mWindowManager.prepareAppTransitionNone();
                    this.mWindowManager.executeAppTransition();
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 1012359606301505741L, 0, null, java.lang.String.valueOf(this.mTargetIntent));
                    activityRecord = targetActivity2;
                } else {
                    this.mDefaultTaskDisplayArea.moveRootTaskBehindBottomMostVisibleRootTask(rootTask);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -7278356485797757819L, 0, null, java.lang.String.valueOf(rootTask), java.lang.String.valueOf(com.android.server.wm.TaskDisplayArea.getRootTaskAbove(rootTask)));
                    com.android.server.wm.Task task = targetActivity.getTask();
                    if (rootTask.getTopMostTask() != task) {
                        rootTask.positionChildAtTop(task);
                    }
                    activityRecord = targetActivity;
                }
                activityRecord.mLaunchTaskBehind = true;
                this.mLaunchedTargetActivity = activityRecord;
                activityRecord.intent.replaceExtras(this.mTargetIntent);
                this.mWindowManager.initializeRecentsAnimation(this.mTargetActivityType, iRecentsAnimationRunner, this, this.mDefaultTaskDisplayArea.getDisplayId(), this.mTaskSupervisor.mRecentTasks.getRecentTaskIds(), activityRecord);
                this.mService.mRootWindowContainer.ensureActivitiesVisible();
                if (j <= 0) {
                    activityOptions = null;
                } else {
                    android.app.ActivityOptions makeBasic = android.app.ActivityOptions.makeBasic();
                    makeBasic.setSourceInfo(4, j);
                    activityOptions = makeBasic;
                }
                this.mTaskSupervisor.getActivityMetricsLogger().notifyActivityLaunched(notifyActivityLaunching, 2, !z, activityRecord, activityOptions);
                this.mDefaultTaskDisplayArea.registerRootTaskOrderChangedListener(this);
                this.mService.continueWindowLayout();
                android.os.Trace.traceEnd(32L);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Failed to start recents activity", e);
                throw e;
            }
        } catch (java.lang.Throwable th) {
            this.mService.continueWindowLayout();
            android.os.Trace.traceEnd(32L);
            throw th;
        }
    }

    private void finishAnimation(@com.android.server.wm.RecentsAnimationController.ReorderMode final int i, final boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 5474198007669537235L, 4, null, java.lang.String.valueOf(this.mWindowManager.getRecentsAnimationController()), java.lang.Long.valueOf(i));
                this.mDefaultTaskDisplayArea.unregisterRootTaskOrderChangedListener(this);
                final com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mWindowManager.getRecentsAnimationController();
                if (recentsAnimationController == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (i != 0) {
                    this.mService.endPowerMode(1);
                }
                if (i == 1) {
                    this.mService.stopAppSwitches();
                }
                inSurfaceTransaction(new java.lang.Runnable() { // from class: com.android.server.wm.RecentsAnimation$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.RecentsAnimation.this.lambda$finishAnimation$0(i, z, recentsAnimationController);
                    }
                });
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishAnimation$0(int i, boolean z, com.android.server.wm.RecentsAnimationController recentsAnimationController) {
        android.os.Trace.traceBegin(32L, "RecentsAnimation#onAnimationFinished_inSurfaceTransaction");
        this.mService.deferWindowLayout();
        try {
            try {
                this.mWindowManager.cleanupRecentsAnimation(i);
                com.android.server.wm.Task rootTask = this.mDefaultTaskDisplayArea.getRootTask(0, this.mTargetActivityType);
                com.android.server.wm.ActivityRecord isInTask = rootTask != null ? rootTask.isInTask(this.mLaunchedTargetActivity) : null;
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 3525834288436624965L, 0, null, java.lang.String.valueOf(rootTask), java.lang.String.valueOf(isInTask), java.lang.String.valueOf(this.mRestoreTargetBehindRootTask));
                if (isInTask == null) {
                    this.mTaskSupervisor.mUserLeaving = false;
                    this.mService.continueWindowLayout();
                    if (this.mWindowManager.mRoot.isLayoutNeeded()) {
                        this.mWindowManager.mRoot.performSurfacePlacement();
                    }
                    setProcessAnimating(false);
                    android.os.Trace.traceEnd(32L);
                    return;
                }
                isInTask.mLaunchTaskBehind = false;
                if (i == 1) {
                    this.mTaskSupervisor.mNoAnimActivities.add(isInTask);
                    if (z) {
                        this.mTaskSupervisor.mUserLeaving = true;
                        rootTask.moveTaskToFront(isInTask.getTask(), true, null, isInTask.appTimeTracker, "RecentsAnimation.onAnimationFinished()");
                    } else {
                        rootTask.moveToFront("RecentsAnimation.onAnimationFinished()");
                    }
                    if (com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS.isLogToAny()) {
                        com.android.server.wm.Task topNonAlwaysOnTopRootTask = getTopNonAlwaysOnTopRootTask();
                        if (topNonAlwaysOnTopRootTask != rootTask) {
                            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -5961176083217302671L, 0, null, java.lang.String.valueOf(rootTask), java.lang.String.valueOf(topNonAlwaysOnTopRootTask));
                        }
                    }
                } else {
                    if (i != 2) {
                        if (!recentsAnimationController.shouldDeferCancelWithScreenshot() && !rootTask.isFocusedRootTaskOnDisplay()) {
                            rootTask.ensureActivitiesVisible(null);
                        }
                        this.mTaskSupervisor.mUserLeaving = false;
                        this.mService.continueWindowLayout();
                        if (this.mWindowManager.mRoot.isLayoutNeeded()) {
                            this.mWindowManager.mRoot.performSurfacePlacement();
                        }
                        setProcessAnimating(false);
                        android.os.Trace.traceEnd(32L);
                        return;
                    }
                    isInTask.getDisplayArea().moveRootTaskBehindRootTask(rootTask, this.mRestoreTargetBehindRootTask);
                    if (com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS.isLogToAny()) {
                        com.android.server.wm.Task rootTaskAbove = com.android.server.wm.TaskDisplayArea.getRootTaskAbove(rootTask);
                        if (this.mRestoreTargetBehindRootTask != null && rootTaskAbove != this.mRestoreTargetBehindRootTask) {
                            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -5893976429537642045L, 0, null, java.lang.String.valueOf(rootTask), java.lang.String.valueOf(this.mRestoreTargetBehindRootTask), java.lang.String.valueOf(rootTaskAbove));
                        }
                    }
                }
                this.mWindowManager.prepareAppTransitionNone();
                this.mService.mRootWindowContainer.ensureActivitiesVisible();
                this.mService.mRootWindowContainer.resumeFocusedTasksTopActivities();
                this.mWindowManager.executeAppTransition();
                rootTask.getRootTask().dispatchTaskInfoChangedIfNeeded(true);
                this.mTaskSupervisor.mUserLeaving = false;
                this.mService.continueWindowLayout();
                if (this.mWindowManager.mRoot.isLayoutNeeded()) {
                    this.mWindowManager.mRoot.performSurfacePlacement();
                }
                setProcessAnimating(false);
                android.os.Trace.traceEnd(32L);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Failed to clean up recents activity", e);
                throw e;
            }
        } catch (java.lang.Throwable th) {
            this.mTaskSupervisor.mUserLeaving = false;
            this.mService.continueWindowLayout();
            if (this.mWindowManager.mRoot.isLayoutNeeded()) {
                this.mWindowManager.mRoot.performSurfacePlacement();
            }
            setProcessAnimating(false);
            android.os.Trace.traceEnd(32L);
            throw th;
        }
    }

    private static void inSurfaceTransaction(java.lang.Runnable runnable) {
        runnable.run();
    }

    private void setProcessAnimating(boolean z) {
        int i;
        if (this.mCaller == null) {
            return;
        }
        this.mCaller.setRunningRecentsAnimation(z);
        int i2 = this.mService.mDemoteTopAppReasons;
        if (z) {
            i = i2 | 2;
        } else {
            i = i2 & (-3);
        }
        this.mService.mDemoteTopAppReasons = i;
        if (z && this.mService.mTopApp != null) {
            this.mService.mTopApp.scheduleUpdateOomAdj();
        }
    }

    @Override // com.android.server.wm.RecentsAnimationController.RecentsAnimationCallbacks
    public void onAnimationFinished(@com.android.server.wm.RecentsAnimationController.ReorderMode int i, boolean z) {
        finishAnimation(i, z);
    }

    @Override // com.android.server.wm.TaskDisplayArea.OnRootTaskOrderChangedListener
    public void onRootTaskOrderChanged(final com.android.server.wm.Task task) {
        com.android.server.wm.RecentsAnimationController recentsAnimationController;
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 4515487264815398694L, 0, null, java.lang.String.valueOf(task));
        if (this.mDefaultTaskDisplayArea.getRootTask(new java.util.function.Predicate() { // from class: com.android.server.wm.RecentsAnimation$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onRootTaskOrderChanged$1;
                lambda$onRootTaskOrderChanged$1 = com.android.server.wm.RecentsAnimation.lambda$onRootTaskOrderChanged$1(com.android.server.wm.Task.this, (com.android.server.wm.Task) obj);
                return lambda$onRootTaskOrderChanged$1;
            }
        }) == null || !task.shouldBeVisible(null) || (recentsAnimationController = this.mWindowManager.getRecentsAnimationController()) == null) {
            return;
        }
        if ((!recentsAnimationController.isAnimatingTask(task.getTopMostTask()) || recentsAnimationController.isTargetApp(task.getTopNonFinishingActivity())) && recentsAnimationController.shouldDeferCancelUntilNextTransition()) {
            this.mWindowManager.prepareAppTransitionNone();
            recentsAnimationController.setCancelOnNextTransitionStart();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onRootTaskOrderChanged$1(com.android.server.wm.Task task, com.android.server.wm.Task task2) {
        return task2 == task;
    }

    private void startRecentsActivityInBackground(java.lang.String str) {
        android.app.ActivityOptions makeBasic = android.app.ActivityOptions.makeBasic();
        makeBasic.setLaunchActivityType(this.mTargetActivityType);
        makeBasic.setAvoidMoveToFront();
        this.mTargetIntent.addFlags(268500992);
        this.mActivityStartController.obtainStarter(this.mTargetIntent, str).setCallingUid(this.mRecentsUid).setCallingPackage(this.mRecentsComponent.getPackageName()).setCallingFeatureId(this.mRecentsFeatureId).setActivityOptions(new com.android.server.wm.SafeActivityOptions(makeBasic)).setUserId(this.mUserId).execute();
    }

    static void notifyAnimationCancelBeforeStart(android.view.IRecentsAnimationRunner iRecentsAnimationRunner) {
        try {
            iRecentsAnimationRunner.onAnimationCanceled((int[]) null, (android.window.TaskSnapshot[]) null);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to cancel recents animation before start", e);
        }
    }

    private com.android.server.wm.Task getTopNonAlwaysOnTopRootTask() {
        return this.mDefaultTaskDisplayArea.getRootTask(new java.util.function.Predicate() { // from class: com.android.server.wm.RecentsAnimation$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTopNonAlwaysOnTopRootTask$2;
                lambda$getTopNonAlwaysOnTopRootTask$2 = com.android.server.wm.RecentsAnimation.lambda$getTopNonAlwaysOnTopRootTask$2((com.android.server.wm.Task) obj);
                return lambda$getTopNonAlwaysOnTopRootTask$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopNonAlwaysOnTopRootTask$2(com.android.server.wm.Task task) {
        return !task.getWindowConfiguration().isAlwaysOnTop();
    }

    private com.android.server.wm.ActivityRecord getTargetActivity(com.android.server.wm.Task task) {
        if (task == null) {
            return null;
        }
        com.android.internal.util.function.pooled.PooledPredicate obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new java.util.function.BiPredicate() { // from class: com.android.server.wm.RecentsAnimation$$ExternalSyntheticLambda0
            @Override // java.util.function.BiPredicate
            public final boolean test(java.lang.Object obj, java.lang.Object obj2) {
                boolean matchesTarget;
                matchesTarget = ((com.android.server.wm.RecentsAnimation) obj).matchesTarget((com.android.server.wm.Task) obj2);
                return matchesTarget;
            }
        }, this, com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.Task.class));
        com.android.server.wm.Task task2 = task.getTask(obtainPredicate);
        obtainPredicate.recycle();
        if (task2 != null) {
            return task2.getTopNonFinishingActivity();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean matchesTarget(com.android.server.wm.Task task) {
        return task.getNonFinishingActivityCount() > 0 && task.mUserId == this.mUserId && task.getBaseIntent().getComponent().equals(this.mTargetIntent.getComponent());
    }
}
