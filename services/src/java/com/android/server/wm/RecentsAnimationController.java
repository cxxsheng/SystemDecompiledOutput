package com.android.server.wm;

/* loaded from: classes3.dex */
public class RecentsAnimationController implements android.os.IBinder.DeathRecipient {
    private static final long FAILSAFE_DELAY = 1000;
    private static final int MODE_UNKNOWN = -1;
    public static final int REORDER_KEEP_IN_PLACE = 0;
    public static final int REORDER_MOVE_TO_ORIGINAL_POSITION = 2;
    public static final int REORDER_MOVE_TO_TOP = 1;
    private static final java.lang.String TAG = com.android.server.wm.RecentsAnimationController.class.getSimpleName();
    private final com.android.server.wm.RecentsAnimationController.RecentsAnimationCallbacks mCallbacks;
    private boolean mCancelDeferredWithScreenshot;
    private boolean mCancelOnNextTransitionStart;
    private boolean mCanceled;
    private com.android.server.wm.DisplayContent mDisplayContent;
    private final int mDisplayId;
    private boolean mInputConsumerEnabled;

    @com.android.internal.annotations.VisibleForTesting
    boolean mIsAddingTaskToTargets;
    private boolean mLinkedToDeathOfRunner;
    private com.android.server.wm.ActivityRecord mNavBarAttachedApp;
    private boolean mNavigationBarAttachedToApp;
    private boolean mRequestDeferCancelUntilNextTransition;
    private android.view.IRecentsAnimationRunner mRunner;
    private final com.android.server.wm.WindowManagerService mService;
    private com.android.server.wm.ActivityRecord mTargetActivityRecord;
    private int mTargetActivityType;
    private final java.util.ArrayList<com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter> mPendingAnimations = new java.util.ArrayList<>();
    private final android.util.IntArray mPendingNewTaskTargets = new android.util.IntArray(0);
    private final java.util.ArrayList<com.android.server.wm.WallpaperAnimationAdapter> mPendingWallpaperAnimations = new java.util.ArrayList<>();
    private boolean mWillFinishToHome = false;
    private final java.lang.Runnable mFailsafeRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.RecentsAnimationController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.wm.RecentsAnimationController.this.onFailsafe();
        }
    };
    private boolean mPendingStart = true;
    private final android.graphics.Rect mTmpRect = new android.graphics.Rect();
    private int mPendingCancelWithScreenshotReorderMode = 2;
    private final java.util.ArrayList<android.view.RemoteAnimationTarget> mPendingTaskAppears = new java.util.ArrayList<>();
    final com.android.server.wm.WindowManagerInternal.AppTransitionListener mAppTransitionListener = new com.android.server.wm.WindowManagerInternal.AppTransitionListener() { // from class: com.android.server.wm.RecentsAnimationController.1
        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public int onAppTransitionStartingLocked(long j, long j2) {
            continueDeferredCancel();
            return 0;
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public void onAppTransitionCancelledLocked(boolean z) {
            continueDeferredCancel();
        }

        private void continueDeferredCancel() {
            com.android.server.wm.RecentsAnimationController.this.mDisplayContent.mAppTransition.unregisterListener(this);
            if (!com.android.server.wm.RecentsAnimationController.this.mCanceled && com.android.server.wm.RecentsAnimationController.this.mCancelOnNextTransitionStart) {
                com.android.server.wm.RecentsAnimationController.this.mCancelOnNextTransitionStart = false;
                com.android.server.wm.RecentsAnimationController.this.cancelAnimationWithScreenshot(com.android.server.wm.RecentsAnimationController.this.mCancelDeferredWithScreenshot);
            }
        }
    };
    private final android.view.IRecentsAnimationController mController = new android.view.IRecentsAnimationController.Stub() { // from class: com.android.server.wm.RecentsAnimationController.2
        public android.window.TaskSnapshot screenshotTask(int i) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 6530904107141905844L, 13, null, java.lang.Long.valueOf(i), java.lang.Boolean.valueOf(com.android.server.wm.RecentsAnimationController.this.mCanceled));
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.wm.RecentsAnimationController.this.mService.getWindowManagerLock()) {
                    if (com.android.server.wm.RecentsAnimationController.this.mCanceled) {
                        return null;
                    }
                    for (int size = com.android.server.wm.RecentsAnimationController.this.mPendingAnimations.size() - 1; size >= 0; size--) {
                        com.android.server.wm.Task task = ((com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter) com.android.server.wm.RecentsAnimationController.this.mPendingAnimations.get(size)).mTask;
                        if (task.mTaskId == i) {
                            com.android.server.wm.TaskSnapshotController taskSnapshotController = com.android.server.wm.RecentsAnimationController.this.mService.mTaskSnapshotController;
                            android.util.ArraySet<com.android.server.wm.Task> newArraySet = com.google.android.collect.Sets.newArraySet(new com.android.server.wm.Task[]{task});
                            taskSnapshotController.snapshotTasks(newArraySet);
                            taskSnapshotController.addSkipClosingAppSnapshotTasks(newArraySet);
                            return taskSnapshotController.getSnapshot(i, task.mUserId, false, false);
                        }
                    }
                    return null;
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setFinishTaskTransaction(int i, android.window.PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction, android.view.SurfaceControl surfaceControl) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -3286551982713129633L, 1, null, java.lang.Long.valueOf(i), java.lang.String.valueOf(pictureInPictureSurfaceTransaction));
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.wm.RecentsAnimationController.this.mService.getWindowManagerLock()) {
                    try {
                        int size = com.android.server.wm.RecentsAnimationController.this.mPendingAnimations.size() - 1;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter taskAnimationAdapter = (com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter) com.android.server.wm.RecentsAnimationController.this.mPendingAnimations.get(size);
                            if (taskAnimationAdapter.mTask.mTaskId != i) {
                                size--;
                            } else {
                                taskAnimationAdapter.mFinishTransaction = pictureInPictureSurfaceTransaction;
                                taskAnimationAdapter.mFinishOverlay = surfaceControl;
                                break;
                            }
                        }
                    } finally {
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void finish(boolean z, boolean z2, com.android.internal.os.IResultReceiver iResultReceiver) {
            int i;
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 5187133389446459984L, 15, null, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(com.android.server.wm.RecentsAnimationController.this.mCanceled));
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.wm.RecentsAnimationController.RecentsAnimationCallbacks recentsAnimationCallbacks = com.android.server.wm.RecentsAnimationController.this.mCallbacks;
                if (z) {
                    i = 1;
                } else {
                    i = 2;
                }
                recentsAnimationCallbacks.onAnimationFinished(i, z2);
                if (iResultReceiver != null) {
                    try {
                        iResultReceiver.send(0, new android.os.Bundle());
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.wm.RecentsAnimationController.TAG, "Failed to report animation finished", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setAnimationTargetsBehindSystemBars(boolean z) throws android.os.RemoteException {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.wm.RecentsAnimationController.this.mService.getWindowManagerLock()) {
                    try {
                        for (int size = com.android.server.wm.RecentsAnimationController.this.mPendingAnimations.size() - 1; size >= 0; size--) {
                            com.android.server.wm.Task task = ((com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter) com.android.server.wm.RecentsAnimationController.this.mPendingAnimations.get(size)).mTask;
                            if (task.getActivityType() != com.android.server.wm.RecentsAnimationController.this.mTargetActivityType) {
                                task.setCanAffectSystemUiFlags(z);
                            }
                        }
                        com.android.server.inputmethod.InputMethodManagerInternal.get().maybeFinishStylusHandwriting();
                        com.android.server.wm.RecentsAnimationController.this.mService.mWindowPlacerLocked.requestTraversal();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setInputConsumerEnabled(boolean z) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 6879496555046975661L, 12, null, java.lang.String.valueOf(z), java.lang.Boolean.valueOf(com.android.server.wm.RecentsAnimationController.this.mCanceled));
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.wm.RecentsAnimationController.this.mService.getWindowManagerLock()) {
                    if (com.android.server.wm.RecentsAnimationController.this.mCanceled) {
                        return;
                    }
                    com.android.server.wm.RecentsAnimationController.this.mInputConsumerEnabled = z;
                    com.android.server.wm.RecentsAnimationController.this.mDisplayContent.getInputMonitor().updateInputWindowsLw(true);
                    com.android.server.wm.RecentsAnimationController.this.mService.scheduleAnimationLocked();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setDeferCancelUntilNextTransition(boolean z, boolean z2) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.RecentsAnimationController.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.RecentsAnimationController.this.setDeferredCancel(z, z2);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }

        public void cleanupScreenshot() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.wm.RecentsAnimationController.this.continueDeferredCancelAnimation();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setWillFinishToHome(boolean z) {
            synchronized (com.android.server.wm.RecentsAnimationController.this.mService.getWindowManagerLock()) {
                com.android.server.wm.RecentsAnimationController.this.setWillFinishToHome(z);
            }
        }

        public boolean removeTask(int i) {
            boolean removeTaskInternal;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.wm.RecentsAnimationController.this.mService.getWindowManagerLock()) {
                    removeTaskInternal = com.android.server.wm.RecentsAnimationController.this.removeTaskInternal(i);
                }
                return removeTaskInternal;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void detachNavigationBarFromApp(boolean z) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.wm.RecentsAnimationController.this.mService.getWindowManagerLock()) {
                    try {
                        com.android.server.wm.RecentsAnimationController.this.restoreNavigationBarFromApp(z || com.android.server.wm.RecentsAnimationController.this.mIsAddingTaskToTargets);
                        com.android.server.wm.RecentsAnimationController.this.mService.mWindowPlacerLocked.requestTraversal();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void animateNavigationBarToApp(long j) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.wm.RecentsAnimationController.this.mService.getWindowManagerLock()) {
                    com.android.server.wm.RecentsAnimationController.this.animateNavigationBarForAppLaunch(j);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    };

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.statusbar.StatusBarManagerInternal mStatusBar = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);

    public interface RecentsAnimationCallbacks {
        void onAnimationFinished(@com.android.server.wm.RecentsAnimationController.ReorderMode int i, boolean z);
    }

    public @interface ReorderMode {
    }

    RecentsAnimationController(com.android.server.wm.WindowManagerService windowManagerService, android.view.IRecentsAnimationRunner iRecentsAnimationRunner, com.android.server.wm.RecentsAnimationController.RecentsAnimationCallbacks recentsAnimationCallbacks, int i) {
        this.mService = windowManagerService;
        this.mRunner = iRecentsAnimationRunner;
        this.mCallbacks = recentsAnimationCallbacks;
        this.mDisplayId = i;
        this.mDisplayContent = windowManagerService.mRoot.getDisplayContent(i);
    }

    public void initialize(int i, android.util.SparseBooleanArray sparseBooleanArray, com.android.server.wm.ActivityRecord activityRecord) {
        this.mTargetActivityType = i;
        this.mDisplayContent.mAppTransition.registerListenerLocked(this.mAppTransitionListener);
        final java.util.ArrayList<com.android.server.wm.Task> visibleTasks = this.mDisplayContent.getDefaultTaskDisplayArea().getVisibleTasks();
        com.android.server.wm.Task rootTask = this.mDisplayContent.getDefaultTaskDisplayArea().getRootTask(0, i);
        if (rootTask != null) {
            rootTask.forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RecentsAnimationController$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.RecentsAnimationController.lambda$initialize$0(visibleTasks, (com.android.server.wm.Task) obj);
                }
            }, true);
        }
        for (int size = visibleTasks.size() - 1; size >= 0; size--) {
            final com.android.server.wm.Task task = visibleTasks.get(size);
            if (!skipAnimation(task)) {
                addAnimation(task, !sparseBooleanArray.get(task.mTaskId), false, new com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.RecentsAnimationController$$ExternalSyntheticLambda2
                    @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                    public final void onAnimationFinished(int i2, com.android.server.wm.AnimationAdapter animationAdapter) {
                        com.android.server.wm.RecentsAnimationController.lambda$initialize$2(com.android.server.wm.Task.this, i2, animationAdapter);
                    }
                });
            }
        }
        if (this.mPendingAnimations.isEmpty()) {
            cancelAnimation(2, "initialize-noVisibleTasks");
            return;
        }
        try {
            linkToDeathOfRunner();
            attachNavigationBarToApp();
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -5305978958548091997L, 0, null, java.lang.String.valueOf(activityRecord.getName()));
            this.mTargetActivityRecord = activityRecord;
            if (activityRecord.windowsCanBeWallpaperTarget()) {
                this.mDisplayContent.pendingLayoutChanges |= 4;
                this.mDisplayContent.setLayoutNeeded();
            }
            this.mService.mWindowPlacerLocked.performSurfacePlacement();
            this.mDisplayContent.mFixedRotationTransitionListener.onStartRecentsAnimation(activityRecord);
            if (this.mStatusBar != null) {
                this.mStatusBar.onRecentsAnimationStateChanged(true);
            }
        } catch (android.os.RemoteException e) {
            cancelAnimation(2, "initialize-failedToLinkToDeath");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initialize$0(java.util.ArrayList arrayList, com.android.server.wm.Task task) {
        if (!arrayList.contains(task)) {
            arrayList.add(task);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initialize$2(com.android.server.wm.Task task, final int i, final com.android.server.wm.AnimationAdapter animationAdapter) {
        task.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.RecentsAnimationController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.WindowState) obj).onAnimationFinished(i, animationAdapter);
            }
        }, true);
    }

    protected boolean isInterestingForAllDrawn(com.android.server.wm.WindowState windowState) {
        return (isTargetApp(windowState.getActivityRecord()) && windowState.getWindowType() != 1 && windowState.getAttrs().alpha == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) ? false : true;
    }

    private boolean skipAnimation(com.android.server.wm.Task task) {
        return task.isAlwaysOnTop() || task.getWindowConfiguration().tasksAreFloating();
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter addAnimation(com.android.server.wm.Task task, boolean z) {
        return addAnimation(task, z, false, null);
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter addAnimation(com.android.server.wm.Task task, boolean z, boolean z2, com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -3801497203749932106L, 0, null, java.lang.String.valueOf(task.getName()));
        com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter taskAnimationAdapter = new com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter(task, z);
        task.startAnimation(task.getPendingTransaction(), taskAnimationAdapter, z2, 8, onAnimationFinishedCallback);
        task.commitPendingTransaction();
        this.mPendingAnimations.add(taskAnimationAdapter);
        return taskAnimationAdapter;
    }

    @com.android.internal.annotations.VisibleForTesting
    void removeAnimation(com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter taskAnimationAdapter) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 3721473589747203697L, 1, null, java.lang.Long.valueOf(taskAnimationAdapter.mTask.mTaskId));
        taskAnimationAdapter.onRemove();
        this.mPendingAnimations.remove(taskAnimationAdapter);
    }

    @com.android.internal.annotations.VisibleForTesting
    void removeWallpaperAnimation(com.android.server.wm.WallpaperAnimationAdapter wallpaperAnimationAdapter) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 5156407755139006078L, 0, null, null);
        wallpaperAnimationAdapter.getLeashFinishedCallback().onAnimationFinished(wallpaperAnimationAdapter.getLastAnimationType(), wallpaperAnimationAdapter);
        this.mPendingWallpaperAnimations.remove(wallpaperAnimationAdapter);
    }

    void startAnimation() {
        android.view.RemoteAnimationTarget[] createAppAnimations;
        android.graphics.Rect rect;
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -1997836523186474317L, 15, null, java.lang.Boolean.valueOf(this.mPendingStart), java.lang.Boolean.valueOf(this.mCanceled));
        if (!this.mPendingStart || this.mCanceled) {
            return;
        }
        try {
            createAppAnimations = createAppAnimations();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to start recents animation", e);
        }
        if (createAppAnimations.length == 0) {
            cancelAnimation(2, "startAnimation-noAppWindows");
            return;
        }
        android.view.RemoteAnimationTarget[] createWallpaperAnimations = createWallpaperAnimations();
        this.mPendingStart = false;
        com.android.server.wm.WindowState targetAppMainWindow = getTargetAppMainWindow();
        if (targetAppMainWindow != null) {
            rect = targetAppMainWindow.getInsetsStateWithVisibilityOverride().calculateInsets(this.mTargetActivityRecord.getBounds(), android.view.WindowInsets.Type.systemBars(), false).toRect();
        } else {
            this.mService.getStableInsets(this.mDisplayId, this.mTmpRect);
            rect = this.mTmpRect;
        }
        this.mRunner.onAnimationStart(this.mController, createAppAnimations, createWallpaperAnimations, rect, (android.graphics.Rect) null, new android.os.Bundle());
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -7532294363367395195L, 0, null, java.lang.String.valueOf(this.mPendingAnimations.stream().map(new java.util.function.Function() { // from class: com.android.server.wm.RecentsAnimationController$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer lambda$startAnimation$3;
                lambda$startAnimation$3 = com.android.server.wm.RecentsAnimationController.lambda$startAnimation$3((com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter) obj);
                return lambda$startAnimation$3;
            }
        }).collect(java.util.stream.Collectors.toList())));
        if (this.mTargetActivityRecord != null) {
            android.util.ArrayMap<com.android.server.wm.WindowContainer, java.lang.Integer> arrayMap = new android.util.ArrayMap<>(1);
            arrayMap.put(this.mTargetActivityRecord, 5);
            this.mService.mAtmService.mTaskSupervisor.getActivityMetricsLogger().notifyTransitionStarting(arrayMap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$startAnimation$3(com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter taskAnimationAdapter) {
        return java.lang.Integer.valueOf(taskAnimationAdapter.mTask.mTaskId);
    }

    boolean isNavigationBarAttachedToApp() {
        return this.mNavigationBarAttachedToApp;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.WindowState getNavigationBarWindow() {
        return this.mDisplayContent.getDisplayPolicy().getNavigationBar();
    }

    private void attachNavigationBarToApp() {
        if (!this.mDisplayContent.getDisplayPolicy().shouldAttachNavBarToAppDuringTransition() || this.mDisplayContent.getAsyncRotationController() != null) {
            return;
        }
        int size = this.mPendingAnimations.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            com.android.server.wm.Task task = this.mPendingAnimations.get(size).mTask;
            if (task.isActivityTypeHomeOrRecents()) {
                size--;
            } else {
                this.mNavBarAttachedApp = task.getTopVisibleActivity();
                break;
            }
        }
        com.android.server.wm.WindowState navigationBarWindow = getNavigationBarWindow();
        if (this.mNavBarAttachedApp == null || navigationBarWindow == null || navigationBarWindow.mToken == null) {
            return;
        }
        this.mNavigationBarAttachedToApp = true;
        navigationBarWindow.mToken.cancelAnimation();
        android.view.SurfaceControl.Transaction pendingTransaction = navigationBarWindow.mToken.getPendingTransaction();
        android.view.SurfaceControl surfaceControl = navigationBarWindow.mToken.getSurfaceControl();
        navigationBarWindow.setSurfaceTranslationY(-this.mNavBarAttachedApp.getBounds().top);
        pendingTransaction.reparent(surfaceControl, this.mNavBarAttachedApp.getSurfaceControl());
        pendingTransaction.show(surfaceControl);
        com.android.server.wm.DisplayArea.Tokens imeContainer = this.mDisplayContent.getImeContainer();
        if (imeContainer.isVisible()) {
            pendingTransaction.setRelativeLayer(surfaceControl, imeContainer.getSurfaceControl(), 1);
        } else {
            pendingTransaction.setLayer(surfaceControl, Integer.MAX_VALUE);
        }
        if (this.mStatusBar != null) {
            this.mStatusBar.setNavigationBarLumaSamplingEnabled(this.mDisplayId, false);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void restoreNavigationBarFromApp(boolean z) {
        if (!this.mNavigationBarAttachedToApp) {
            return;
        }
        this.mNavigationBarAttachedToApp = false;
        if (this.mStatusBar != null) {
            this.mStatusBar.setNavigationBarLumaSamplingEnabled(this.mDisplayId, true);
        }
        com.android.server.wm.WindowState navigationBarWindow = getNavigationBarWindow();
        if (navigationBarWindow == null) {
            return;
        }
        navigationBarWindow.setSurfaceTranslationY(0);
        com.android.server.wm.WindowToken windowToken = navigationBarWindow.mToken;
        if (windowToken == null) {
            return;
        }
        android.view.SurfaceControl.Transaction pendingTransaction = this.mDisplayContent.getPendingTransaction();
        com.android.server.wm.WindowContainer parent = windowToken.getParent();
        pendingTransaction.setLayer(windowToken.getSurfaceControl(), windowToken.getLastLayer());
        if (z) {
            new com.android.server.wm.NavBarFadeAnimationController(this.mDisplayContent).fadeWindowToken(true);
        } else {
            pendingTransaction.reparent(windowToken.getSurfaceControl(), parent.getSurfaceControl());
        }
    }

    void animateNavigationBarForAppLaunch(long j) {
        if (!this.mDisplayContent.getDisplayPolicy().shouldAttachNavBarToAppDuringTransition() || this.mDisplayContent.getAsyncRotationController() != null || this.mNavigationBarAttachedToApp || this.mNavBarAttachedApp == null) {
            return;
        }
        new com.android.server.wm.NavBarFadeAnimationController(this.mDisplayContent).fadeOutAndInSequentially(j, null, this.mNavBarAttachedApp.getSurfaceControl());
    }

    void addTaskToTargets(com.android.server.wm.Task task, com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        if (this.mRunner != null) {
            this.mIsAddingTaskToTargets = task != null;
            this.mNavBarAttachedApp = task == null ? null : task.getTopVisibleActivity();
            if (isAnimatingTask(task) || skipAnimation(task)) {
                return;
            }
            collectTaskRemoteAnimations(task, 0, onAnimationFinishedCallback);
        }
    }

    void sendTasksAppeared() {
        if (this.mPendingTaskAppears.isEmpty() || this.mRunner == null) {
            return;
        }
        try {
            this.mRunner.onTasksAppeared((android.view.RemoteAnimationTarget[]) this.mPendingTaskAppears.toArray(new android.view.RemoteAnimationTarget[0]));
            this.mPendingTaskAppears.clear();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to report task appeared", e);
        }
    }

    private void collectTaskRemoteAnimations(com.android.server.wm.Task task, final int i, final com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        final android.util.SparseBooleanArray recentTaskIds = this.mService.mAtmService.getRecentTasks().getRecentTaskIds();
        task.forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.RecentsAnimationController$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RecentsAnimationController.this.lambda$collectTaskRemoteAnimations$4(recentTaskIds, onAnimationFinishedCallback, i, (com.android.server.wm.Task) obj);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$collectTaskRemoteAnimations$4(android.util.SparseBooleanArray sparseBooleanArray, com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback, int i, com.android.server.wm.Task task) {
        if (!task.shouldBeVisible(null)) {
            return;
        }
        int i2 = task.mTaskId;
        com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter addAnimation = addAnimation(task, !sparseBooleanArray.get(i2), true, onAnimationFinishedCallback);
        this.mPendingNewTaskTargets.add(i2);
        android.view.RemoteAnimationTarget createRemoteAnimationTarget = addAnimation.createRemoteAnimationTarget(i2, i);
        if (createRemoteAnimationTarget != null) {
            this.mPendingTaskAppears.add(createRemoteAnimationTarget);
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -1336603089105439710L, 0, null, java.lang.String.valueOf(createRemoteAnimationTarget));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean removeTaskInternal(int i) {
        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
            com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter taskAnimationAdapter = this.mPendingAnimations.get(size);
            if (taskAnimationAdapter.mTask.mTaskId == i && taskAnimationAdapter.mTask.isOnTop()) {
                removeAnimation(taskAnimationAdapter);
                int indexOf = this.mPendingNewTaskTargets.indexOf(i);
                if (indexOf == -1) {
                    return true;
                }
                this.mPendingNewTaskTargets.remove(indexOf);
                return true;
            }
        }
        return false;
    }

    private android.view.RemoteAnimationTarget[] createAppAnimations() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
            com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter taskAnimationAdapter = this.mPendingAnimations.get(size);
            android.view.RemoteAnimationTarget createRemoteAnimationTarget = taskAnimationAdapter.createRemoteAnimationTarget(-1, -1);
            if (createRemoteAnimationTarget != null) {
                arrayList.add(createRemoteAnimationTarget);
            } else {
                removeAnimation(taskAnimationAdapter);
            }
        }
        return (android.view.RemoteAnimationTarget[]) arrayList.toArray(new android.view.RemoteAnimationTarget[arrayList.size()]);
    }

    private android.view.RemoteAnimationTarget[] createWallpaperAnimations() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 2547528895718568379L, 0, null, null);
        return com.android.server.wm.WallpaperAnimationAdapter.startWallpaperAnimations(this.mDisplayContent, 0L, 0L, new java.util.function.Consumer() { // from class: com.android.server.wm.RecentsAnimationController$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.RecentsAnimationController.this.lambda$createWallpaperAnimations$5((com.android.server.wm.WallpaperAnimationAdapter) obj);
            }
        }, this.mPendingWallpaperAnimations);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createWallpaperAnimations$5(com.android.server.wm.WallpaperAnimationAdapter wallpaperAnimationAdapter) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mPendingWallpaperAnimations.remove(wallpaperAnimationAdapter);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void forceCancelAnimation(@com.android.server.wm.RecentsAnimationController.ReorderMode int i, java.lang.String str) {
        if (!this.mCanceled) {
            cancelAnimation(i, str);
        } else {
            continueDeferredCancelAnimation();
        }
    }

    void cancelAnimation(@com.android.server.wm.RecentsAnimationController.ReorderMode int i, java.lang.String str) {
        cancelAnimation(i, false, str);
    }

    void cancelAnimationWithScreenshot(boolean z) {
        cancelAnimation(0, z, "rootTaskOrderChanged");
    }

    public void cancelAnimationForHomeStart() {
        int i;
        if (this.mTargetActivityType == 2 && this.mWillFinishToHome) {
            i = 1;
        } else {
            i = 0;
        }
        cancelAnimation(i, true, "cancelAnimationForHomeStart");
    }

    public void cancelAnimationForDisplayChange() {
        cancelAnimation(this.mWillFinishToHome ? 1 : 2, true, "cancelAnimationForDisplayChange");
    }

    private void cancelAnimation(@com.android.server.wm.RecentsAnimationController.ReorderMode int i, boolean z, java.lang.String str) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 5444932814080651576L, 0, null, java.lang.String.valueOf(str));
        synchronized (this.mService.getWindowManagerLock()) {
            try {
                if (this.mCanceled) {
                    return;
                }
                this.mService.mH.removeCallbacks(this.mFailsafeRunnable);
                this.mCanceled = true;
                if (z && !this.mPendingAnimations.isEmpty()) {
                    android.util.ArrayMap<com.android.server.wm.Task, android.window.TaskSnapshot> screenshotRecentTasks = screenshotRecentTasks();
                    this.mPendingCancelWithScreenshotReorderMode = i;
                    if (!screenshotRecentTasks.isEmpty()) {
                        try {
                            int[] iArr = new int[screenshotRecentTasks.size()];
                            android.window.TaskSnapshot[] taskSnapshotArr = new android.window.TaskSnapshot[screenshotRecentTasks.size()];
                            for (int size = screenshotRecentTasks.size() - 1; size >= 0; size--) {
                                iArr[size] = screenshotRecentTasks.keyAt(size).mTaskId;
                                taskSnapshotArr[size] = screenshotRecentTasks.valueAt(size);
                            }
                            this.mRunner.onAnimationCanceled(iArr, taskSnapshotArr);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(TAG, "Failed to cancel recents animation", e);
                        }
                        scheduleFailsafe();
                        return;
                    }
                }
                try {
                    this.mRunner.onAnimationCanceled((int[]) null, (android.window.TaskSnapshot[]) null);
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.e(TAG, "Failed to cancel recents animation", e2);
                }
                this.mCallbacks.onAnimationFinished(i, false);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void continueDeferredCancelAnimation() {
        this.mCallbacks.onAnimationFinished(this.mPendingCancelWithScreenshotReorderMode, false);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setWillFinishToHome(boolean z) {
        this.mWillFinishToHome = z;
    }

    void setCancelOnNextTransitionStart() {
        this.mCancelOnNextTransitionStart = true;
    }

    void setDeferredCancel(boolean z, boolean z2) {
        this.mRequestDeferCancelUntilNextTransition = z;
        this.mCancelDeferredWithScreenshot = z2;
    }

    boolean shouldDeferCancelUntilNextTransition() {
        return this.mRequestDeferCancelUntilNextTransition;
    }

    boolean shouldDeferCancelWithScreenshot() {
        return this.mRequestDeferCancelUntilNextTransition && this.mCancelDeferredWithScreenshot;
    }

    private android.util.ArrayMap<com.android.server.wm.Task, android.window.TaskSnapshot> screenshotRecentTasks() {
        com.android.server.wm.TaskSnapshotController taskSnapshotController = this.mService.mTaskSnapshotController;
        android.util.ArrayMap<com.android.server.wm.Task, android.window.TaskSnapshot> arrayMap = new android.util.ArrayMap<>();
        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
            com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter taskAnimationAdapter = this.mPendingAnimations.get(size);
            com.android.server.wm.Task task = taskAnimationAdapter.mTask;
            if (!task.isActivityTypeHome()) {
                taskSnapshotController.recordSnapshot(task);
                android.window.TaskSnapshot snapshot = taskSnapshotController.getSnapshot(task.mTaskId, task.mUserId, false, false);
                if (snapshot != null) {
                    arrayMap.put(task, snapshot);
                    taskAnimationAdapter.setSnapshotOverlay(snapshot);
                }
            }
        }
        taskSnapshotController.addSkipClosingAppSnapshotTasks(arrayMap.keySet());
        return arrayMap;
    }

    void cleanupAnimation(@com.android.server.wm.RecentsAnimationController.ReorderMode int i) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 622027757443954945L, 5, null, java.lang.Long.valueOf(this.mPendingAnimations.size()), java.lang.Long.valueOf(i));
        if (i != 2 && this.mTargetActivityRecord != this.mDisplayContent.topRunningActivity()) {
            this.mDisplayContent.mFixedRotationTransitionListener.notifyRecentsWillBeTop();
        }
        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
            com.android.server.wm.RecentsAnimationController.TaskAnimationAdapter taskAnimationAdapter = this.mPendingAnimations.get(size);
            if (i == 1 || i == 0) {
                taskAnimationAdapter.mTask.dontAnimateDimExit();
            }
            removeAnimation(taskAnimationAdapter);
            taskAnimationAdapter.onCleanup();
        }
        this.mPendingNewTaskTargets.clear();
        this.mPendingTaskAppears.clear();
        for (int size2 = this.mPendingWallpaperAnimations.size() - 1; size2 >= 0; size2--) {
            removeWallpaperAnimation(this.mPendingWallpaperAnimations.get(size2));
        }
        restoreNavigationBarFromApp(i == 1 || this.mIsAddingTaskToTargets);
        this.mService.mH.removeCallbacks(this.mFailsafeRunnable);
        this.mDisplayContent.mAppTransition.unregisterListener(this.mAppTransitionListener);
        unlinkToDeathOfRunner();
        this.mRunner = null;
        this.mCanceled = true;
        if (i == 2 && !this.mIsAddingTaskToTargets) {
            com.android.server.inputmethod.InputMethodManagerInternal.get().updateImeWindowStatus(false, this.mDisplayId);
        }
        this.mDisplayContent.getInputMonitor().updateInputWindowsLw(true);
        if (this.mTargetActivityRecord != null && (i == 1 || i == 0)) {
            this.mDisplayContent.mAppTransition.notifyAppTransitionFinishedLocked(this.mTargetActivityRecord.token);
        }
        this.mDisplayContent.mFixedRotationTransitionListener.onFinishRecentsAnimation();
        if (this.mStatusBar != null) {
            this.mStatusBar.onRecentsAnimationStateChanged(false);
        }
    }

    void scheduleFailsafe() {
        this.mService.mH.postDelayed(this.mFailsafeRunnable, 1000L);
    }

    void onFailsafe() {
        forceCancelAnimation(this.mWillFinishToHome ? 1 : 2, "onFailsafe");
    }

    private void linkToDeathOfRunner() throws android.os.RemoteException {
        if (!this.mLinkedToDeathOfRunner) {
            this.mRunner.asBinder().linkToDeath(this, 0);
            this.mLinkedToDeathOfRunner = true;
        }
    }

    private void unlinkToDeathOfRunner() {
        if (this.mLinkedToDeathOfRunner) {
            this.mRunner.asBinder().unlinkToDeath(this, 0);
            this.mLinkedToDeathOfRunner = false;
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        forceCancelAnimation(2, "binderDied");
        synchronized (this.mService.getWindowManagerLock()) {
            try {
                com.android.server.wm.InputMonitor inputMonitor = this.mDisplayContent.getInputMonitor();
                com.android.server.wm.InputConsumerImpl inputConsumer = inputMonitor.getInputConsumer("recents_animation_input_consumer");
                if (inputConsumer != null) {
                    inputMonitor.destroyInputConsumer(inputConsumer.mToken);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void checkAnimationReady(com.android.server.wm.WallpaperController wallpaperController) {
        if (this.mPendingStart) {
            if (!isTargetOverWallpaper() || (wallpaperController.getWallpaperTarget() != null && wallpaperController.wallpaperTransitionReady())) {
                this.mService.getRecentsAnimationController().startAnimation();
            }
        }
    }

    boolean isWallpaperVisible(com.android.server.wm.WindowState windowState) {
        return windowState != null && windowState.mAttrs.type == 1 && ((windowState.mActivityRecord != null && this.mTargetActivityRecord == windowState.mActivityRecord) || isAnimatingTask(windowState.getTask())) && isTargetOverWallpaper() && windowState.isOnScreen();
    }

    boolean shouldApplyInputConsumer(com.android.server.wm.ActivityRecord activityRecord) {
        return this.mInputConsumerEnabled && activityRecord != null && !isTargetApp(activityRecord) && isAnimatingApp(activityRecord);
    }

    boolean updateInputConsumerForApp(android.view.InputWindowHandle inputWindowHandle) {
        com.android.server.wm.WindowState targetAppMainWindow = getTargetAppMainWindow();
        if (targetAppMainWindow != null) {
            targetAppMainWindow.getBounds(this.mTmpRect);
            inputWindowHandle.touchableRegion.set(this.mTmpRect);
            return true;
        }
        return false;
    }

    boolean isTargetApp(com.android.server.wm.ActivityRecord activityRecord) {
        return this.mTargetActivityRecord != null && activityRecord == this.mTargetActivityRecord;
    }

    private boolean isTargetOverWallpaper() {
        if (this.mTargetActivityRecord == null) {
            return false;
        }
        return this.mTargetActivityRecord.windowsCanBeWallpaperTarget();
    }

    com.android.server.wm.WindowState getTargetAppMainWindow() {
        if (this.mTargetActivityRecord == null) {
            return null;
        }
        return this.mTargetActivityRecord.findMainWindow();
    }

    com.android.server.wm.DisplayArea getTargetAppDisplayArea() {
        if (this.mTargetActivityRecord == null) {
            return null;
        }
        return this.mTargetActivityRecord.getDisplayArea();
    }

    boolean isAnimatingTask(com.android.server.wm.Task task) {
        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
            if (task == this.mPendingAnimations.get(size).mTask) {
                return true;
            }
        }
        return false;
    }

    boolean isAnimatingWallpaper(com.android.server.wm.WallpaperWindowToken wallpaperWindowToken) {
        for (int size = this.mPendingWallpaperAnimations.size() - 1; size >= 0; size--) {
            if (wallpaperWindowToken == this.mPendingWallpaperAnimations.get(size).getToken()) {
                return true;
            }
        }
        return false;
    }

    private boolean isAnimatingApp(com.android.server.wm.ActivityRecord activityRecord) {
        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
            if (activityRecord.isDescendantOf(this.mPendingAnimations.get(size).mTask)) {
                return true;
            }
        }
        return false;
    }

    boolean shouldIgnoreForAccessibility(com.android.server.wm.WindowState windowState) {
        com.android.server.wm.Task task = windowState.getTask();
        return (task == null || !isAnimatingTask(task) || isTargetApp(windowState.mActivityRecord)) ? false : true;
    }

    void linkFixedRotationTransformIfNeeded(@android.annotation.NonNull com.android.server.wm.WindowToken windowToken) {
        if (this.mTargetActivityRecord == null) {
            return;
        }
        windowToken.linkFixedRotationTransform(this.mTargetActivityRecord);
    }

    @com.android.internal.annotations.VisibleForTesting
    class TaskAnimationAdapter implements com.android.server.wm.AnimationAdapter {
        private com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback mCapturedFinishCallback;
        private android.view.SurfaceControl mCapturedLeash;
        private android.view.SurfaceControl mFinishOverlay;
        private android.window.PictureInPictureSurfaceTransaction mFinishTransaction;
        private final boolean mIsRecentTaskInvisible;
        private int mLastAnimationType;
        private android.view.SurfaceControl mSnapshotOverlay;
        private android.view.RemoteAnimationTarget mTarget;
        private final com.android.server.wm.Task mTask;
        private final android.graphics.Rect mBounds = new android.graphics.Rect();
        private final android.graphics.Rect mLocalBounds = new android.graphics.Rect();

        TaskAnimationAdapter(com.android.server.wm.Task task, boolean z) {
            this.mTask = task;
            this.mIsRecentTaskInvisible = z;
            this.mBounds.set(this.mTask.getBounds());
            this.mLocalBounds.set(this.mBounds);
            android.graphics.Point point = new android.graphics.Point();
            this.mTask.getRelativePosition(point);
            this.mLocalBounds.offsetTo(point.x, point.y);
        }

        android.view.RemoteAnimationTarget createRemoteAnimationTarget(int i, int i2) {
            com.android.server.wm.WindowState windowState;
            int i3;
            int i4;
            com.android.server.wm.ActivityRecord topRealVisibleActivity = this.mTask.getTopRealVisibleActivity();
            if (topRealVisibleActivity == null) {
                topRealVisibleActivity = this.mTask.getTopVisibleActivity();
            }
            if (topRealVisibleActivity != null) {
                windowState = topRealVisibleActivity.findMainWindow();
            } else {
                windowState = null;
            }
            if (windowState == null) {
                return null;
            }
            android.graphics.Rect rect = windowState.getInsetsStateWithVisibilityOverride().calculateInsets(this.mBounds, android.view.WindowInsets.Type.systemBars(), false).toRect();
            com.android.server.wm.utils.InsetUtils.addInsets(rect, windowState.mActivityRecord.getLetterboxInsets());
            if (i2 == -1) {
                if (topRealVisibleActivity.getActivityType() == com.android.server.wm.RecentsAnimationController.this.mTargetActivityType) {
                    i3 = 0;
                } else {
                    i3 = 1;
                }
            } else {
                i3 = i2;
            }
            if (i >= 0) {
                i4 = i;
            } else {
                i4 = this.mTask.mTaskId;
            }
            this.mTarget = new android.view.RemoteAnimationTarget(i4, i3, this.mCapturedLeash, !topRealVisibleActivity.fillsParent(), new android.graphics.Rect(), rect, this.mTask.getPrefixOrderIndex(), new android.graphics.Point(this.mBounds.left, this.mBounds.top), this.mLocalBounds, this.mBounds, this.mTask.getWindowConfiguration(), this.mIsRecentTaskInvisible, (android.view.SurfaceControl) null, (android.graphics.Rect) null, this.mTask.getTaskInfo(), topRealVisibleActivity.checkEnterPictureInPictureAppOpsState());
            com.android.server.wm.ActivityRecord topNonFinishingActivity = this.mTask.getTopNonFinishingActivity();
            if (topNonFinishingActivity != null && topNonFinishingActivity.mStartingData != null && topNonFinishingActivity.mStartingData.hasImeSurface()) {
                this.mTarget.setWillShowImeOnTarget(true);
            }
            return this.mTarget;
        }

        void setSnapshotOverlay(android.window.TaskSnapshot taskSnapshot) {
            android.hardware.HardwareBuffer hardwareBuffer = taskSnapshot.getHardwareBuffer();
            if (hardwareBuffer == null) {
                return;
            }
            this.mSnapshotOverlay = com.android.server.wm.RecentsAnimationController.this.mService.mSurfaceControlFactory.apply(new android.view.SurfaceSession()).setName("RecentTaskScreenshotSurface").setCallsite("TaskAnimationAdapter.setSnapshotOverlay").setFormat(hardwareBuffer.getFormat()).setParent(this.mCapturedLeash).setBLASTLayer().build();
            float width = (this.mTask.getBounds().width() * 1.0f) / hardwareBuffer.getWidth();
            this.mTask.getPendingTransaction().setBuffer(this.mSnapshotOverlay, android.graphics.GraphicBuffer.createFromHardwareBuffer(hardwareBuffer)).setColorSpace(this.mSnapshotOverlay, taskSnapshot.getColorSpace()).setLayer(this.mSnapshotOverlay, Integer.MAX_VALUE).setMatrix(this.mSnapshotOverlay, width, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, width).show(this.mSnapshotOverlay).apply();
        }

        void onRemove() {
            if (this.mSnapshotOverlay != null) {
                this.mTask.getPendingTransaction().remove(this.mSnapshotOverlay).apply();
                this.mSnapshotOverlay = null;
            }
            this.mTask.setCanAffectSystemUiFlags(true);
            this.mCapturedFinishCallback.onAnimationFinished(this.mLastAnimationType, this);
        }

        void onCleanup() {
            android.view.SurfaceControl.Transaction pendingTransaction = this.mTask.getPendingTransaction();
            if (this.mFinishTransaction != null) {
                if (this.mFinishOverlay != null) {
                    pendingTransaction.reparent(this.mFinishOverlay, this.mTask.mSurfaceControl);
                }
                android.window.PictureInPictureSurfaceTransaction.apply(this.mFinishTransaction, this.mTask.mSurfaceControl, pendingTransaction);
                this.mTask.setLastRecentsAnimationTransaction(this.mFinishTransaction, this.mFinishOverlay);
                if (com.android.server.wm.RecentsAnimationController.this.mDisplayContent.isFixedRotationLaunchingApp(com.android.server.wm.RecentsAnimationController.this.mTargetActivityRecord)) {
                    com.android.server.wm.RecentsAnimationController.this.mDisplayContent.mPinnedTaskController.setEnterPipTransaction(this.mFinishTransaction);
                }
                if (this.mTask.getActivityType() != com.android.server.wm.RecentsAnimationController.this.mTargetActivityType && this.mFinishTransaction.getShouldDisableCanAffectSystemUiFlags()) {
                    this.mTask.setCanAffectSystemUiFlags(false);
                }
                this.mFinishTransaction = null;
                this.mFinishOverlay = null;
                pendingTransaction.apply();
                return;
            }
            if (!this.mTask.isAttached()) {
                pendingTransaction.apply();
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        public android.view.SurfaceControl getSnapshotOverlay() {
            return this.mSnapshotOverlay;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public boolean getShowWallpaper() {
            return false;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void startAnimation(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, int i, @android.annotation.NonNull com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            transaction.setPosition(surfaceControl, this.mLocalBounds.left, this.mLocalBounds.top);
            com.android.server.wm.RecentsAnimationController.this.mTmpRect.set(this.mLocalBounds);
            com.android.server.wm.RecentsAnimationController.this.mTmpRect.offsetTo(0, 0);
            transaction.setWindowCrop(surfaceControl, com.android.server.wm.RecentsAnimationController.this.mTmpRect);
            this.mCapturedLeash = surfaceControl;
            this.mCapturedFinishCallback = onAnimationFinishedCallback;
            this.mLastAnimationType = i;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void onAnimationCancelled(android.view.SurfaceControl surfaceControl) {
            com.android.server.wm.RecentsAnimationController.this.cancelAnimation(2, "taskAnimationAdapterCanceled");
        }

        @Override // com.android.server.wm.AnimationAdapter
        public long getDurationHint() {
            return 0L;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public long getStatusBarTransitionsStartTime() {
            return android.os.SystemClock.uptimeMillis();
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.println("task=" + this.mTask);
            if (this.mTarget != null) {
                printWriter.print(str);
                printWriter.println("Target:");
                this.mTarget.dump(printWriter, str + "  ");
            } else {
                printWriter.print(str);
                printWriter.println("Target: null");
            }
            printWriter.println("mIsRecentTaskInvisible=" + this.mIsRecentTaskInvisible);
            printWriter.println("mLocalBounds=" + this.mLocalBounds);
            printWriter.println("mFinishTransaction=" + this.mFinishTransaction);
            printWriter.println("mBounds=" + this.mBounds);
            printWriter.println("mIsRecentTaskInvisible=" + this.mIsRecentTaskInvisible);
        }

        @Override // com.android.server.wm.AnimationAdapter
        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268034L);
            if (this.mTarget != null) {
                this.mTarget.dumpDebug(protoOutputStream, 1146756268033L);
            }
            protoOutputStream.end(start);
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        java.lang.String str2 = str + "  ";
        printWriter.print(str);
        printWriter.println(com.android.server.wm.RecentsAnimationController.class.getSimpleName() + ":");
        printWriter.print(str2);
        printWriter.println("mPendingStart=" + this.mPendingStart);
        printWriter.print(str2);
        printWriter.println("mPendingAnimations=" + this.mPendingAnimations.size());
        printWriter.print(str2);
        printWriter.println("mCanceled=" + this.mCanceled);
        printWriter.print(str2);
        printWriter.println("mInputConsumerEnabled=" + this.mInputConsumerEnabled);
        printWriter.print(str2);
        printWriter.println("mTargetActivityRecord=" + this.mTargetActivityRecord);
        printWriter.print(str2);
        printWriter.println("isTargetOverWallpaper=" + isTargetOverWallpaper());
        printWriter.print(str2);
        printWriter.println("mRequestDeferCancelUntilNextTransition=" + this.mRequestDeferCancelUntilNextTransition);
        printWriter.print(str2);
        printWriter.println("mCancelOnNextTransitionStart=" + this.mCancelOnNextTransitionStart);
        printWriter.print(str2);
        printWriter.println("mCancelDeferredWithScreenshot=" + this.mCancelDeferredWithScreenshot);
        printWriter.print(str2);
        printWriter.println("mPendingCancelWithScreenshotReorderMode=" + this.mPendingCancelWithScreenshotReorderMode);
    }
}
