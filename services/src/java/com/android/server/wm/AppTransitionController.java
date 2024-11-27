package com.android.server.wm;

/* loaded from: classes3.dex */
public class AppTransitionController {
    private static final java.lang.String TAG = "WindowManager";
    private static final int TYPE_ACTIVITY = 1;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_TASK = 3;
    private static final int TYPE_TASK_FRAGMENT = 2;
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private final com.android.server.wm.WindowManagerService mService;
    private final com.android.server.wm.WallpaperController mWallpaperControllerLocked;
    private android.view.RemoteAnimationDefinition mRemoteAnimationDefinition = null;
    private final android.util.ArrayMap<com.android.server.wm.WindowContainer, java.lang.Integer> mTempTransitionReasons = new android.util.ArrayMap<>();
    private final java.util.ArrayList<com.android.server.wm.WindowContainer> mTempTransitionWindows = new java.util.ArrayList<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface TransitContainerType {
    }

    AppTransitionController(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        this.mWallpaperControllerLocked = this.mDisplayContent.mWallpaperController;
    }

    void registerRemoteAnimations(android.view.RemoteAnimationDefinition remoteAnimationDefinition) {
        this.mRemoteAnimationDefinition = remoteAnimationDefinition;
    }

    @android.annotation.Nullable
    private com.android.server.wm.WindowState getOldWallpaper() {
        com.android.server.wm.WindowState wallpaperTarget = this.mWallpaperControllerLocked.getWallpaperTarget();
        int firstAppTransition = this.mDisplayContent.mAppTransition.getFirstAppTransition();
        boolean z = true;
        android.util.ArraySet<com.android.server.wm.WindowContainer> animationTargets = getAnimationTargets(this.mDisplayContent.mOpeningApps, this.mDisplayContent.mClosingApps, true);
        if (wallpaperTarget == null || (!wallpaperTarget.hasWallpaper() && ((firstAppTransition != 1 && firstAppTransition != 3) || animationTargets.isEmpty() || animationTargets.valueAt(0).asTask() == null || !this.mWallpaperControllerLocked.isWallpaperVisible()))) {
            z = false;
        }
        if (this.mWallpaperControllerLocked.isWallpaperTargetAnimating() || !z) {
            return null;
        }
        return wallpaperTarget;
    }

    void handleAppTransitionReady() {
        android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet;
        this.mTempTransitionReasons.clear();
        if (!transitionGoodToGo(this.mDisplayContent.mOpeningApps, this.mTempTransitionReasons) || !transitionGoodToGo(this.mDisplayContent.mChangingContainers, this.mTempTransitionReasons) || !transitionGoodToGoForTaskFragments()) {
            return;
        }
        if (!this.mDisplayContent.mOpeningApps.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.wm.AppTransitionController$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return ((com.android.server.wm.ActivityRecord) obj).isActivityTypeRecents();
            }
        })) {
            android.util.ArraySet arraySet2 = new android.util.ArraySet();
            arraySet2.addAll((android.util.ArraySet) this.mDisplayContent.mOpeningApps);
            arraySet2.addAll((android.util.ArraySet) this.mDisplayContent.mChangingContainers);
            int i = 0;
            boolean z = false;
            while (true) {
                if (i >= arraySet2.size()) {
                    break;
                }
                com.android.server.wm.ActivityRecord appFromContainer = getAppFromContainer((com.android.server.wm.WindowContainer) arraySet2.valueAt(i));
                if (appFromContainer != null) {
                    if (!appFromContainer.isAnimating(2, 8)) {
                        z = false;
                        break;
                    }
                    z = true;
                }
                i++;
            }
            if (z) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -5726018006883159788L, 0, null, null);
                return;
            }
        }
        android.os.Trace.traceBegin(32L, "AppTransitionReady");
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 6514556033257323299L, 0, null, null);
        this.mDisplayContent.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.AppTransitionController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.WindowState) obj).cleanupAnimatingExitWindow();
            }
        }, true);
        com.android.server.wm.AppTransition appTransition = this.mDisplayContent.mAppTransition;
        this.mDisplayContent.mNoAnimationNotifyOnTransitionFinished.clear();
        appTransition.removeAppTransitionTimeoutCallbacks();
        this.mDisplayContent.mWallpaperMayChange = false;
        int size = this.mDisplayContent.mOpeningApps.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((com.android.server.wm.ActivityRecord) this.mDisplayContent.mOpeningApps.valueAtUnchecked(i2)).clearAnimatingFlags();
        }
        int size2 = this.mDisplayContent.mChangingContainers.size();
        for (int i3 = 0; i3 < size2; i3++) {
            com.android.server.wm.ActivityRecord appFromContainer2 = getAppFromContainer((com.android.server.wm.WindowContainer) this.mDisplayContent.mChangingContainers.valueAtUnchecked(i3));
            if (appFromContainer2 != null) {
                appFromContainer2.clearAnimatingFlags();
            }
        }
        this.mWallpaperControllerLocked.adjustWallpaperWindowsForAppTransitionIfNeeded(this.mDisplayContent.mOpeningApps);
        android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet3 = this.mDisplayContent.mOpeningApps;
        android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet4 = this.mDisplayContent.mClosingApps;
        if (!this.mDisplayContent.mAtmService.mBackNavigationController.isMonitoringTransition()) {
            arraySet = arraySet4;
        } else {
            arraySet3 = new android.util.ArraySet<>(this.mDisplayContent.mOpeningApps);
            android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet5 = new android.util.ArraySet<>(this.mDisplayContent.mClosingApps);
            if (this.mDisplayContent.mAtmService.mBackNavigationController.removeIfContainsBackAnimationTargets(arraySet3, arraySet5)) {
                this.mDisplayContent.mAtmService.mBackNavigationController.clearBackAnimations();
            }
            arraySet = arraySet5;
        }
        int transitCompatType = getTransitCompatType(this.mDisplayContent.mAppTransition, arraySet3, arraySet, this.mDisplayContent.mChangingContainers, this.mWallpaperControllerLocked.getWallpaperTarget(), getOldWallpaper(), this.mDisplayContent.mSkipAppTransitionAnimation);
        this.mDisplayContent.mSkipAppTransitionAnimation = false;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 3518082157667760495L, 1, null, java.lang.Long.valueOf(this.mDisplayContent.mDisplayId), java.lang.String.valueOf(appTransition.toString()), java.lang.String.valueOf(arraySet3), java.lang.String.valueOf(arraySet), java.lang.String.valueOf(com.android.server.wm.AppTransition.appTransitionOldToString(transitCompatType)));
        android.util.ArraySet<java.lang.Integer> collectActivityTypes = collectActivityTypes(arraySet3, arraySet, this.mDisplayContent.mChangingContainers);
        android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet6 = arraySet;
        com.android.server.wm.ActivityRecord findAnimLayoutParamsToken = findAnimLayoutParamsToken(transitCompatType, collectActivityTypes, arraySet3, arraySet, this.mDisplayContent.mChangingContainers);
        com.android.server.wm.ActivityRecord topApp = getTopApp(arraySet3, false);
        com.android.server.wm.ActivityRecord topApp2 = getTopApp(arraySet6, false);
        com.android.server.wm.ActivityRecord topApp3 = getTopApp(this.mDisplayContent.mChangingContainers, false);
        android.view.WindowManager.LayoutParams animLp = getAnimLp(findAnimLayoutParamsToken);
        if (!overrideWithTaskFragmentRemoteAnimation(transitCompatType, collectActivityTypes)) {
            unfreezeEmbeddedChangingWindows();
            overrideWithRemoteAnimationIfSet(findAnimLayoutParamsToken, transitCompatType, collectActivityTypes);
        }
        boolean z2 = containsVoiceInteraction(this.mDisplayContent.mClosingApps) || containsVoiceInteraction(this.mDisplayContent.mOpeningApps);
        this.mService.mSurfaceAnimationRunner.deferStartingAnimations();
        try {
            applyAnimations(arraySet3, arraySet6, transitCompatType, animLp, z2);
            handleClosingApps();
            handleOpeningApps();
            handleChangingApps(transitCompatType);
            handleClosingChangingContainers();
            appTransition.setLastAppTransition(transitCompatType, topApp, topApp2, topApp3);
            appTransition.getTransitFlags();
            int goodToGo = appTransition.goodToGo(transitCompatType, topApp);
            appTransition.postAnimationCallback();
            appTransition.clear();
            this.mService.mSurfaceAnimationRunner.continueStartingAnimations();
            this.mService.mSnapshotController.onTransitionStarting(this.mDisplayContent);
            this.mDisplayContent.mOpeningApps.clear();
            this.mDisplayContent.mClosingApps.clear();
            this.mDisplayContent.mChangingContainers.clear();
            this.mDisplayContent.mUnknownAppVisibilityController.clear();
            this.mDisplayContent.mClosingChangingContainers.clear();
            this.mDisplayContent.setLayoutNeeded();
            this.mDisplayContent.computeImeTarget(true);
            this.mService.mAtmService.mTaskSupervisor.getActivityMetricsLogger().notifyTransitionStarting(this.mTempTransitionReasons);
            android.os.Trace.traceEnd(32L);
            com.android.server.wm.DisplayContent displayContent = this.mDisplayContent;
            displayContent.pendingLayoutChanges = goodToGo | 1 | 2 | displayContent.pendingLayoutChanges;
        } catch (java.lang.Throwable th) {
            appTransition.clear();
            this.mService.mSurfaceAnimationRunner.continueStartingAnimations();
            throw th;
        }
    }

    static int getTransitCompatType(com.android.server.wm.AppTransition appTransition, android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet, android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet2, android.util.ArraySet<com.android.server.wm.WindowContainer> arraySet3, @android.annotation.Nullable com.android.server.wm.WindowState windowState, @android.annotation.Nullable com.android.server.wm.WindowState windowState2, boolean z) {
        com.android.server.wm.ActivityRecord topApp = getTopApp(arraySet, false);
        com.android.server.wm.ActivityRecord topApp2 = getTopApp(arraySet2, true);
        boolean z2 = canBeWallpaperTarget(arraySet) && windowState != null;
        boolean z3 = canBeWallpaperTarget(arraySet2) && windowState != null;
        switch (appTransition.getKeyguardTransition()) {
            case 7:
                return z2 ? 21 : 20;
            case 8:
                if (!arraySet2.isEmpty()) {
                    return 6;
                }
                if (!arraySet.isEmpty() && arraySet.valueAt(0).getActivityType() == 5) {
                    return 33;
                }
                return 22;
            case 9:
                return 23;
            default:
                if (topApp != null && topApp.getActivityType() == 5) {
                    return 31;
                }
                if (topApp2 != null && topApp2.getActivityType() == 5) {
                    return 32;
                }
                if (z) {
                    return -1;
                }
                int transitFlags = appTransition.getTransitFlags();
                int firstAppTransition = appTransition.getFirstAppTransition();
                if (appTransition.containsTransitRequest(6) && !arraySet3.isEmpty()) {
                    int transitContainerType = getTransitContainerType(arraySet3.valueAt(0));
                    switch (transitContainerType) {
                        case 2:
                            return 30;
                        case 3:
                            return 27;
                        default:
                            throw new java.lang.IllegalStateException("TRANSIT_CHANGE with unrecognized changing type=" + transitContainerType);
                    }
                }
                if ((transitFlags & 16) != 0) {
                    return 26;
                }
                if (firstAppTransition == 0) {
                    return 0;
                }
                if (com.android.server.wm.AppTransition.isNormalTransit(firstAppTransition)) {
                    boolean z4 = !arraySet.isEmpty();
                    boolean z5 = true;
                    for (int size = arraySet.size() - 1; size >= 0; size--) {
                        com.android.server.wm.ActivityRecord valueAt = arraySet.valueAt(size);
                        if (!valueAt.isVisible()) {
                            if (!valueAt.fillsParent()) {
                                z5 = false;
                            } else {
                                z4 = false;
                                z5 = false;
                            }
                        }
                    }
                    boolean z6 = !arraySet2.isEmpty();
                    int size2 = arraySet2.size() - 1;
                    while (true) {
                        if (size2 >= 0) {
                            if (arraySet2.valueAt(size2).fillsParent()) {
                                z6 = false;
                            } else {
                                size2--;
                            }
                        }
                    }
                    if (z6 && z5) {
                        return 25;
                    }
                    if (z4 && arraySet2.isEmpty()) {
                        return 24;
                    }
                }
                if (z3 && z2) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -2503124388387340567L, 0, null, null);
                    switch (firstAppTransition) {
                        case 1:
                        case 3:
                            return 14;
                        case 2:
                        case 4:
                            return 15;
                    }
                }
                if (windowState2 != null && !arraySet.isEmpty() && !arraySet.contains(windowState2.mActivityRecord) && arraySet2.contains(windowState2.mActivityRecord) && topApp2 == windowState2.mActivityRecord) {
                    return 12;
                }
                if (windowState != null && windowState.isVisible() && arraySet.contains(windowState.mActivityRecord) && topApp == windowState.mActivityRecord) {
                    return 13;
                }
                android.util.ArraySet<com.android.server.wm.WindowContainer> animationTargets = getAnimationTargets(arraySet, arraySet2, true);
                android.util.ArraySet<com.android.server.wm.WindowContainer> animationTargets2 = getAnimationTargets(arraySet, arraySet2, false);
                com.android.server.wm.WindowContainer valueAt2 = !animationTargets.isEmpty() ? animationTargets.valueAt(0) : null;
                com.android.server.wm.WindowContainer valueAt3 = animationTargets2.isEmpty() ? null : animationTargets2.valueAt(0);
                int transitContainerType2 = getTransitContainerType(valueAt2);
                int transitContainerType3 = getTransitContainerType(valueAt3);
                if (appTransition.containsTransitRequest(3) && transitContainerType2 == 3) {
                    return (topApp == null || !topApp.isActivityTypeHome()) ? 10 : 11;
                }
                if (appTransition.containsTransitRequest(4) && transitContainerType3 == 3) {
                    return 11;
                }
                if (appTransition.containsTransitRequest(1)) {
                    if (transitContainerType2 == 3) {
                        return (appTransition.getTransitFlags() & 32) != 0 ? 16 : 8;
                    }
                    if (transitContainerType2 == 1) {
                        return 6;
                    }
                    if (transitContainerType2 == 2) {
                        return 28;
                    }
                }
                if (appTransition.containsTransitRequest(2)) {
                    if (transitContainerType3 == 3) {
                        return 9;
                    }
                    if (transitContainerType3 == 2) {
                        return 29;
                    }
                    if (transitContainerType3 == 1) {
                        for (int size3 = arraySet2.size() - 1; size3 >= 0; size3--) {
                            if (arraySet2.valueAt(size3).visibleIgnoringKeyguard) {
                                return 7;
                            }
                        }
                        return -1;
                    }
                }
                return (!appTransition.containsTransitRequest(5) || animationTargets.isEmpty() || arraySet.isEmpty()) ? 0 : 18;
        }
    }

    private static int getTransitContainerType(@android.annotation.Nullable com.android.server.wm.WindowContainer<?> windowContainer) {
        if (windowContainer == null) {
            return 0;
        }
        if (windowContainer.asTask() != null) {
            return 3;
        }
        if (windowContainer.asTaskFragment() != null) {
            return 2;
        }
        if (windowContainer.asActivityRecord() == null) {
            return 0;
        }
        return 1;
    }

    @android.annotation.Nullable
    private static android.view.WindowManager.LayoutParams getAnimLp(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.WindowState findMainWindow = activityRecord != null ? activityRecord.findMainWindow() : null;
        if (findMainWindow != null) {
            return findMainWindow.mAttrs;
        }
        return null;
    }

    android.view.RemoteAnimationAdapter getRemoteAnimationOverride(@android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer, int i, android.util.ArraySet<java.lang.Integer> arraySet) {
        android.view.RemoteAnimationDefinition remoteAnimationDefinition;
        android.view.RemoteAnimationAdapter adapter;
        if (windowContainer != null && (remoteAnimationDefinition = windowContainer.getRemoteAnimationDefinition()) != null && (adapter = remoteAnimationDefinition.getAdapter(i, arraySet)) != null) {
            return adapter;
        }
        if (this.mRemoteAnimationDefinition != null) {
            return this.mRemoteAnimationDefinition.getAdapter(i, arraySet);
        }
        return null;
    }

    private void unfreezeEmbeddedChangingWindows() {
        android.util.ArraySet<com.android.server.wm.WindowContainer> arraySet = this.mDisplayContent.mChangingContainers;
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer valueAt = arraySet.valueAt(size);
            if (valueAt.isEmbedded()) {
                valueAt.mSurfaceFreezer.unfreeze(valueAt.getSyncTransaction());
            }
        }
    }

    private boolean transitionMayContainNonAppWindows(int i) {
        return com.android.server.wm.NonAppWindowAnimationAdapter.shouldStartNonAppWindowAnimationsForKeyguardExit(i) || com.android.server.wm.NonAppWindowAnimationAdapter.shouldAttachNavBarToApp(this.mService, this.mDisplayContent, i) || com.android.server.wm.WallpaperAnimationAdapter.shouldStartWallpaperAnimation(this.mDisplayContent);
    }

    private boolean transitionContainsTaskFragmentWithBoundsOverride() {
        boolean z = true;
        for (int size = this.mDisplayContent.mChangingContainers.size() - 1; size >= 0; size--) {
            if (this.mDisplayContent.mChangingContainers.valueAt(size).isEmbedded()) {
                return true;
            }
        }
        this.mTempTransitionWindows.clear();
        this.mTempTransitionWindows.addAll(this.mDisplayContent.mClosingApps);
        this.mTempTransitionWindows.addAll(this.mDisplayContent.mOpeningApps);
        int size2 = this.mTempTransitionWindows.size() - 1;
        while (true) {
            if (size2 < 0) {
                z = false;
                break;
            }
            com.android.server.wm.TaskFragment taskFragment = this.mTempTransitionWindows.get(size2).asActivityRecord().getTaskFragment();
            if (taskFragment != null && taskFragment.isEmbeddedWithBoundsOverride()) {
                break;
            }
            size2--;
        }
        this.mTempTransitionWindows.clear();
        return z;
    }

    @android.annotation.Nullable
    private com.android.server.wm.Task findParentTaskForAllEmbeddedWindows() {
        com.android.server.wm.Task task;
        this.mTempTransitionWindows.clear();
        this.mTempTransitionWindows.addAll(this.mDisplayContent.mClosingApps);
        this.mTempTransitionWindows.addAll(this.mDisplayContent.mOpeningApps);
        this.mTempTransitionWindows.addAll(this.mDisplayContent.mChangingContainers);
        int size = this.mTempTransitionWindows.size() - 1;
        com.android.server.wm.Task task2 = null;
        com.android.server.wm.Task task3 = null;
        while (true) {
            if (size < 0) {
                task2 = task3;
                break;
            }
            com.android.server.wm.ActivityRecord appFromContainer = getAppFromContainer(this.mTempTransitionWindows.get(size));
            if (appFromContainer == null || (task = appFromContainer.getTask()) == null || task.inPinnedWindowingMode() || ((task3 != null && task3 != task) || task.getRootActivity() == null || (appFromContainer.getUid() != task.effectiveUid && !appFromContainer.isEmbedded()))) {
                break;
            }
            size--;
            task3 = task;
        }
        this.mTempTransitionWindows.clear();
        return task2;
    }

    @android.annotation.Nullable
    private android.window.ITaskFragmentOrganizer findTaskFragmentOrganizer(@android.annotation.Nullable com.android.server.wm.Task task) {
        if (task == null) {
            return null;
        }
        final android.window.ITaskFragmentOrganizer[] iTaskFragmentOrganizerArr = new android.window.ITaskFragmentOrganizer[1];
        if (task.forAllLeafTaskFragments(new java.util.function.Predicate() { // from class: com.android.server.wm.AppTransitionController$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$findTaskFragmentOrganizer$0;
                lambda$findTaskFragmentOrganizer$0 = com.android.server.wm.AppTransitionController.lambda$findTaskFragmentOrganizer$0(iTaskFragmentOrganizerArr, (com.android.server.wm.TaskFragment) obj);
                return lambda$findTaskFragmentOrganizer$0;
            }
        })) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 855146509305002043L, 0, null, null);
            return null;
        }
        return iTaskFragmentOrganizerArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$findTaskFragmentOrganizer$0(android.window.ITaskFragmentOrganizer[] iTaskFragmentOrganizerArr, com.android.server.wm.TaskFragment taskFragment) {
        android.window.ITaskFragmentOrganizer taskFragmentOrganizer = taskFragment.getTaskFragmentOrganizer();
        if (taskFragmentOrganizer == null) {
            return false;
        }
        if (iTaskFragmentOrganizerArr[0] != null && !iTaskFragmentOrganizerArr[0].asBinder().equals(taskFragmentOrganizer.asBinder())) {
            return true;
        }
        iTaskFragmentOrganizerArr[0] = taskFragmentOrganizer;
        return false;
    }

    private boolean overrideWithTaskFragmentRemoteAnimation(int i, android.util.ArraySet<java.lang.Integer> arraySet) {
        android.view.RemoteAnimationDefinition remoteAnimationDefinition;
        if (transitionMayContainNonAppWindows(i) || !transitionContainsTaskFragmentWithBoundsOverride()) {
            return false;
        }
        final com.android.server.wm.Task findParentTaskForAllEmbeddedWindows = findParentTaskForAllEmbeddedWindows();
        android.window.ITaskFragmentOrganizer findTaskFragmentOrganizer = findTaskFragmentOrganizer(findParentTaskForAllEmbeddedWindows);
        android.view.RemoteAnimationAdapter remoteAnimationAdapter = null;
        if (findTaskFragmentOrganizer != null) {
            remoteAnimationDefinition = this.mDisplayContent.mAtmService.mTaskFragmentOrganizerController.getRemoteAnimationDefinition(findTaskFragmentOrganizer);
        } else {
            remoteAnimationDefinition = null;
        }
        if (remoteAnimationDefinition != null) {
            remoteAnimationAdapter = remoteAnimationDefinition.getAdapter(i, arraySet);
        }
        if (remoteAnimationAdapter == null) {
            return false;
        }
        this.mDisplayContent.mAppTransition.overridePendingAppTransitionRemote(remoteAnimationAdapter, false, true);
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 59396412370137517L, 0, null, java.lang.String.valueOf(com.android.server.wm.AppTransition.appTransitionOldToString(i)));
        boolean z = !findParentTaskForAllEmbeddedWindows.isFullyTrustedEmbedding(this.mDisplayContent.mAtmService.mTaskFragmentOrganizerController.getTaskFragmentOrganizerUid(findTaskFragmentOrganizer));
        com.android.server.wm.RemoteAnimationController remoteAnimationController = this.mDisplayContent.mAppTransition.getRemoteAnimationController();
        if (z && remoteAnimationController != null) {
            remoteAnimationController.setOnRemoteAnimationReady(new java.lang.Runnable() { // from class: com.android.server.wm.AppTransitionController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.AppTransitionController.lambda$overrideWithTaskFragmentRemoteAnimation$2(com.android.server.wm.Task.this);
                }
            });
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 2280055488397326910L, 1, null, java.lang.Long.valueOf(findParentTaskForAllEmbeddedWindows.mTaskId));
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$overrideWithTaskFragmentRemoteAnimation$2(com.android.server.wm.Task task) {
        task.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.AppTransitionController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.ActivityRecord) obj).setDropInputForAnimation(true);
            }
        });
    }

    private void overrideWithRemoteAnimationIfSet(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, int i, android.util.ArraySet<java.lang.Integer> arraySet) {
        android.view.RemoteAnimationAdapter remoteAnimationAdapter = null;
        if (i != 26) {
            if (com.android.server.wm.AppTransition.isKeyguardGoingAwayTransitOld(i)) {
                if (this.mRemoteAnimationDefinition != null) {
                    remoteAnimationAdapter = this.mRemoteAnimationDefinition.getAdapter(i, arraySet);
                }
            } else if (this.mDisplayContent.mAppTransition.getRemoteAnimationController() == null) {
                remoteAnimationAdapter = getRemoteAnimationOverride(activityRecord, i, arraySet);
            }
        }
        if (remoteAnimationAdapter != null) {
            this.mDisplayContent.mAppTransition.overridePendingAppTransitionRemote(remoteAnimationAdapter);
        }
    }

    @android.annotation.Nullable
    static com.android.server.wm.Task findRootTaskFromContainer(com.android.server.wm.WindowContainer windowContainer) {
        return windowContainer.asTaskFragment() != null ? windowContainer.asTaskFragment().getRootTask() : windowContainer.asActivityRecord().getRootTask();
    }

    @android.annotation.Nullable
    static com.android.server.wm.ActivityRecord getAppFromContainer(com.android.server.wm.WindowContainer windowContainer) {
        return windowContainer.asTaskFragment() != null ? windowContainer.asTaskFragment().getTopNonFinishingActivity() : windowContainer.asActivityRecord();
    }

    @android.annotation.Nullable
    private com.android.server.wm.ActivityRecord findAnimLayoutParamsToken(final int i, final android.util.ArraySet<java.lang.Integer> arraySet, android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet2, android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet3, android.util.ArraySet<com.android.server.wm.WindowContainer> arraySet4) {
        com.android.server.wm.ActivityRecord lookForHighestTokenWithFilter = lookForHighestTokenWithFilter(arraySet3, arraySet2, arraySet4, new java.util.function.Predicate() { // from class: com.android.server.wm.AppTransitionController$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$findAnimLayoutParamsToken$3;
                lambda$findAnimLayoutParamsToken$3 = com.android.server.wm.AppTransitionController.lambda$findAnimLayoutParamsToken$3(i, arraySet, (com.android.server.wm.ActivityRecord) obj);
                return lambda$findAnimLayoutParamsToken$3;
            }
        });
        if (lookForHighestTokenWithFilter != null) {
            return lookForHighestTokenWithFilter;
        }
        com.android.server.wm.ActivityRecord lookForHighestTokenWithFilter2 = lookForHighestTokenWithFilter(arraySet3, arraySet2, arraySet4, new java.util.function.Predicate() { // from class: com.android.server.wm.AppTransitionController$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$findAnimLayoutParamsToken$4;
                lambda$findAnimLayoutParamsToken$4 = com.android.server.wm.AppTransitionController.lambda$findAnimLayoutParamsToken$4((com.android.server.wm.ActivityRecord) obj);
                return lambda$findAnimLayoutParamsToken$4;
            }
        });
        if (lookForHighestTokenWithFilter2 != null) {
            return lookForHighestTokenWithFilter2;
        }
        return lookForHighestTokenWithFilter(arraySet3, arraySet2, arraySet4, new java.util.function.Predicate() { // from class: com.android.server.wm.AppTransitionController$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$findAnimLayoutParamsToken$5;
                lambda$findAnimLayoutParamsToken$5 = com.android.server.wm.AppTransitionController.lambda$findAnimLayoutParamsToken$5((com.android.server.wm.ActivityRecord) obj);
                return lambda$findAnimLayoutParamsToken$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$findAnimLayoutParamsToken$3(int i, android.util.ArraySet arraySet, com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.getRemoteAnimationDefinition() != null && activityRecord.getRemoteAnimationDefinition().hasTransition(i, arraySet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$findAnimLayoutParamsToken$4(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.fillsParent() && activityRecord.findMainWindow() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$findAnimLayoutParamsToken$5(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.findMainWindow() != null;
    }

    private static android.util.ArraySet<java.lang.Integer> collectActivityTypes(android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet, android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet2, android.util.ArraySet<com.android.server.wm.WindowContainer> arraySet3) {
        android.util.ArraySet<java.lang.Integer> arraySet4 = new android.util.ArraySet<>();
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            arraySet4.add(java.lang.Integer.valueOf(arraySet.valueAt(size).getActivityType()));
        }
        for (int size2 = arraySet2.size() - 1; size2 >= 0; size2--) {
            arraySet4.add(java.lang.Integer.valueOf(arraySet2.valueAt(size2).getActivityType()));
        }
        for (int size3 = arraySet3.size() - 1; size3 >= 0; size3--) {
            arraySet4.add(java.lang.Integer.valueOf(arraySet3.valueAt(size3).getActivityType()));
        }
        return arraySet4;
    }

    private static com.android.server.wm.ActivityRecord lookForHighestTokenWithFilter(android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet, android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet2, android.util.ArraySet<com.android.server.wm.WindowContainer> arraySet3, java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate) {
        com.android.server.wm.WindowContainer valueAt;
        int size = arraySet.size();
        int size2 = arraySet2.size() + size;
        int size3 = arraySet3.size() + size2;
        int i = Integer.MIN_VALUE;
        com.android.server.wm.ActivityRecord activityRecord = null;
        for (int i2 = 0; i2 < size3; i2++) {
            if (i2 < size) {
                valueAt = arraySet.valueAt(i2);
            } else if (i2 < size2) {
                valueAt = arraySet2.valueAt(i2 - size);
            } else {
                valueAt = arraySet3.valueAt(i2 - size2);
            }
            int prefixOrderIndex = valueAt.getPrefixOrderIndex();
            com.android.server.wm.ActivityRecord appFromContainer = getAppFromContainer(valueAt);
            if (appFromContainer != null && predicate.test(appFromContainer) && prefixOrderIndex > i) {
                activityRecord = appFromContainer;
                i = prefixOrderIndex;
            }
        }
        return activityRecord;
    }

    private boolean containsVoiceInteraction(android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet) {
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            if (arraySet.valueAt(size).mVoiceInteraction) {
                return true;
            }
        }
        return false;
    }

    private void applyAnimations(android.util.ArraySet<com.android.server.wm.WindowContainer> arraySet, android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet2, int i, boolean z, android.view.WindowManager.LayoutParams layoutParams, boolean z2) {
        int size = arraySet.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.wm.WindowContainer valueAt = arraySet.valueAt(i2);
            java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList = new java.util.ArrayList<>();
            for (int i3 = 0; i3 < arraySet2.size(); i3++) {
                com.android.server.wm.ActivityRecord valueAt2 = arraySet2.valueAt(i3);
                if (valueAt2.isDescendantOf(valueAt)) {
                    arrayList.add(valueAt2);
                }
            }
            valueAt.applyAnimation(layoutParams, i, z, z2, arrayList);
        }
    }

    static boolean isTaskViewTask(com.android.server.wm.WindowContainer windowContainer) {
        if ((windowContainer instanceof com.android.server.wm.Task) && ((com.android.server.wm.Task) windowContainer).mRemoveWithTaskOrganizer) {
            return true;
        }
        com.android.server.wm.WindowContainer parent = windowContainer.getParent();
        return parent != null && (parent instanceof com.android.server.wm.Task) && ((com.android.server.wm.Task) parent).mRemoveWithTaskOrganizer;
    }

    @com.android.internal.annotations.VisibleForTesting
    static android.util.ArraySet<com.android.server.wm.WindowContainer> getAnimationTargets(android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet, android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet2, boolean z) {
        boolean z2;
        java.util.ArrayDeque arrayDeque = new java.util.ArrayDeque();
        android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet3 = z ? arraySet : arraySet2;
        for (int i = 0; i < arraySet3.size(); i++) {
            com.android.server.wm.ActivityRecord valueAt = arraySet3.valueAt(i);
            if (valueAt.shouldApplyAnimation(z)) {
                arrayDeque.add(valueAt);
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -3156084190956669377L, 60, null, java.lang.String.valueOf(valueAt), java.lang.Boolean.valueOf(valueAt.isVisible()), false);
            }
        }
        if (z) {
            arraySet = arraySet2;
        }
        android.util.ArraySet arraySet4 = new android.util.ArraySet();
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            for (com.android.server.wm.ActivityRecord valueAt2 = arraySet.valueAt(i2); valueAt2 != null; valueAt2 = valueAt2.getParent()) {
                arraySet4.add(valueAt2);
            }
        }
        android.util.ArraySet<com.android.server.wm.WindowContainer> arraySet5 = new android.util.ArraySet<>();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (!arrayDeque.isEmpty()) {
            com.android.server.wm.WindowContainer windowContainer = (com.android.server.wm.WindowContainer) arrayDeque.removeFirst();
            com.android.server.wm.WindowContainer parent = windowContainer.getParent();
            arrayList.clear();
            arrayList.add(windowContainer);
            if (!isTaskViewTask(windowContainer)) {
                if (parent == null || !parent.canCreateRemoteAnimationTarget() || ((windowContainer.asTask() != null && windowContainer.asTask().mInRemoveTask) || parent.isChangingAppTransition())) {
                    z2 = false;
                } else {
                    if (!arraySet4.contains(parent)) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (windowContainer.asTask() != null && windowContainer.asTask().getAdjacentTask() != null) {
                        z2 = false;
                    }
                    for (int i3 = 0; i3 < parent.getChildCount(); i3++) {
                        com.android.server.wm.WindowContainer childAt = parent.getChildAt(i3);
                        if (arrayDeque.remove(childAt)) {
                            if (!isTaskViewTask(childAt)) {
                                arrayList.add(childAt);
                            }
                        } else if (childAt != windowContainer && childAt.isVisible()) {
                            z2 = false;
                        }
                    }
                }
                if (z2) {
                    arrayDeque.add(parent);
                } else {
                    arraySet5.addAll(arrayList);
                }
            }
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -8226278785414579647L, 0, null, java.lang.String.valueOf(arraySet3), java.lang.String.valueOf(arraySet5));
        return arraySet5;
    }

    private void applyAnimations(android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet, android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet2, int i, android.view.WindowManager.LayoutParams layoutParams, boolean z) {
        com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mService.getRecentsAnimationController();
        if (i == -1 || (arraySet.isEmpty() && arraySet2.isEmpty())) {
            if (recentsAnimationController != null) {
                recentsAnimationController.sendTasksAppeared();
                return;
            }
            return;
        }
        if (com.android.server.wm.AppTransition.isActivityTransitOld(i)) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i2 = 0; i2 < arraySet2.size(); i2++) {
                com.android.server.wm.ActivityRecord valueAt = arraySet2.valueAt(i2);
                if (valueAt.areBoundsLetterboxed()) {
                    arrayList.add(new android.util.Pair(valueAt, valueAt.getLetterboxInsets()));
                }
            }
            for (int i3 = 0; i3 < arraySet.size(); i3++) {
                com.android.server.wm.ActivityRecord valueAt2 = arraySet.valueAt(i3);
                if (valueAt2.areBoundsLetterboxed()) {
                    android.graphics.Rect letterboxInsets = valueAt2.getLetterboxInsets();
                    java.util.Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        android.util.Pair pair = (android.util.Pair) it.next();
                        if (letterboxInsets.equals((android.graphics.Rect) pair.second)) {
                            com.android.server.wm.ActivityRecord activityRecord = (com.android.server.wm.ActivityRecord) pair.first;
                            valueAt2.setNeedsLetterboxedAnimation(true);
                            activityRecord.setNeedsLetterboxedAnimation(true);
                        }
                    }
                }
            }
        }
        android.util.ArraySet<com.android.server.wm.WindowContainer> animationTargets = getAnimationTargets(arraySet, arraySet2, true);
        android.util.ArraySet<com.android.server.wm.WindowContainer> animationTargets2 = getAnimationTargets(arraySet, arraySet2, false);
        applyAnimations(animationTargets, arraySet, i, true, layoutParams, z);
        applyAnimations(animationTargets2, arraySet2, i, false, layoutParams, z);
        if (recentsAnimationController != null) {
            recentsAnimationController.sendTasksAppeared();
        }
        for (int i4 = 0; i4 < arraySet.size(); i4++) {
            ((com.android.server.wm.ActivityRecord) arraySet.valueAtUnchecked(i4)).mOverrideTaskTransition = false;
        }
        for (int i5 = 0; i5 < arraySet2.size(); i5++) {
            ((com.android.server.wm.ActivityRecord) arraySet2.valueAtUnchecked(i5)).mOverrideTaskTransition = false;
        }
        com.android.server.wm.AccessibilityController accessibilityController = this.mDisplayContent.mWmService.mAccessibilityController;
        if (accessibilityController.hasCallbacks()) {
            accessibilityController.onAppWindowTransition(this.mDisplayContent.getDisplayId(), i);
        }
    }

    private void handleOpeningApps() {
        android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet = this.mDisplayContent.mOpeningApps;
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            com.android.server.wm.ActivityRecord valueAt = arraySet.valueAt(i);
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 4418653408751596915L, 0, null, java.lang.String.valueOf(valueAt));
            valueAt.commitVisibility(true, false);
            com.android.server.wm.WindowContainer animatingContainer = valueAt.getAnimatingContainer(2, 1);
            if (animatingContainer == null || !animatingContainer.getAnimationSources().contains(valueAt)) {
                this.mDisplayContent.mNoAnimationNotifyOnTransitionFinished.add(valueAt.token);
            }
            valueAt.updateReportedVisibilityLocked();
            valueAt.showAllWindowsLocked();
            if (this.mDisplayContent.mAppTransition.isNextAppTransitionThumbnailUp()) {
                valueAt.attachThumbnailAnimation();
            } else if (this.mDisplayContent.mAppTransition.isNextAppTransitionOpenCrossProfileApps()) {
                valueAt.attachCrossProfileAppsThumbnailAnimation();
            }
        }
    }

    private void handleClosingApps() {
        android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet = this.mDisplayContent.mClosingApps;
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            com.android.server.wm.ActivityRecord valueAt = arraySet.valueAt(i);
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -8367738619313176909L, 0, null, java.lang.String.valueOf(valueAt));
            valueAt.commitVisibility(false, false);
            valueAt.updateReportedVisibilityLocked();
            valueAt.allDrawn = true;
            if (valueAt.mStartingWindow != null && !valueAt.mStartingWindow.mAnimatingExit) {
                valueAt.removeStartingWindow();
            }
            if (this.mDisplayContent.mAppTransition.isNextAppTransitionThumbnailDown()) {
                valueAt.attachThumbnailAnimation();
            }
        }
    }

    private void handleClosingChangingContainers() {
        android.util.ArrayMap<com.android.server.wm.WindowContainer, android.graphics.Rect> arrayMap = this.mDisplayContent.mClosingChangingContainers;
        while (!arrayMap.isEmpty()) {
            com.android.server.wm.WindowContainer keyAt = arrayMap.keyAt(0);
            arrayMap.remove(keyAt);
            com.android.server.wm.TaskFragment asTaskFragment = keyAt.asTaskFragment();
            if (asTaskFragment != null) {
                asTaskFragment.updateOrganizedTaskFragmentSurface();
            }
        }
    }

    private void handleChangingApps(int i) {
        android.util.ArraySet<com.android.server.wm.WindowContainer> arraySet = this.mDisplayContent.mChangingContainers;
        int size = arraySet.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.wm.WindowContainer valueAt = arraySet.valueAt(i2);
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 1855459282905873641L, 0, null, java.lang.String.valueOf(valueAt));
            valueAt.applyAnimation(null, i, true, false, null);
        }
    }

    private boolean transitionGoodToGo(android.util.ArraySet<? extends com.android.server.wm.WindowContainer> arraySet, android.util.ArrayMap<com.android.server.wm.WindowContainer, java.lang.Integer> arrayMap) {
        int i;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 2951634988136738868L, 61, null, java.lang.Long.valueOf(arraySet.size()), java.lang.Boolean.valueOf(this.mService.mDisplayFrozen), java.lang.Boolean.valueOf(this.mDisplayContent.mAppTransition.isTimeout()));
        if (this.mDisplayContent.mAppTransition.isTimeout()) {
            return true;
        }
        com.android.server.wm.ScreenRotationAnimation rotationAnimation = this.mService.mRoot.getDisplayContent(0).getRotationAnimation();
        if (rotationAnimation != null && rotationAnimation.isAnimating() && this.mDisplayContent.getDisplayRotation().needsUpdate()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 4963754906024950916L, 0, null, null);
            return false;
        }
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            com.android.server.wm.ActivityRecord appFromContainer = getAppFromContainer(arraySet.valueAt(i2));
            if (appFromContainer != null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 5073676463280304697L, 1020, null, java.lang.String.valueOf(appFromContainer), java.lang.Boolean.valueOf(appFromContainer.allDrawn), java.lang.Boolean.valueOf(appFromContainer.isStartingWindowDisplayed()), java.lang.Boolean.valueOf(appFromContainer.startingMoved), java.lang.Boolean.valueOf(appFromContainer.isRelaunching()), java.lang.String.valueOf(appFromContainer.mStartingWindow));
                boolean z = appFromContainer.allDrawn && !appFromContainer.isRelaunching();
                if (!z && !appFromContainer.isStartingWindowDisplayed() && !appFromContainer.startingMoved) {
                    return false;
                }
                if (z) {
                    arrayMap.put(appFromContainer, 2);
                } else {
                    if (appFromContainer.mStartingData instanceof com.android.server.wm.SplashScreenStartingData) {
                        i = 1;
                    } else {
                        i = 4;
                    }
                    arrayMap.put(appFromContainer, java.lang.Integer.valueOf(i));
                }
            }
        }
        if (this.mDisplayContent.mAppTransition.isFetchingAppTransitionsSpecs()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 3437142041296647115L, 0, null, null);
            return false;
        }
        if (this.mDisplayContent.mUnknownAppVisibilityController.allResolved()) {
            return !this.mWallpaperControllerLocked.isWallpaperVisible() || this.mWallpaperControllerLocked.wallpaperTransitionReady();
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 1461079689316480707L, 0, null, java.lang.String.valueOf(this.mDisplayContent.mUnknownAppVisibilityController.getDebugMessage()));
        return false;
    }

    private boolean transitionGoodToGoForTaskFragments() {
        if (this.mDisplayContent.mAppTransition.isTimeout()) {
            return true;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (int size = this.mDisplayContent.mOpeningApps.size() - 1; size >= 0; size--) {
            arraySet.add(this.mDisplayContent.mOpeningApps.valueAt(size).getRootTask());
        }
        for (int size2 = this.mDisplayContent.mClosingApps.size() - 1; size2 >= 0; size2--) {
            arraySet.add(this.mDisplayContent.mClosingApps.valueAt(size2).getRootTask());
        }
        for (int size3 = this.mDisplayContent.mChangingContainers.size() - 1; size3 >= 0; size3--) {
            arraySet.add(findRootTaskFromContainer(this.mDisplayContent.mChangingContainers.valueAt(size3)));
        }
        for (int size4 = arraySet.size() - 1; size4 >= 0; size4--) {
            com.android.server.wm.Task task = (com.android.server.wm.Task) arraySet.valueAt(size4);
            if (task != null && task.forAllLeafTaskFragments(new java.util.function.Predicate() { // from class: com.android.server.wm.AppTransitionController$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$transitionGoodToGoForTaskFragments$6;
                    lambda$transitionGoodToGoForTaskFragments$6 = com.android.server.wm.AppTransitionController.lambda$transitionGoodToGoForTaskFragments$6((com.android.server.wm.TaskFragment) obj);
                    return lambda$transitionGoodToGoForTaskFragments$6;
                }
            })) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$transitionGoodToGoForTaskFragments$6(com.android.server.wm.TaskFragment taskFragment) {
        if (!taskFragment.isReadyToTransit()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 3579533288018884842L, 0, null, java.lang.String.valueOf(taskFragment));
            return true;
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isTransitWithinTask(int i, com.android.server.wm.Task task) {
        if (task == null || !this.mDisplayContent.mChangingContainers.isEmpty()) {
            return false;
        }
        if (i != 6 && i != 7 && i != 18) {
            return false;
        }
        java.util.Iterator<com.android.server.wm.ActivityRecord> it = this.mDisplayContent.mOpeningApps.iterator();
        while (it.hasNext()) {
            if (it.next().getTask() != task) {
                return false;
            }
        }
        java.util.Iterator<com.android.server.wm.ActivityRecord> it2 = this.mDisplayContent.mClosingApps.iterator();
        while (it2.hasNext()) {
            if (it2.next().getTask() != task) {
                return false;
            }
        }
        return true;
    }

    private static boolean canBeWallpaperTarget(android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet) {
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            if (arraySet.valueAt(size).windowsCanBeWallpaperTarget()) {
                return true;
            }
        }
        return false;
    }

    private static com.android.server.wm.ActivityRecord getTopApp(android.util.ArraySet<? extends com.android.server.wm.WindowContainer> arraySet, boolean z) {
        int prefixOrderIndex;
        int i = Integer.MIN_VALUE;
        com.android.server.wm.ActivityRecord activityRecord = null;
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord appFromContainer = getAppFromContainer(arraySet.valueAt(size));
            if (appFromContainer != null && ((!z || appFromContainer.isVisible()) && (prefixOrderIndex = appFromContainer.getPrefixOrderIndex()) > i)) {
                activityRecord = appFromContainer;
                i = prefixOrderIndex;
            }
        }
        return activityRecord;
    }
}
