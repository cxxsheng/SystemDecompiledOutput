package com.android.server.wm;

/* loaded from: classes3.dex */
class BackNavigationController {
    private static final java.lang.String TAG = "CoreBackPreview";
    private static int sDefaultAnimationResId;
    static final boolean sPredictBackEnable = android.os.SystemProperties.getBoolean("persist.wm.debug.predictive_back", true);
    private com.android.server.wm.BackNavigationController.AnimationHandler mAnimationHandler;
    private boolean mBackAnimationInProgress;
    private int mLastBackType;
    private java.lang.Runnable mPendingAnimation;
    private com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder mPendingAnimationBuilder;
    private boolean mShowWallpaper;
    private com.android.server.wm.Transition mWaitTransitionFinish;
    private com.android.server.wm.WindowManagerService mWindowManagerService;
    private final com.android.server.wm.BackNavigationController.NavigationMonitor mNavigationMonitor = new com.android.server.wm.BackNavigationController.NavigationMonitor();
    private final java.util.ArrayList<com.android.server.wm.WindowContainer> mTmpOpenApps = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.WindowContainer> mTmpCloseApps = new java.util.ArrayList<>();

    BackNavigationController() {
    }

    void onFocusChanged(com.android.server.wm.WindowState windowState) {
        this.mNavigationMonitor.onFocusWindowChanged(windowState);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.window.BackNavigationInfo startBackNavigation(@android.annotation.NonNull android.os.RemoteCallback remoteCallback, android.window.BackAnimationAdapter backAnimationAdapter) {
        char c;
        com.android.server.wm.Task task;
        com.android.server.wm.WindowContainer windowContainer;
        if (!sPredictBackEnable) {
            return null;
        }
        com.android.server.wm.WindowManagerService windowManagerService = this.mWindowManagerService;
        android.window.BackNavigationInfo.Builder builder = new android.window.BackNavigationInfo.Builder();
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (isMonitoringTransition()) {
                    android.util.Slog.w(TAG, "Previous animation hasn't finish, status: " + this.mAnimationHandler);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                com.android.server.wm.WindowState focusedWindowLocked = windowManagerService.getFocusedWindowLocked();
                com.android.server.wm.WindowState windowState = focusedWindowLocked;
                if (focusedWindowLocked == null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, -699215053676660941L, 0, "No focused window, defaulting to top current task's window", null);
                    com.android.server.wm.Task topDisplayFocusedRootTask = windowManagerService.mAtmService.getTopDisplayFocusedRootTask();
                    windowState = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda4
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            return ((com.android.server.wm.WindowState) obj).isFocused();
                        }
                    }) : null;
                }
                if (windowState == null) {
                    android.util.Slog.e(TAG, "Window is null, returning null.");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                boolean moveFocusToTopEmbeddedWindow = this.mWindowManagerService.moveFocusToTopEmbeddedWindow(windowState);
                com.android.server.wm.WindowState windowState2 = windowState;
                if (moveFocusToTopEmbeddedWindow) {
                    com.android.server.wm.WindowState focusedWindowLocked2 = windowManagerService.getFocusedWindowLocked();
                    windowState2 = focusedWindowLocked2;
                    if (focusedWindowLocked2 == null) {
                        android.util.Slog.e(TAG, "New focused window is null, returning null.");
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return null;
                    }
                }
                com.android.server.wm.RecentsAnimationController recentsAnimationController = windowManagerService.getRecentsAnimationController();
                com.android.server.wm.ActivityRecord activityRecord = windowState2.mActivityRecord;
                if ((activityRecord != null && activityRecord.isActivityTypeHomeOrRecents() && activityRecord.mTransitionController.isTransientLaunch(activityRecord)) || (recentsAnimationController != null && recentsAnimationController.shouldApplyInputConsumer(activityRecord))) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, -1459414342866553129L, 0, "Current focused window being animated by recents. Overriding back callback to recents controller callback.", null);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                if (!windowState2.isDrawn()) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, 2881085074175114605L, 0, "Focused window didn't have a valid surface drawn.", null);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                com.android.server.wm.ActivityRecord activityRecord2 = windowState2.mActivityRecord;
                com.android.server.wm.Task task2 = windowState2.getTask();
                if ((task2 != null && !task2.isVisibleRequested()) || (activityRecord2 != null && !activityRecord2.isVisibleRequested())) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, -6183551796617134986L, 0, "Focus window is closing.", null);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                android.window.OnBackInvokedCallbackInfo onBackInvokedCallbackInfo = windowState2.getOnBackInvokedCallbackInfo();
                if (onBackInvokedCallbackInfo == null) {
                    android.util.Slog.e(TAG, "No callback registered, returning null.");
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                final int i = 4;
                if (onBackInvokedCallbackInfo.isSystemCallback()) {
                    c = 65535;
                } else {
                    c = 4;
                }
                builder.setOnBackInvokedCallback(onBackInvokedCallbackInfo.getCallback());
                builder.setAnimationCallback(onBackInvokedCallbackInfo.isAnimationCallback());
                this.mNavigationMonitor.startMonitor(windowState2, remoteCallback);
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, 4039315468791789889L, 0, "startBackNavigation currentTask=%s, topRunningActivity=%s, callbackInfo=%s, currentFocus=%s", java.lang.String.valueOf(task2), java.lang.String.valueOf(activityRecord2), java.lang.String.valueOf(onBackInvokedCallbackInfo), java.lang.String.valueOf(windowState2));
                if (c != 4 && activityRecord2 != null && task2 != null && ((!activityRecord2.isActivityTypeHome() || windowState2.mAttrs.type != 1) && !activityRecord2.mHasSceneTransition)) {
                    java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList = new java.util.ArrayList<>();
                    boolean animatablePrevActivities = getAnimatablePrevActivities(task2, activityRecord2, arrayList);
                    boolean isKeyguardOccluded = isKeyguardOccluded(windowState2);
                    if (!animatablePrevActivities) {
                        windowContainer = null;
                        task = null;
                    } else if (windowState2.getParent().getChildCount() > 1 && windowState2.getParent().getChildAt(0) != windowState2) {
                        task = null;
                        i = 0;
                        windowContainer = windowState2;
                    } else if (hasTranslucentActivity(activityRecord2, arrayList)) {
                        windowContainer = null;
                        task = null;
                    } else if (arrayList.size() > 0) {
                        if (!isKeyguardOccluded || isAllActivitiesCanShowWhenLocked(arrayList)) {
                            com.android.server.wm.WindowContainer parent = activityRecord2.getParent();
                            if (parent != null && (parent.asTask() != null || (parent.asTaskFragment() != null && parent.canCustomizeAppTransition()))) {
                                if (isCustomizeExitAnimation(windowState2)) {
                                    builder.setWindowAnimations(windowState2.mAttrs.packageName, windowState2.mAttrs.windowAnimations);
                                }
                                com.android.server.wm.ActivityRecord.CustomAppTransition customAnimation = activityRecord2.getCustomAnimation(false);
                                if (customAnimation != null) {
                                    builder.setCustomAnimation(activityRecord2.packageName, customAnimation.mEnterAnim, customAnimation.mExitAnim, customAnimation.mBackgroundColor);
                                }
                            }
                            task = arrayList.get(0).getTask();
                            i = 2;
                            windowContainer = activityRecord2;
                        } else {
                            windowContainer = null;
                            task = null;
                        }
                    } else if (task2.mAtmService.getLockTaskController().isTaskLocked(task2)) {
                        windowContainer = null;
                        task = null;
                    } else {
                        com.android.server.wm.Task task3 = task2.mRootWindowContainer.getTask(new java.util.function.Predicate() { // from class: com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda6
                            @Override // java.util.function.Predicate
                            public final boolean test(java.lang.Object obj) {
                                boolean lambda$startBackNavigation$1;
                                lambda$startBackNavigation$1 = com.android.server.wm.BackNavigationController.lambda$startBackNavigation$1((com.android.server.wm.Task) obj);
                                return lambda$startBackNavigation$1;
                            }
                        }, task2, false, true);
                        com.android.server.wm.ActivityRecord topNonFinishingActivity = task3 != null ? task3.getTopNonFinishingActivity() : null;
                        if (topNonFinishingActivity != null) {
                            arrayList.add(topNonFinishingActivity);
                            findAdjacentActivityIfExist(topNonFinishingActivity, arrayList);
                        }
                        if (task3 != null && !arrayList.isEmpty() && (!isKeyguardOccluded || isAllActivitiesCanShowWhenLocked(arrayList))) {
                            if (task3.isActivityTypeHome()) {
                                this.mShowWallpaper = true;
                                task = task3;
                                i = 1;
                                windowContainer = task2;
                            } else {
                                com.android.server.wm.Task asTask = task3.getParent().asTask();
                                com.android.server.wm.Task asTask2 = task2.getParent().asTask();
                                if (task3.inMultiWindowMode() && asTask != asTask2) {
                                    task = task3;
                                    windowContainer = null;
                                } else {
                                    task = task3;
                                    i = 3;
                                    windowContainer = task3;
                                }
                            }
                        }
                        task = task3;
                        windowContainer = null;
                    }
                    builder.setType(i);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, 8456834061534378653L, 0, "Previous Destination is Activity:%s Task:%s removedContainer:%s, backType=%s", java.lang.String.valueOf(arrayList.size() > 0 ? android.text.TextUtils.join(";", arrayList.stream().map(new java.util.function.Function() { // from class: com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda7
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            android.content.ComponentName componentName;
                            componentName = ((com.android.server.wm.ActivityRecord) obj).mActivityComponent;
                            return componentName;
                        }
                    }).toArray()) : null), java.lang.String.valueOf(task != null ? task.getName() : null), java.lang.String.valueOf(windowContainer), java.lang.String.valueOf(android.window.BackNavigationInfo.typeToString(i)));
                    boolean z = (i == 1 || i == 3 || i == 2 || i == 0) && backAnimationAdapter != null;
                    if (z) {
                        com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder prepareAnimation = this.mAnimationHandler.prepareAnimation(i, backAnimationAdapter, task2, task, activityRecord2, arrayList, windowContainer);
                        this.mBackAnimationInProgress = prepareAnimation != null;
                        if (this.mBackAnimationInProgress) {
                            if (!windowContainer.hasCommittedReparentToAnimationLeash() && !windowContainer.mTransitionController.inTransition() && !this.mWindowManagerService.mSyncEngine.hasPendingSyncSets()) {
                                scheduleAnimation(prepareAnimation);
                            }
                            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, 4900967164780429209L, 0, "Pending back animation due to another animation is running", null);
                            this.mPendingAnimationBuilder = prepareAnimation;
                            for (int size = arrayList.size() - 1; size >= 0; size--) {
                                arrayList.get(size).setDeferHidingClient(true);
                            }
                        }
                    }
                    builder.setPrepareRemoteAnimation(z);
                    if (windowContainer != null) {
                        builder.setOnBackNavigationDone(new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda8
                            public final void onResult(android.os.Bundle bundle) {
                                com.android.server.wm.BackNavigationController.this.lambda$startBackNavigation$3(i, bundle);
                            }
                        }));
                    } else {
                        this.mNavigationMonitor.stopMonitorForRemote();
                    }
                    this.mLastBackType = i;
                    android.window.BackNavigationInfo build = builder.build();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return build;
                }
                builder.setType(4);
                builder.setOnBackNavigationDone(new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda5
                    public final void onResult(android.os.Bundle bundle) {
                        com.android.server.wm.BackNavigationController.this.lambda$startBackNavigation$0(bundle);
                    }
                }));
                this.mLastBackType = 4;
                android.window.BackNavigationInfo build2 = builder.build();
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return build2;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startBackNavigation$0(android.os.Bundle bundle) {
        lambda$startBackNavigation$3(bundle, 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$startBackNavigation$1(com.android.server.wm.Task task) {
        com.android.server.wm.ActivityRecord topNonFinishingActivity;
        return task.showToCurrentUser() && !task.mChildren.isEmpty() && (topNonFinishingActivity = task.getTopNonFinishingActivity()) != null && topNonFinishingActivity.showToCurrentUser();
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean getAnimatablePrevActivities(@android.annotation.NonNull com.android.server.wm.Task task, @android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList) {
        if (activityRecord.mAtmService.mTaskOrganizerController.shouldInterceptBackPressedOnRootTask(task.getRootTask())) {
            return false;
        }
        com.android.server.wm.ActivityRecord rootActivity = task.getRootActivity(false, true);
        if (rootActivity == null || !com.android.server.wm.ActivityClientController.shouldMoveTaskToBack(activityRecord, rootActivity)) {
            com.android.server.wm.ActivityRecord activity = task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getAnimatablePrevActivities$4;
                    lambda$getAnimatablePrevActivities$4 = com.android.server.wm.BackNavigationController.lambda$getAnimatablePrevActivities$4((com.android.server.wm.ActivityRecord) obj);
                    return lambda$getAnimatablePrevActivities$4;
                }
            }, activityRecord, false, true);
            com.android.server.wm.TaskFragment taskFragment = activityRecord.getTaskFragment();
            if (taskFragment != null && taskFragment.asTask() == null) {
                if (activity != null && taskFragment.hasChild(activity)) {
                    arrayList.add(activity);
                    return true;
                }
                if (taskFragment.getAdjacentTaskFragment() != null) {
                    if (taskFragment.getCompanionTaskFragment() == null) {
                        return false;
                    }
                    com.android.server.wm.WindowContainer parent = taskFragment.getParent();
                    com.android.server.wm.TaskFragment adjacentTaskFragment = taskFragment.getAdjacentTaskFragment();
                    if (parent.mChildren.indexOf(taskFragment) >= parent.mChildren.indexOf(adjacentTaskFragment)) {
                        taskFragment = adjacentTaskFragment;
                    }
                    return task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$getAnimatablePrevActivities$5;
                            lambda$getAnimatablePrevActivities$5 = com.android.server.wm.BackNavigationController.lambda$getAnimatablePrevActivities$5((com.android.server.wm.ActivityRecord) obj);
                            return lambda$getAnimatablePrevActivities$5;
                        }
                    }, taskFragment.getTopNonFinishingActivity(), false, true) == null;
                }
                if (taskFragment.getCompanionTaskFragment() != null) {
                    com.android.server.wm.ActivityRecord activity2 = task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda3
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$getAnimatablePrevActivities$7;
                            lambda$getAnimatablePrevActivities$7 = com.android.server.wm.BackNavigationController.lambda$getAnimatablePrevActivities$7((com.android.server.wm.ActivityRecord) obj);
                            return lambda$getAnimatablePrevActivities$7;
                        }
                    }, taskFragment.getCompanionTaskFragment().getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.BackNavigationController$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$getAnimatablePrevActivities$6;
                            lambda$getAnimatablePrevActivities$6 = com.android.server.wm.BackNavigationController.lambda$getAnimatablePrevActivities$6((com.android.server.wm.ActivityRecord) obj);
                            return lambda$getAnimatablePrevActivities$6;
                        }
                    }, false), false, true);
                    if (activity2 != null) {
                        arrayList.add(activity2);
                        addPreviousAdjacentActivityIfExist(activity2, arrayList);
                    }
                    return true;
                }
            }
            if (activity == null) {
                return true;
            }
            addPreviousAdjacentActivityIfExist(activity, arrayList);
            arrayList.add(activity);
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAnimatablePrevActivities$4(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.finishing;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAnimatablePrevActivities$5(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.finishing;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAnimatablePrevActivities$6(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.finishing;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAnimatablePrevActivities$7(com.android.server.wm.ActivityRecord activityRecord) {
        return !activityRecord.finishing;
    }

    private static void addPreviousAdjacentActivityIfExist(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList) {
        com.android.server.wm.TaskFragment adjacentTaskFragment;
        com.android.server.wm.ActivityRecord topNonFinishingActivity;
        com.android.server.wm.TaskFragment taskFragment = activityRecord.getTaskFragment();
        if (taskFragment != null && taskFragment.asTask() == null && (adjacentTaskFragment = taskFragment.getAdjacentTaskFragment()) != null && adjacentTaskFragment.asTask() == null && (topNonFinishingActivity = adjacentTaskFragment.getTopNonFinishingActivity()) != null) {
            arrayList.add(topNonFinishingActivity);
        }
    }

    private static void findAdjacentActivityIfExist(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList) {
        com.android.server.wm.ActivityRecord topNonFinishingActivity;
        com.android.server.wm.TaskFragment taskFragment = activityRecord.getTaskFragment();
        if (taskFragment == null || taskFragment.getAdjacentTaskFragment() == null || (topNonFinishingActivity = taskFragment.getAdjacentTaskFragment().getTopNonFinishingActivity()) == null) {
            return;
        }
        arrayList.add(topNonFinishingActivity);
    }

    private static boolean hasTranslucentActivity(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList) {
        if (!activityRecord.occludesParent() || activityRecord.showWallpaper()) {
            return true;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord activityRecord2 = arrayList.get(size);
            if (!activityRecord2.occludesParent() || activityRecord2.showWallpaper()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllActivitiesCanShowWhenLocked(@android.annotation.NonNull java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (!arrayList.get(size).canShowWhenLocked()) {
                return false;
            }
        }
        return !arrayList.isEmpty();
    }

    boolean isMonitoringTransition() {
        return this.mAnimationHandler.mComposed || this.mNavigationMonitor.isMonitorForRemote();
    }

    private void scheduleAnimation(@android.annotation.NonNull com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder scheduleAnimationBuilder) {
        this.mPendingAnimation = scheduleAnimationBuilder.build();
        this.mWindowManagerService.mWindowPlacerLocked.requestTraversal();
        if (this.mShowWallpaper) {
            this.mWindowManagerService.getDefaultDisplayContentLocked().mWallpaperController.adjustWallpaperWindows();
        }
    }

    private boolean isWaitBackTransition() {
        return this.mAnimationHandler.mComposed && this.mAnimationHandler.mWaitTransition;
    }

    boolean isKeyguardOccluded(com.android.server.wm.WindowState windowState) {
        return this.mWindowManagerService.mAtmService.mKeyguardController.isKeyguardOccluded(windowState.getDisplayId());
    }

    private static boolean isCustomizeExitAnimation(com.android.server.wm.WindowState windowState) {
        if (!java.util.Objects.equals(windowState.mAttrs.packageName, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME) && windowState.mAttrs.windowAnimations != 0) {
            com.android.internal.policy.TransitionAnimation transitionAnimation = windowState.getDisplayContent().mAppTransition.mTransitionAnimation;
            int animationResId = transitionAnimation.getAnimationResId(windowState.mAttrs, 7, 0);
            if (android.content.res.ResourceId.isValid(animationResId)) {
                if (sDefaultAnimationResId == 0) {
                    sDefaultAnimationResId = transitionAnimation.getDefaultAnimationResId(7, 0);
                }
                return sDefaultAnimationResId != animationResId;
            }
        }
        return false;
    }

    boolean removeIfContainsBackAnimationTargets(android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet, android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet2) {
        if (!isMonitoringTransition()) {
            return false;
        }
        this.mTmpCloseApps.addAll(arraySet2);
        boolean removeIfWaitForBackTransition = removeIfWaitForBackTransition(arraySet, arraySet2);
        if (!removeIfWaitForBackTransition) {
            this.mNavigationMonitor.onTransitionReadyWhileNavigate(this.mTmpOpenApps, this.mTmpCloseApps);
        }
        this.mTmpCloseApps.clear();
        return removeIfWaitForBackTransition;
    }

    boolean removeIfWaitForBackTransition(android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet, android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet2) {
        if (!isWaitBackTransition() || !this.mAnimationHandler.containsBackAnimationTargets(this.mTmpOpenApps, this.mTmpCloseApps)) {
            return false;
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            if (this.mAnimationHandler.isTarget(arraySet.valueAt(size), true)) {
                arraySet.removeAt(size);
                this.mAnimationHandler.markStartingSurfaceMatch(null);
            }
        }
        for (int size2 = arraySet2.size() - 1; size2 >= 0; size2--) {
            if (this.mAnimationHandler.isTarget(arraySet2.valueAt(size2), false)) {
                arraySet2.removeAt(size2);
            }
        }
        return true;
    }

    void removePredictiveSurfaceIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        this.mAnimationHandler.markWindowHasDrawn(activityRecord);
    }

    private class NavigationMonitor {
        private com.android.server.wm.WindowState mNavigatingWindow;
        private android.os.RemoteCallback mObserver;

        private NavigationMonitor() {
        }

        void startMonitor(@android.annotation.NonNull com.android.server.wm.WindowState windowState, @android.annotation.NonNull android.os.RemoteCallback remoteCallback) {
            this.mNavigatingWindow = windowState;
            this.mObserver = remoteCallback;
        }

        void stopMonitorForRemote() {
            this.mObserver = null;
        }

        void stopMonitorTransition() {
            this.mNavigatingWindow = null;
        }

        boolean isMonitorForRemote() {
            return (this.mNavigatingWindow == null || this.mObserver == null) ? false : true;
        }

        boolean isMonitorAnimationOrTransition() {
            return this.mNavigatingWindow != null && (com.android.server.wm.BackNavigationController.this.mAnimationHandler.mComposed || com.android.server.wm.BackNavigationController.this.mAnimationHandler.mWaitTransition);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onFocusWindowChanged(com.android.server.wm.WindowState windowState) {
            if (atSameDisplay(windowState)) {
                if ((!isMonitorForRemote() && !isMonitorAnimationOrTransition()) || windowState == null || windowState == this.mNavigatingWindow) {
                    return;
                }
                if (windowState.mActivityRecord == null || windowState.mActivityRecord == this.mNavigatingWindow.mActivityRecord) {
                    cancelBackNavigating("focusWindowChanged");
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onTransitionReadyWhileNavigate(java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList, java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList2) {
            if (!isMonitorForRemote() && !isMonitorAnimationOrTransition()) {
                return;
            }
            java.util.ArrayList arrayList3 = new java.util.ArrayList(arrayList);
            arrayList3.addAll(arrayList2);
            for (int size = arrayList3.size() - 1; size >= 0; size--) {
                if (((com.android.server.wm.WindowContainer) arrayList3.get(size)).hasChild(this.mNavigatingWindow)) {
                    cancelBackNavigating("transitionHappens");
                    return;
                }
            }
        }

        private boolean atSameDisplay(com.android.server.wm.WindowState windowState) {
            if (this.mNavigatingWindow == null) {
                return false;
            }
            return windowState == null || windowState.getDisplayId() == this.mNavigatingWindow.getDisplayId();
        }

        private void cancelBackNavigating(java.lang.String str) {
            com.android.server.wm.EventLogTags.writeWmBackNaviCanceled(str);
            if (isMonitorForRemote()) {
                this.mObserver.sendResult((android.os.Bundle) null);
            }
            if (isMonitorAnimationOrTransition()) {
                com.android.server.wm.BackNavigationController.this.clearBackAnimations();
            }
            com.android.server.wm.BackNavigationController.this.cancelPendingAnimation();
        }
    }

    void onTransactionReady(com.android.server.wm.Transition transition, java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList, android.view.SurfaceControl.Transaction transaction) {
        if (!isMonitoringTransition() || arrayList.isEmpty()) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer windowContainer = arrayList.get(size).mContainer;
            if (windowContainer.asActivityRecord() != null || windowContainer.asTask() != null || windowContainer.asTaskFragment() != null) {
                if (windowContainer.isVisibleRequested()) {
                    this.mTmpOpenApps.add(windowContainer);
                } else {
                    this.mTmpCloseApps.add(windowContainer);
                }
            }
        }
        boolean z = isWaitBackTransition() && (transition.mType == 2 || transition.mType == 4) && this.mAnimationHandler.containsBackAnimationTargets(this.mTmpOpenApps, this.mTmpCloseApps);
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, -6431452312492819825L, 192, "onTransactionReady, opening: %s, closing: %s, animating: %s, match: %b", java.lang.String.valueOf(this.mTmpOpenApps), java.lang.String.valueOf(this.mTmpCloseApps), java.lang.String.valueOf(this.mAnimationHandler), java.lang.Boolean.valueOf(z));
        if (!z) {
            this.mNavigationMonitor.onTransitionReadyWhileNavigate(this.mTmpOpenApps, this.mTmpCloseApps);
        } else {
            if (this.mWaitTransitionFinish != null) {
                android.util.Slog.e(TAG, "Gesture animation is applied on another transition?");
            }
            this.mWaitTransitionFinish = transition;
            int size2 = this.mTmpOpenApps.size() - 1;
            while (true) {
                if (size2 < 0) {
                    break;
                }
                if (this.mAnimationHandler.isTarget(this.mTmpOpenApps.get(size2), true)) {
                    this.mAnimationHandler.markStartingSurfaceMatch(transaction);
                    break;
                }
                size2--;
            }
            if (this.mAnimationHandler.mOpenAnimAdaptor.mCloseTransaction != null) {
                transaction.merge(this.mAnimationHandler.mOpenAnimAdaptor.mCloseTransaction);
                this.mAnimationHandler.mOpenAnimAdaptor.mCloseTransaction = null;
            }
            transaction.hide(this.mAnimationHandler.mCloseAdaptor.mTarget.getSurfaceControl());
        }
        this.mTmpOpenApps.clear();
        this.mTmpCloseApps.clear();
    }

    boolean isMonitorTransitionTarget(com.android.server.wm.WindowContainer windowContainer) {
        if (!isWaitBackTransition() || this.mWaitTransitionFinish == null) {
            return false;
        }
        return this.mAnimationHandler.isTarget(windowContainer, windowContainer.isVisibleRequested());
    }

    void clearBackAnimations() {
        this.mAnimationHandler.clearBackAnimateTarget();
        this.mNavigationMonitor.stopMonitorTransition();
        this.mWaitTransitionFinish = null;
    }

    boolean onTransitionFinish(java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList, @android.annotation.NonNull com.android.server.wm.Transition transition) {
        boolean z;
        if (transition == this.mWaitTransitionFinish) {
            clearBackAnimations();
        }
        if (!this.mBackAnimationInProgress || this.mPendingAnimationBuilder == null) {
            return false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, -4051770154814262074L, 0, "Handling the deferred animation after transition finished", null);
        int i = 0;
        while (true) {
            if (i >= transition.mParticipants.size()) {
                z = false;
                break;
            }
            com.android.server.wm.WindowContainer valueAt = transition.mParticipants.valueAt(i);
            if ((valueAt.asActivityRecord() == null && valueAt.asTask() == null && valueAt.asTaskFragment() == null) || !this.mPendingAnimationBuilder.containTarget(valueAt)) {
                i++;
            } else {
                z = true;
                break;
            }
        }
        if (!z) {
            android.util.Slog.w(TAG, "Finished transition didn't include the targets open: " + java.util.Arrays.toString(this.mPendingAnimationBuilder.mOpenTargets) + " close: " + this.mPendingAnimationBuilder.mCloseTarget);
            cancelPendingAnimation();
            return false;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList.get(i2).mContainer.prepareSurfaces();
        }
        scheduleAnimation(this.mPendingAnimationBuilder);
        this.mPendingAnimationBuilder = null;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelPendingAnimation() {
        if (this.mPendingAnimationBuilder == null) {
            return;
        }
        try {
            this.mPendingAnimationBuilder.mBackAnimationAdapter.getRunner().onAnimationCancelled();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote animation gone", e);
        }
        this.mPendingAnimationBuilder = null;
    }

    static class AnimationHandler {
        private static final int ACTIVITY_SWITCH = 2;
        private static final int DIALOG_CLOSE = 3;
        private static final int TASK_SWITCH = 1;
        private static final int UNKNOWN = 0;
        private com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptor mCloseAdaptor;
        private boolean mComposed;
        private com.android.server.wm.ActivityRecord[] mOpenActivities;
        private com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptorWrapper mOpenAnimAdaptor;
        private final boolean mShowWindowlessSurface;
        private boolean mStartingSurfaceTargetMatch;
        private int mSwitchType = 0;
        private boolean mWaitTransition;
        private final com.android.server.wm.WindowManagerService mWindowManagerService;

        AnimationHandler(com.android.server.wm.WindowManagerService windowManagerService) {
            boolean z = false;
            this.mWindowManagerService = windowManagerService;
            if (windowManagerService.mContext.getResources().getBoolean(android.R.bool.config_pinnerAssistantApp) && com.android.window.flags.Flags.activitySnapshotByDefault()) {
                z = true;
            }
            this.mShowWindowlessSurface = z;
        }

        private static boolean isActivitySwitch(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull com.android.server.wm.WindowContainer[] windowContainerArr) {
            if (windowContainerArr == null || windowContainerArr.length == 0 || windowContainer.asActivityRecord() == null) {
                return false;
            }
            com.android.server.wm.Task task = windowContainer.asActivityRecord().getTask();
            for (int length = windowContainerArr.length - 1; length >= 0; length--) {
                if (windowContainerArr[length].asActivityRecord() == null || task != windowContainerArr[length].asActivityRecord().getTask()) {
                    return false;
                }
            }
            return true;
        }

        private static boolean isTaskSwitch(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull com.android.server.wm.WindowContainer[] windowContainerArr) {
            return (windowContainerArr == null || windowContainerArr.length != 1 || windowContainer.asTask() == null || windowContainerArr[0].asTask() == null || windowContainer.asTask() == windowContainerArr[0].asTask()) ? false : true;
        }

        private static boolean isDialogClose(com.android.server.wm.WindowContainer windowContainer) {
            return windowContainer.asWindowState() != null;
        }

        private void initiate(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull com.android.server.wm.WindowContainer[] windowContainerArr, @android.annotation.NonNull com.android.server.wm.ActivityRecord[] activityRecordArr) {
            if (isActivitySwitch(windowContainer, windowContainerArr)) {
                this.mSwitchType = 2;
            } else if (isTaskSwitch(windowContainer, windowContainerArr)) {
                this.mSwitchType = 1;
            } else if (isDialogClose(windowContainer)) {
                this.mSwitchType = 3;
            } else {
                this.mSwitchType = 0;
                return;
            }
            this.mCloseAdaptor = createAdaptor(windowContainer, false, this.mSwitchType);
            if (this.mCloseAdaptor.mAnimationTarget == null) {
                android.util.Slog.w(com.android.server.wm.BackNavigationController.TAG, "composeNewAnimations fail, skip");
                clearBackAnimateTarget();
                return;
            }
            if (activityRecordArr.length == 1) {
                com.android.server.wm.ActivityRecord activityRecord = activityRecordArr[0];
                com.android.server.wm.DisplayContent displayContent = activityRecord.mDisplayContent;
                displayContent.rotateInDifferentOrientationIfNeeded(activityRecord);
                if (activityRecord.hasFixedRotationTransform()) {
                    displayContent.setFixedRotationLaunchingApp(activityRecord, activityRecord.getWindowConfiguration().getRotation());
                }
            }
            this.mOpenAnimAdaptor = new com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptorWrapper(true, this.mSwitchType, windowContainerArr);
            if (!this.mOpenAnimAdaptor.isValid()) {
                android.util.Slog.w(com.android.server.wm.BackNavigationController.TAG, "compose animations fail, skip");
                clearBackAnimateTarget();
            } else {
                this.mOpenActivities = activityRecordArr;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean composeAnimations(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull com.android.server.wm.WindowContainer[] windowContainerArr, @android.annotation.NonNull com.android.server.wm.ActivityRecord[] activityRecordArr) {
            if (this.mComposed || this.mWaitTransition) {
                android.util.Slog.e(com.android.server.wm.BackNavigationController.TAG, "Previous animation is running " + this);
                return false;
            }
            clearBackAnimateTarget();
            if (windowContainer == null || windowContainerArr == null || windowContainerArr.length == 0 || windowContainerArr.length > 2) {
                android.util.Slog.e(com.android.server.wm.BackNavigationController.TAG, "reset animation with null target close: " + windowContainer + " open: " + java.util.Arrays.toString(windowContainerArr));
                return false;
            }
            initiate(windowContainer, windowContainerArr, activityRecordArr);
            if (this.mSwitchType == 0) {
                return false;
            }
            this.mComposed = true;
            this.mWaitTransition = false;
            return true;
        }

        @android.annotation.Nullable
        android.view.RemoteAnimationTarget[] getAnimationTargets() {
            if (!this.mComposed) {
                return null;
            }
            return new android.view.RemoteAnimationTarget[]{this.mCloseAdaptor.mAnimationTarget, this.mOpenAnimAdaptor.mRemoteAnimationTarget};
        }

        boolean isSupportWindowlessSurface() {
            return this.mWindowManagerService.mAtmService.mTaskOrganizerController.isSupportWindowlessStartingSurface();
        }

        boolean containTarget(@android.annotation.NonNull java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList, boolean z) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (isTarget(arrayList.get(size), z)) {
                    return true;
                }
            }
            return arrayList.isEmpty();
        }

        boolean isTarget(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, boolean z) {
            if (!this.mComposed) {
                return false;
            }
            if (z) {
                for (int length = this.mOpenAnimAdaptor.mAdaptors.length - 1; length >= 0; length--) {
                    if (isAnimateTarget(windowContainer, this.mOpenAnimAdaptor.mAdaptors[length].mTarget, this.mSwitchType)) {
                        return true;
                    }
                }
                return false;
            }
            return isAnimateTarget(windowContainer, this.mCloseAdaptor.mTarget, this.mSwitchType);
        }

        void markWindowHasDrawn(com.android.server.wm.ActivityRecord activityRecord) {
            if (!this.mComposed || this.mWaitTransition) {
                return;
            }
            boolean z = true;
            for (int length = this.mOpenAnimAdaptor.mAdaptors.length - 1; length >= 0; length--) {
                com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptor backWindowAnimationAdaptor = this.mOpenAnimAdaptor.mAdaptors[length];
                if (isAnimateTarget(activityRecord, backWindowAnimationAdaptor.mTarget, this.mSwitchType)) {
                    backWindowAnimationAdaptor.mAppWindowDrawn = true;
                }
                z &= backWindowAnimationAdaptor.mAppWindowDrawn;
            }
            if (z) {
                this.mOpenAnimAdaptor.cleanUpWindowlessSurface(true);
            }
        }

        private static boolean isAnimateTarget(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer2, int i) {
            if (i == 1) {
                if (windowContainer.isActivityTypeHome() && windowContainer2.isActivityTypeHome()) {
                    return true;
                }
                return windowContainer == windowContainer2 || (windowContainer2.asTask() != null && windowContainer2.hasChild(windowContainer)) || (windowContainer2.asActivityRecord() != null && windowContainer.hasChild(windowContainer2));
            }
            if (i == 2) {
                return windowContainer == windowContainer2 || (windowContainer.asTaskFragment() != null && windowContainer.hasChild(windowContainer2));
            }
            return false;
        }

        void finishPresentAnimations() {
            if (!this.mComposed) {
                return;
            }
            if (this.mCloseAdaptor != null) {
                this.mCloseAdaptor.mTarget.cancelAnimation();
                this.mCloseAdaptor = null;
            }
            if (this.mOpenAnimAdaptor != null) {
                this.mOpenAnimAdaptor.cleanUp(this.mStartingSurfaceTargetMatch);
                this.mOpenAnimAdaptor = null;
            }
            if (this.mOpenActivities != null) {
                for (int length = this.mOpenActivities.length - 1; length >= 0; length--) {
                    com.android.server.wm.ActivityRecord activityRecord = this.mOpenActivities[length];
                    if (activityRecord.mLaunchTaskBehind) {
                        com.android.server.wm.BackNavigationController.restoreLaunchBehind(activityRecord);
                    }
                }
            }
        }

        void markStartingSurfaceMatch(android.view.SurfaceControl.Transaction transaction) {
            if (this.mStartingSurfaceTargetMatch) {
                return;
            }
            this.mStartingSurfaceTargetMatch = true;
            this.mOpenAnimAdaptor.reparentWindowlessSurfaceToTarget(transaction);
        }

        void clearBackAnimateTarget() {
            finishPresentAnimations();
            this.mComposed = false;
            this.mWaitTransition = false;
            this.mStartingSurfaceTargetMatch = false;
            this.mSwitchType = 0;
            this.mOpenActivities = null;
        }

        boolean containsBackAnimationTargets(@android.annotation.NonNull java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList, @android.annotation.NonNull java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList2) {
            if (!containTarget(arrayList2, false)) {
                return false;
            }
            if (!containTarget(arrayList, true) && !containTarget(arrayList, false)) {
                return false;
            }
            return true;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("AnimationTargets{ openTarget= ");
            sb.append(this.mOpenAnimAdaptor != null ? dumpOpenAnimTargetsToString() : null);
            sb.append(" closeTarget= ");
            sb.append(this.mCloseAdaptor != null ? this.mCloseAdaptor.mTarget : null);
            sb.append(" mSwitchType= ");
            sb.append(this.mSwitchType);
            sb.append(" mComposed= ");
            sb.append(this.mComposed);
            sb.append(" mWaitTransition= ");
            sb.append(this.mWaitTransition);
            sb.append('}');
            return sb.toString();
        }

        private java.lang.String dumpOpenAnimTargetsToString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("{");
            for (int i = 0; i < this.mOpenAnimAdaptor.mAdaptors.length; i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(this.mOpenAnimAdaptor.mAdaptors[i].mTarget);
            }
            sb.append("}");
            return sb.toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        public static com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptor createAdaptor(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, boolean z, int i) {
            com.android.server.wm.TaskFragment taskFragment;
            com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptor backWindowAnimationAdaptor = new com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptor(windowContainer, z, i);
            android.view.SurfaceControl.Transaction pendingTransaction = windowContainer.getPendingTransaction();
            if (z && windowContainer.asActivityRecord() != null && (taskFragment = windowContainer.asActivityRecord().getTaskFragment()) != null) {
                taskFragment.updateOrganizedTaskFragmentSurface();
                pendingTransaction.show(taskFragment.mSurfaceControl);
            }
            windowContainer.startAnimation(pendingTransaction, backWindowAnimationAdaptor, false, 256);
            return backWindowAnimationAdaptor;
        }

        private static class BackWindowAnimationAdaptorWrapper {
            final com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptor[] mAdaptors;
            android.view.SurfaceControl.Transaction mCloseTransaction;
            final android.view.RemoteAnimationTarget mRemoteAnimationTarget;
            private int mRequestedStartingSurfaceId = -1;
            private android.view.SurfaceControl mStartingSurface;

            BackWindowAnimationAdaptorWrapper(boolean z, int i, @android.annotation.NonNull com.android.server.wm.WindowContainer... windowContainerArr) {
                this.mAdaptors = new com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptor[windowContainerArr.length];
                for (int length = windowContainerArr.length - 1; length >= 0; length--) {
                    this.mAdaptors[length] = com.android.server.wm.BackNavigationController.AnimationHandler.createAdaptor(windowContainerArr[length], z, i);
                }
                this.mRemoteAnimationTarget = windowContainerArr.length > 1 ? createWrapTarget() : this.mAdaptors[0].mAnimationTarget;
            }

            boolean isValid() {
                for (int length = this.mAdaptors.length - 1; length >= 0; length--) {
                    if (this.mAdaptors[length].mAnimationTarget == null) {
                        return false;
                    }
                }
                return true;
            }

            void cleanUp(boolean z) {
                cleanUpWindowlessSurface(z);
                for (int length = this.mAdaptors.length - 1; length >= 0; length--) {
                    this.mAdaptors[length].mTarget.cancelAnimation();
                }
                if (this.mCloseTransaction != null) {
                    this.mCloseTransaction.apply();
                    this.mCloseTransaction = null;
                }
            }

            private android.view.RemoteAnimationTarget createWrapTarget() {
                android.graphics.Rect rect = new android.graphics.Rect();
                for (int length = this.mAdaptors.length - 1; length >= 0; length--) {
                    rect.union(this.mAdaptors[length].mAnimationTarget.localBounds);
                }
                com.android.server.wm.WindowContainer windowContainer = this.mAdaptors[0].mTarget;
                com.android.server.wm.Task task = windowContainer.asActivityRecord() != null ? windowContainer.asActivityRecord().getTask() : windowContainer.asTask();
                android.view.RemoteAnimationTarget remoteAnimationTarget = this.mAdaptors[0].mAnimationTarget;
                android.view.SurfaceControl build = new android.view.SurfaceControl.Builder().setName("cross-animation-leash").setContainerLayer().setHidden(false).setParent(task.getSurfaceControl()).build();
                this.mCloseTransaction = new android.view.SurfaceControl.Transaction();
                this.mCloseTransaction.reparent(build, null);
                android.view.SurfaceControl.Transaction pendingTransaction = windowContainer.getPendingTransaction();
                pendingTransaction.setLayer(build, windowContainer.getParent().getLastLayer());
                for (int length2 = this.mAdaptors.length - 1; length2 >= 0; length2--) {
                    com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptor backWindowAnimationAdaptor = this.mAdaptors[length2];
                    pendingTransaction.reparent(backWindowAnimationAdaptor.mAnimationTarget.leash, build);
                    pendingTransaction.setPosition(backWindowAnimationAdaptor.mAnimationTarget.leash, backWindowAnimationAdaptor.mAnimationTarget.localBounds.left, backWindowAnimationAdaptor.mAnimationTarget.localBounds.top);
                    com.android.server.wm.WindowContainer parent = backWindowAnimationAdaptor.mTarget.getParent();
                    if (parent != null) {
                        this.mCloseTransaction.reparent(backWindowAnimationAdaptor.mTarget.getSurfaceControl(), parent.getSurfaceControl());
                    }
                }
                return new android.view.RemoteAnimationTarget(remoteAnimationTarget.taskId, remoteAnimationTarget.mode, build, remoteAnimationTarget.isTranslucent, remoteAnimationTarget.clipRect, remoteAnimationTarget.contentInsets, remoteAnimationTarget.prefixOrderIndex, new android.graphics.Point(rect.left, rect.top), rect, rect, remoteAnimationTarget.windowConfiguration, true, (android.view.SurfaceControl) null, (android.graphics.Rect) null, remoteAnimationTarget.taskInfo, remoteAnimationTarget.allowEnterPip);
            }

            void createStartingSurface(@android.annotation.Nullable android.window.TaskSnapshot taskSnapshot) {
                final com.android.server.wm.Task task;
                com.android.server.wm.ActivityRecord topNonFinishingActivity;
                if (this.mAdaptors[0].mSwitchType == 3) {
                    return;
                }
                com.android.server.wm.WindowContainer windowContainer = this.mAdaptors[0].mTarget;
                int i = this.mAdaptors[0].mSwitchType;
                if (i == 1) {
                    task = windowContainer.asTask();
                } else {
                    task = i == 2 ? windowContainer.asActivityRecord().getTask() : null;
                }
                if (task == null) {
                    return;
                }
                if (i == 2) {
                    topNonFinishingActivity = windowContainer.asActivityRecord();
                } else {
                    topNonFinishingActivity = task.getTopNonFinishingActivity();
                }
                if (topNonFinishingActivity == null) {
                    return;
                }
                this.mRequestedStartingSurfaceId = task.mAtmService.mTaskOrganizerController.addWindowlessStartingSurface(task, topNonFinishingActivity, this.mAdaptors.length == 1 ? topNonFinishingActivity.getSurfaceControl() : this.mRemoteAnimationTarget.leash, taskSnapshot, this.mAdaptors.length == 1 ? topNonFinishingActivity.getConfiguration() : task.getConfiguration(), new android.window.IWindowlessStartingSurfaceCallback.Stub() { // from class: com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptorWrapper.1
                    public void onSurfaceAdded(android.view.SurfaceControl surfaceControl) {
                        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = task.mWmService.mGlobalLock;
                        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                if (com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptorWrapper.this.mRequestedStartingSurfaceId != -1) {
                                    com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptorWrapper.this.mStartingSurface = surfaceControl;
                                }
                            } catch (java.lang.Throwable th) {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    }
                });
            }

            void reparentWindowlessSurfaceToTarget(android.view.SurfaceControl.Transaction transaction) {
                android.view.SurfaceControl surfaceControl;
                if (this.mRequestedStartingSurfaceId != -1 && this.mStartingSurface != null && this.mStartingSurface.isValid()) {
                    if (transaction == null) {
                        transaction = this.mAdaptors[0].mTarget.getPendingTransaction();
                    }
                    if (this.mAdaptors.length != 1) {
                        com.android.server.wm.WindowContainer windowContainer = this.mAdaptors[0].mTarget;
                        com.android.server.wm.Task task = windowContainer.asActivityRecord() != null ? windowContainer.asActivityRecord().getTask() : windowContainer.asTask();
                        android.view.SurfaceControl surfaceControl2 = this.mStartingSurface;
                        if (task == null) {
                            surfaceControl = this.mAdaptors[0].mTarget.getSurfaceControl();
                        } else {
                            surfaceControl = task.getSurfaceControl();
                        }
                        transaction.reparent(surfaceControl2, surfaceControl);
                    }
                    this.mStartingSurface = null;
                }
            }

            void cleanUpWindowlessSurface(boolean z) {
                if (this.mRequestedStartingSurfaceId == -1) {
                    return;
                }
                this.mAdaptors[0].mTarget.mWmService.mAtmService.mTaskOrganizerController.removeWindowlessStartingSurface(this.mRequestedStartingSurfaceId, !z);
                this.mRequestedStartingSurfaceId = -1;
                this.mStartingSurface = null;
            }
        }

        private static class BackWindowAnimationAdaptor implements com.android.server.wm.AnimationAdapter {
            private android.view.RemoteAnimationTarget mAnimationTarget;
            boolean mAppWindowDrawn;
            private final android.graphics.Rect mBounds = new android.graphics.Rect();
            android.view.SurfaceControl mCapturedLeash;
            private final boolean mIsOpen;
            private final int mSwitchType;
            private final com.android.server.wm.WindowContainer mTarget;

            BackWindowAnimationAdaptor(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, boolean z, int i) {
                this.mBounds.set(windowContainer.getBounds());
                this.mTarget = windowContainer;
                this.mIsOpen = z;
                this.mSwitchType = i;
            }

            @Override // com.android.server.wm.AnimationAdapter
            public boolean getShowWallpaper() {
                return false;
            }

            @Override // com.android.server.wm.AnimationAdapter
            public void startAnimation(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, int i, com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
                this.mCapturedLeash = surfaceControl;
                createRemoteAnimationTarget();
                com.android.server.wm.WindowState asWindowState = this.mTarget.asWindowState();
                if (asWindowState != null && this.mSwitchType == 3) {
                    android.graphics.Rect frame = asWindowState.getFrame();
                    asWindowState.transformFrameToSurfacePosition(frame.left, frame.top, new android.graphics.Point());
                    transaction.setPosition(this.mCapturedLeash, r5.x, r5.y);
                }
            }

            @Override // com.android.server.wm.AnimationAdapter
            public void onAnimationCancelled(android.view.SurfaceControl surfaceControl) {
                if (this.mCapturedLeash == surfaceControl) {
                    this.mCapturedLeash = null;
                }
            }

            @Override // com.android.server.wm.AnimationAdapter
            public long getDurationHint() {
                return 0L;
            }

            @Override // com.android.server.wm.AnimationAdapter
            public long getStatusBarTransitionsStartTime() {
                return 0L;
            }

            @Override // com.android.server.wm.AnimationAdapter
            public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
                printWriter.print(str + "BackWindowAnimationAdaptor mCapturedLeash=");
                printWriter.print(this.mCapturedLeash);
                printWriter.println();
            }

            @Override // com.android.server.wm.AnimationAdapter
            public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream) {
            }

            android.view.RemoteAnimationTarget createRemoteAnimationTarget() {
                android.graphics.Rect rect;
                if (this.mAnimationTarget != null) {
                    return this.mAnimationTarget;
                }
                com.android.server.wm.WindowState asWindowState = this.mTarget.asWindowState();
                com.android.server.wm.ActivityRecord activityRecord = asWindowState != null ? asWindowState.getActivityRecord() : null;
                com.android.server.wm.Task task = activityRecord != null ? activityRecord.getTask() : this.mTarget.asTask();
                if (task == null && this.mTarget.asTaskFragment() != null) {
                    task = this.mTarget.asTaskFragment().getTask();
                    activityRecord = this.mTarget.asTaskFragment().getTopNonFinishingActivity();
                }
                if (activityRecord == null) {
                    activityRecord = task != null ? task.getTopNonFinishingActivity() : this.mTarget.asActivityRecord();
                }
                if (task == null && activityRecord != null) {
                    task = activityRecord.getTask();
                }
                if (task == null || activityRecord == null) {
                    android.util.Slog.e(com.android.server.wm.BackNavigationController.TAG, "createRemoteAnimationTarget fail " + this.mTarget);
                    return null;
                }
                com.android.server.wm.WindowState findMainWindow = activityRecord.findMainWindow();
                if (findMainWindow != null) {
                    android.graphics.Rect rect2 = findMainWindow.getInsetsStateWithVisibilityOverride().calculateInsets(this.mBounds, android.view.WindowInsets.Type.tappableElement(), false).toRect();
                    com.android.server.wm.utils.InsetUtils.addInsets(rect2, findMainWindow.mActivityRecord.getLetterboxInsets());
                    rect = rect2;
                } else {
                    rect = new android.graphics.Rect();
                }
                this.mAnimationTarget = new android.view.RemoteAnimationTarget(task.mTaskId, !this.mIsOpen ? 1 : 0, this.mCapturedLeash, !activityRecord.fillsParent(), new android.graphics.Rect(), rect, activityRecord.getPrefixOrderIndex(), new android.graphics.Point(this.mBounds.left, this.mBounds.top), this.mBounds, this.mBounds, task.getWindowConfiguration(), true, (android.view.SurfaceControl) null, (android.graphics.Rect) null, task.getTaskInfo(), activityRecord.checkEnterPictureInPictureAppOpsState());
                return this.mAnimationTarget;
            }
        }

        com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder prepareAnimation(int i, android.window.BackAnimationAdapter backAnimationAdapter, com.android.server.wm.Task task, com.android.server.wm.Task task2, com.android.server.wm.ActivityRecord activityRecord, java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList, com.android.server.wm.WindowContainer windowContainer) {
            switch (i) {
                case 0:
                    return new com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder(i, backAnimationAdapter).setComposeTarget(windowContainer, activityRecord).setIsLaunchBehind(false);
                case 1:
                    return new com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder(i, backAnimationAdapter).setIsLaunchBehind(true).setComposeTarget(task, task2);
                case 2:
                    return new com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder(i, backAnimationAdapter).setComposeTarget(activityRecord, (com.android.server.wm.ActivityRecord[]) arrayList.toArray(new com.android.server.wm.ActivityRecord[arrayList.size()])).setIsLaunchBehind(false);
                case 3:
                    return new com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder(i, backAnimationAdapter).setComposeTarget(task, task2).setIsLaunchBehind(false);
                default:
                    return null;
            }
        }

        class ScheduleAnimationBuilder {
            final android.window.BackAnimationAdapter mBackAnimationAdapter;
            com.android.server.wm.WindowContainer mCloseTarget;
            boolean mIsLaunchBehind;
            com.android.server.wm.WindowContainer[] mOpenTargets;
            final int mType;

            ScheduleAnimationBuilder(int i, android.window.BackAnimationAdapter backAnimationAdapter) {
                this.mType = i;
                this.mBackAnimationAdapter = backAnimationAdapter;
            }

            com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder setComposeTarget(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull com.android.server.wm.WindowContainer... windowContainerArr) {
                this.mCloseTarget = windowContainer;
                this.mOpenTargets = windowContainerArr;
                return this;
            }

            com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder setIsLaunchBehind(boolean z) {
                this.mIsLaunchBehind = z;
                return this;
            }

            boolean containTarget(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
                if (this.mOpenTargets != null) {
                    for (int length = this.mOpenTargets.length - 1; length >= 0; length--) {
                        if (windowContainer == this.mOpenTargets[length] || this.mOpenTargets[length].hasChild(windowContainer)) {
                            return true;
                        }
                    }
                }
                return windowContainer == this.mCloseTarget || this.mCloseTarget.hasChild(windowContainer);
            }

            private void applyPreviewStrategy(@android.annotation.NonNull com.android.server.wm.BackNavigationController.AnimationHandler.BackWindowAnimationAdaptorWrapper backWindowAnimationAdaptorWrapper, @android.annotation.NonNull com.android.server.wm.ActivityRecord[] activityRecordArr) {
                boolean z;
                if (com.android.server.wm.BackNavigationController.AnimationHandler.this.isSupportWindowlessSurface() && com.android.server.wm.BackNavigationController.AnimationHandler.this.mShowWindowlessSurface && !this.mIsLaunchBehind) {
                    z = false;
                    android.window.TaskSnapshot snapshot = com.android.server.wm.BackNavigationController.getSnapshot(backWindowAnimationAdaptorWrapper.mAdaptors[0].mTarget, activityRecordArr);
                    backWindowAnimationAdaptorWrapper.createStartingSurface(snapshot);
                    if (snapshot == null && backWindowAnimationAdaptorWrapper.mRequestedStartingSurfaceId != -1) {
                        z = true;
                    }
                } else {
                    z = true;
                }
                if (z) {
                    for (int length = activityRecordArr.length - 1; length >= 0; length--) {
                        com.android.server.wm.BackNavigationController.setLaunchBehind(activityRecordArr[length]);
                    }
                }
                if (com.android.server.wm.BackNavigationController.AnimationHandler.this.mWindowManagerService.mRoot.mTransitionController.isShellTransitionsEnabled()) {
                    for (int length2 = activityRecordArr.length - 1; length2 >= 0; length2--) {
                        com.android.server.wm.WindowContainer.enforceSurfaceVisible(activityRecordArr[length2]);
                    }
                }
            }

            @android.annotation.Nullable
            java.lang.Runnable build() {
                if (this.mOpenTargets == null || this.mCloseTarget == null || this.mOpenTargets.length == 0) {
                    return null;
                }
                boolean z = this.mIsLaunchBehind || !com.android.server.wm.BackNavigationController.AnimationHandler.this.isSupportWindowlessSurface();
                com.android.server.wm.ActivityRecord[] topOpenActivities = com.android.server.wm.BackNavigationController.getTopOpenActivities(this.mOpenTargets);
                if (z && topOpenActivities == null) {
                    android.util.Slog.e(com.android.server.wm.BackNavigationController.TAG, "No opening activity");
                    return null;
                }
                if (!com.android.server.wm.BackNavigationController.AnimationHandler.this.composeAnimations(this.mCloseTarget, this.mOpenTargets, topOpenActivities)) {
                    return null;
                }
                this.mCloseTarget.mTransitionController.mSnapshotController.mActivitySnapshotController.clearOnBackPressedActivities();
                applyPreviewStrategy(com.android.server.wm.BackNavigationController.AnimationHandler.this.mOpenAnimAdaptor, topOpenActivities);
                final android.window.IBackAnimationFinishedCallback makeAnimationFinishedCallback = makeAnimationFinishedCallback();
                final android.view.RemoteAnimationTarget[] animationTargets = com.android.server.wm.BackNavigationController.AnimationHandler.this.getAnimationTargets();
                return new java.lang.Runnable() { // from class: com.android.server.wm.BackNavigationController$AnimationHandler$ScheduleAnimationBuilder$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder.this.lambda$build$0(animationTargets, makeAnimationFinishedCallback);
                    }
                };
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$build$0(android.view.RemoteAnimationTarget[] remoteAnimationTargetArr, android.window.IBackAnimationFinishedCallback iBackAnimationFinishedCallback) {
                try {
                    this.mBackAnimationAdapter.getRunner().onAnimationStart(remoteAnimationTargetArr, (android.view.RemoteAnimationTarget[]) null, (android.view.RemoteAnimationTarget[]) null, iBackAnimationFinishedCallback);
                } catch (android.os.RemoteException e) {
                    e.printStackTrace();
                }
            }

            private android.window.IBackAnimationFinishedCallback makeAnimationFinishedCallback() {
                return new android.window.IBackAnimationFinishedCallback.Stub() { // from class: com.android.server.wm.BackNavigationController.AnimationHandler.ScheduleAnimationBuilder.1
                    public void onAnimationFinished(boolean z) {
                        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.BackNavigationController.AnimationHandler.this.mWindowManagerService.mGlobalLock;
                        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                if (!com.android.server.wm.BackNavigationController.AnimationHandler.this.mComposed) {
                                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                    return;
                                }
                                if (!z) {
                                    com.android.server.wm.BackNavigationController.AnimationHandler.this.clearBackAnimateTarget();
                                } else {
                                    com.android.server.wm.BackNavigationController.AnimationHandler.this.mWaitTransition = true;
                                }
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            } catch (java.lang.Throwable th) {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                    }
                };
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public static com.android.server.wm.ActivityRecord[] getTopOpenActivities(@android.annotation.NonNull com.android.server.wm.WindowContainer[] windowContainerArr) {
        com.android.server.wm.WindowContainer windowContainer = windowContainerArr[0];
        if (windowContainer.asTask() == null) {
            if (windowContainer.asActivityRecord() == null) {
                return null;
            }
            int length = windowContainerArr.length;
            com.android.server.wm.ActivityRecord[] activityRecordArr = new com.android.server.wm.ActivityRecord[length];
            for (int i = length - 1; i >= 0; i--) {
                activityRecordArr[i] = windowContainerArr[i].asActivityRecord();
            }
            return activityRecordArr;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.wm.ActivityRecord topNonFinishingActivity = windowContainer.asTask().getTopNonFinishingActivity();
        if (topNonFinishingActivity != null) {
            arrayList.add(topNonFinishingActivity);
            findAdjacentActivityIfExist(topNonFinishingActivity, arrayList);
        }
        com.android.server.wm.ActivityRecord[] activityRecordArr2 = new com.android.server.wm.ActivityRecord[arrayList.size()];
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            activityRecordArr2[size] = (com.android.server.wm.ActivityRecord) arrayList.get(size);
        }
        return activityRecordArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setLaunchBehind(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        if (!activityRecord.isVisibleRequested()) {
            activityRecord.commitVisibility(true, false);
            activityRecord.mTransitionController.mSnapshotController.mActivitySnapshotController.addOnBackPressedActivity(activityRecord);
        }
        activityRecord.mLaunchTaskBehind = true;
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, 2077221835543623088L, 0, "Setting Activity.mLauncherTaskBehind to true. Activity=%s", java.lang.String.valueOf(activityRecord));
        activityRecord.mTaskSupervisor.mStoppingActivities.remove(activityRecord);
        activityRecord.getDisplayContent().ensureActivitiesVisible(null, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void restoreLaunchBehind(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        activityRecord.mDisplayContent.continueUpdateOrientationForDiffOrienLaunchingApp();
        activityRecord.mTaskSupervisor.scheduleLaunchTaskBehindComplete(activityRecord.token);
        activityRecord.mLaunchTaskBehind = false;
        activityRecord.mTransitionController.mSnapshotController.mActivitySnapshotController.clearOnBackPressedActivities();
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, -4442170697458371588L, 0, "Setting Activity.mLauncherTaskBehind to false. Activity=%s", java.lang.String.valueOf(activityRecord));
    }

    void checkAnimationReady(com.android.server.wm.WallpaperController wallpaperController) {
        if (!this.mBackAnimationInProgress) {
            return;
        }
        if ((!this.mShowWallpaper || (wallpaperController.getWallpaperTarget() != null && wallpaperController.wallpaperTransitionReady())) && this.mPendingAnimation != null) {
            startAnimation();
        }
    }

    void startAnimation() {
        if (!this.mBackAnimationInProgress) {
            if (this.mPendingAnimation != null) {
                clearBackAnimations();
                this.mPendingAnimation = null;
                return;
            }
            return;
        }
        if (this.mPendingAnimation != null) {
            this.mPendingAnimation.run();
            this.mPendingAnimation = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onBackNavigationDone, reason: merged with bridge method [inline-methods] */
    public void lambda$startBackNavigation$3(android.os.Bundle bundle, int i) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, 267946503010201613L, 12, "onBackNavigationDone backType=%s, triggerBack=%b", java.lang.String.valueOf(i), java.lang.Boolean.valueOf(bundle != null && bundle.getBoolean("TriggerBack")));
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWindowManagerService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mNavigationMonitor.stopMonitorForRemote();
                this.mBackAnimationInProgress = false;
                this.mShowWallpaper = false;
                this.mPendingAnimation = null;
                this.mPendingAnimationBuilder = null;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    static android.window.TaskSnapshot getSnapshot(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, com.android.server.wm.ActivityRecord[] activityRecordArr) {
        android.window.TaskSnapshot taskSnapshot;
        if (windowContainer.asTask() != null) {
            com.android.server.wm.Task asTask = windowContainer.asTask();
            taskSnapshot = asTask.mRootWindowContainer.mWindowManager.mTaskSnapshotController.getSnapshot(asTask.mTaskId, asTask.mUserId, false, false);
        } else if (windowContainer.asActivityRecord() == null) {
            taskSnapshot = null;
        } else {
            taskSnapshot = windowContainer.asActivityRecord().mWmService.mSnapshotController.mActivitySnapshotController.getSnapshot(activityRecordArr);
        }
        if (isSnapshotCompatible(taskSnapshot, activityRecordArr)) {
            return taskSnapshot;
        }
        return null;
    }

    static boolean isSnapshotCompatible(@android.annotation.NonNull android.window.TaskSnapshot taskSnapshot, @android.annotation.NonNull com.android.server.wm.ActivityRecord[] activityRecordArr) {
        if (taskSnapshot == null) {
            return false;
        }
        boolean z = false;
        for (int length = activityRecordArr.length - 1; length >= 0; length--) {
            com.android.server.wm.ActivityRecord activityRecord = activityRecordArr[length];
            if (!activityRecord.isSnapshotOrientationCompatible(taskSnapshot)) {
                return false;
            }
            z |= activityRecord.isSnapshotComponentCompatible(taskSnapshot);
        }
        return z;
    }

    void setWindowManager(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mWindowManagerService = windowManagerService;
        this.mAnimationHandler = new com.android.server.wm.BackNavigationController.AnimationHandler(windowManagerService);
    }

    boolean isWallpaperVisible(com.android.server.wm.WindowState windowState) {
        return this.mAnimationHandler.mComposed && this.mShowWallpaper && windowState.mAttrs.type == 1 && windowState.mActivityRecord != null && this.mAnimationHandler.isTarget(windowState.mActivityRecord, true);
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366145L, this.mBackAnimationInProgress);
        protoOutputStream.write(1120986464258L, this.mLastBackType);
        protoOutputStream.write(1133871366147L, this.mShowWallpaper);
        if (this.mAnimationHandler.mOpenAnimAdaptor != null && this.mAnimationHandler.mOpenAnimAdaptor.mAdaptors.length > 0) {
            this.mAnimationHandler.mOpenActivities[0].writeNameToProto(protoOutputStream, 1138166333444L);
        } else {
            protoOutputStream.write(1138166333444L, "");
        }
        protoOutputStream.write(1133871366149L, this.mAnimationHandler.mComposed || this.mAnimationHandler.mWaitTransition);
        protoOutputStream.end(start);
    }
}
