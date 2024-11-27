package com.android.server.wm;

import com.android.server.wm.ActivityTaskManagerService.SleepTokenAcquirerImpl;

/* loaded from: classes3.dex */
class KeyguardController {
    private static final int DEFER_WAKE_TRANSITION_TIMEOUT_MS = 5000;
    static final java.lang.String KEYGUARD_SLEEP_TOKEN_TAG = "keyguard";
    private static final java.lang.String TAG = "ActivityTaskManager";
    private com.android.server.wm.RootWindowContainer mRootWindowContainer;
    private final com.android.server.wm.ActivityTaskManagerService mService;
    private final com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer mSleepTokenAcquirer;
    private final com.android.server.wm.ActivityTaskSupervisor mTaskSupervisor;
    private boolean mWaitingForWakeTransition;
    private com.android.server.wm.WindowManagerService mWindowManager;
    private final android.util.SparseArray<com.android.server.wm.KeyguardController.KeyguardDisplayState> mDisplayStates = new android.util.SparseArray<>();
    private com.android.server.wm.Transition.ReadyCondition mWaitAodHide = null;
    private final java.lang.Runnable mResetWaitTransition = new java.lang.Runnable() { // from class: com.android.server.wm.KeyguardController$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.wm.KeyguardController.this.lambda$new$0();
        }
    };

    KeyguardController(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) {
        this.mService = activityTaskManagerService;
        this.mTaskSupervisor = activityTaskSupervisor;
        com.android.server.wm.ActivityTaskManagerService activityTaskManagerService2 = this.mService;
        java.util.Objects.requireNonNull(activityTaskManagerService2);
        this.mSleepTokenAcquirer = activityTaskManagerService2.new SleepTokenAcquirerImpl(KEYGUARD_SLEEP_TOKEN_TAG);
    }

    void setWindowManager(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mWindowManager = windowManagerService;
        this.mRootWindowContainer = this.mService.mRootWindowContainer;
    }

    boolean isAodShowing(int i) {
        return getDisplayState(i).mAodShowing;
    }

    boolean isKeyguardOrAodShowing(int i) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(i);
        return ((!displayState.mKeyguardShowing && !displayState.mAodShowing) || displayState.mKeyguardGoingAway || displayState.mOccluded) ? false : true;
    }

    boolean isKeyguardUnoccludedOrAodShowing(int i) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(i);
        if (i == 0 && displayState.mAodShowing) {
            return !displayState.mKeyguardGoingAway;
        }
        return isKeyguardOrAodShowing(i);
    }

    boolean isKeyguardShowing(int i) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(i);
        return (!displayState.mKeyguardShowing || displayState.mKeyguardGoingAway || displayState.mOccluded) ? false : true;
    }

    boolean isKeyguardLocked(int i) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(i);
        return displayState.mKeyguardShowing && !displayState.mKeyguardGoingAway;
    }

    boolean isKeyguardOccluded(int i) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(i);
        return displayState.mKeyguardShowing && !displayState.mKeyguardGoingAway && displayState.mOccluded;
    }

    boolean topActivityOccludesKeyguard(com.android.server.wm.ActivityRecord activityRecord) {
        return getDisplayState(activityRecord.getDisplayId()).mTopOccludesActivity == activityRecord;
    }

    boolean isKeyguardGoingAway(int i) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(i);
        return displayState.mKeyguardGoingAway && displayState.mKeyguardShowing;
    }

    /* JADX WARN: Multi-variable type inference failed */
    void setKeyguardShown(int i, boolean z, boolean z2) {
        if (this.mRootWindowContainer.getDisplayContent(i).isKeyguardAlwaysUnlocked()) {
            android.util.Slog.i(TAG, "setKeyguardShown ignoring always unlocked display " + i);
            return;
        }
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(i);
        boolean z3 = true;
        byte b = z2 != displayState.mAodShowing;
        byte b2 = displayState.mAodShowing && !z2;
        byte b3 = displayState.mKeyguardGoingAway && z;
        if (z == displayState.mKeyguardShowing && (b3 == false || b2 != false)) {
            z3 = false;
        }
        if (b2 != false) {
            updateDeferTransitionForAod(false);
        }
        if (!z3 && b == false) {
            setWakeTransitionReady();
            return;
        }
        com.android.server.wm.EventLogTags.writeWmSetKeyguardShown(i, z ? 1 : 0, z2 ? 1 : 0, displayState.mKeyguardGoingAway ? 1 : 0, displayState.mOccluded ? 1 : 0, "setKeyguardShown");
        if (i == 0) {
            if ((((z2 ? 1 : 0) ^ (z ? 1 : 0)) != 0 || (z2 && b != false && z3)) && !displayState.mKeyguardGoingAway && android.view.Display.isOnState(this.mRootWindowContainer.getDefaultDisplay().getDisplayInfo().state)) {
                this.mWindowManager.mTaskSnapshotController.snapshotForSleeping(0);
            }
        }
        displayState.mKeyguardShowing = z;
        displayState.mAodShowing = z2;
        if (z3) {
            displayState.mKeyguardGoingAway = false;
            if (z) {
                displayState.mDismissalRequested = false;
            }
            if (b3 != false) {
                this.mRootWindowContainer.getDefaultDisplay().requestTransitionAndLegacyPrepare(3, 2048);
                this.mWindowManager.executeAppTransition();
            }
        }
        updateKeyguardSleepToken();
        this.mRootWindowContainer.ensureActivitiesVisible();
        com.android.server.inputmethod.InputMethodManagerInternal.get().updateImeWindowStatus(false, i);
        setWakeTransitionReady();
        if (b != false) {
            this.mWindowManager.mWindowPlacerLocked.performSurfacePlacement();
        }
    }

    private void setWakeTransitionReady() {
        if (this.mWindowManager.mAtmService.getTransitionController().getCollectingTransitionType() == 11) {
            this.mWindowManager.mAtmService.getTransitionController().setReady(this.mRootWindowContainer.getDefaultDisplay());
        }
    }

    void keyguardGoingAway(int i, int i2) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(i);
        if (!displayState.mKeyguardShowing || displayState.mKeyguardGoingAway) {
            return;
        }
        android.os.Trace.traceBegin(32L, "keyguardGoingAway");
        this.mService.deferWindowLayout();
        displayState.mKeyguardGoingAway = true;
        try {
            com.android.server.wm.EventLogTags.writeWmSetKeyguardShown(i, displayState.mKeyguardShowing ? 1 : 0, displayState.mAodShowing ? 1 : 0, 1, displayState.mOccluded ? 1 : 0, "keyguardGoingAway");
            int convertTransitFlags = convertTransitFlags(i2);
            com.android.server.wm.DisplayContent defaultDisplay = this.mRootWindowContainer.getDefaultDisplay();
            defaultDisplay.prepareAppTransition(7, convertTransitFlags);
            defaultDisplay.mAtmService.getTransitionController().requestTransitionIfNeeded(4, convertTransitFlags, null, defaultDisplay);
            updateKeyguardSleepToken();
            this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            this.mRootWindowContainer.ensureActivitiesVisible();
            this.mRootWindowContainer.addStartingWindowsForVisibleActivities();
            this.mWindowManager.executeAppTransition();
        } finally {
            this.mService.continueWindowLayout();
            android.os.Trace.traceEnd(32L);
        }
    }

    void dismissKeyguard(android.os.IBinder iBinder, com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) {
        com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
        if (forTokenLocked == null || !forTokenLocked.visibleIgnoringKeyguard) {
            failCallback(iKeyguardDismissCallback);
            return;
        }
        android.util.Slog.i(TAG, "Activity requesting to dismiss Keyguard: " + forTokenLocked);
        if (forTokenLocked.getTurnScreenOnFlag() && forTokenLocked.isTopRunningActivity()) {
            this.mTaskSupervisor.wakeUp("dismissKeyguard");
        }
        this.mWindowManager.dismissKeyguard(iKeyguardDismissCallback, charSequence);
    }

    private void failCallback(com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback) {
        try {
            iKeyguardDismissCallback.onDismissError();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to call callback", e);
        }
    }

    private int convertTransitFlags(int i) {
        int i2;
        if ((i & 1) == 0) {
            i2 = 256;
        } else {
            i2 = 257;
        }
        if ((i & 2) != 0) {
            i2 |= 2;
        }
        if ((i & 4) != 0) {
            i2 |= 4;
        }
        if ((i & 8) != 0) {
            i2 |= 8;
        }
        if ((i & 16) != 0) {
            return i2 | 512;
        }
        return i2;
    }

    boolean canShowActivityWhileKeyguardShowing(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(activityRecord.getDisplayId());
        return activityRecord.containsDismissKeyguardWindow() && canDismissKeyguard() && !displayState.mAodShowing && (displayState.mDismissalRequested || (activityRecord.canShowWhenLocked() && displayState.mDismissingKeyguardActivity != activityRecord));
    }

    boolean canShowWhileOccluded(boolean z, boolean z2) {
        return z2 || (z && !this.mWindowManager.isKeyguardSecure(this.mService.getCurrentUserId()));
    }

    boolean checkKeyguardVisibility(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.mDisplayContent.canShowWithInsecureKeyguard() && canDismissKeyguard()) {
            return true;
        }
        if (isKeyguardOrAodShowing(activityRecord.mDisplayContent.getDisplayId())) {
            return canShowActivityWhileKeyguardShowing(activityRecord);
        }
        if (isKeyguardLocked(activityRecord.getDisplayId())) {
            return canShowWhileOccluded(activityRecord.containsDismissKeyguardWindow(), activityRecord.canShowWhenLocked());
        }
        return true;
    }

    void updateVisibility() {
        for (int childCount = this.mRootWindowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.DisplayContent childAt = this.mRootWindowContainer.getChildAt(childCount);
            if (!childAt.isRemoving() && !childAt.isRemoved()) {
                com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(childAt.mDisplayId);
                displayState.updateVisibility(this, childAt);
                if (displayState.mRequestDismissKeyguard) {
                    handleDismissKeyguard(childAt.getDisplayId());
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOccludedChanged(int i, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        if (i != 0) {
            updateKeyguardSleepToken(i);
            return;
        }
        com.android.server.wm.TransitionController transitionController = this.mRootWindowContainer.mTransitionController;
        boolean z = getDisplayState(i).mOccluded;
        boolean isKeyguardLocked = isKeyguardLocked(i);
        boolean z2 = isKeyguardLocked && !transitionController.isCollecting();
        this.mWindowManager.mPolicy.onKeyguardOccludedChangedLw(z);
        this.mService.deferWindowLayout();
        try {
            if (isKeyguardLocked(i)) {
                if (z) {
                    this.mRootWindowContainer.getDefaultDisplay().requestTransitionAndLegacyPrepare(8, 4096, activityRecord != null ? activityRecord.getRootTask() : null);
                } else {
                    this.mRootWindowContainer.getDefaultDisplay().requestTransitionAndLegacyPrepare(9, 8192);
                }
            } else if (transitionController.inTransition()) {
                java.util.ArrayList<java.lang.Runnable> arrayList = transitionController.mStateValidators;
                com.android.server.policy.WindowManagerPolicy windowManagerPolicy = this.mWindowManager.mPolicy;
                java.util.Objects.requireNonNull(windowManagerPolicy);
                arrayList.add(new com.android.server.wm.KeyguardController$$ExternalSyntheticLambda0(windowManagerPolicy));
            } else {
                this.mWindowManager.mPolicy.applyKeyguardOcclusionChange();
            }
            updateKeyguardSleepToken(i);
            if (isKeyguardLocked && z2) {
                this.mWindowManager.executeAppTransition();
            }
            this.mService.continueWindowLayout();
        } catch (java.lang.Throwable th) {
            this.mService.continueWindowLayout();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleKeyguardGoingAwayChanged(com.android.server.wm.DisplayContent displayContent) {
        this.mService.deferWindowLayout();
        try {
            displayContent.prepareAppTransition(7, 0);
            displayContent.mAtmService.getTransitionController().requestTransitionIfNeeded(1, 256, null, displayContent);
            updateKeyguardSleepToken();
            this.mWindowManager.executeAppTransition();
        } finally {
            this.mService.continueWindowLayout();
        }
    }

    private void handleDismissKeyguard(int i) {
        if (!this.mWindowManager.isKeyguardSecure(this.mService.getCurrentUserId())) {
            return;
        }
        this.mWindowManager.dismissKeyguard(null, null);
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(i);
        displayState.mDismissalRequested = true;
        com.android.server.wm.DisplayContent defaultDisplay = this.mRootWindowContainer.getDefaultDisplay();
        if (displayState.mKeyguardShowing && canDismissKeyguard() && defaultDisplay.mAppTransition.containsTransitRequest(9)) {
            this.mWindowManager.executeAppTransition();
        }
    }

    com.android.server.wm.ActivityRecord getTopOccludingActivity(int i) {
        return getDisplayState(i).mTopOccludesActivity;
    }

    com.android.server.wm.ActivityRecord getDismissKeyguardActivity(int i) {
        return getDisplayState(i).mDismissingKeyguardActivity;
    }

    boolean canDismissKeyguard() {
        return this.mWindowManager.mPolicy.isKeyguardTrustedLw() || !this.mWindowManager.isKeyguardSecure(this.mService.getCurrentUserId());
    }

    boolean isShowingDream() {
        return getDisplayState(0).mShowingDream;
    }

    private void updateKeyguardSleepToken() {
        for (int childCount = this.mRootWindowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
            updateKeyguardSleepToken(this.mRootWindowContainer.getChildAt(childCount).mDisplayId);
        }
    }

    private void updateKeyguardSleepToken(int i) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(i);
        if (isKeyguardUnoccludedOrAodShowing(i)) {
            displayState.mSleepTokenAcquirer.acquire(i);
        } else {
            displayState.mSleepTokenAcquirer.release(i);
        }
    }

    private com.android.server.wm.KeyguardController.KeyguardDisplayState getDisplayState(int i) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState keyguardDisplayState = this.mDisplayStates.get(i);
        if (keyguardDisplayState == null) {
            com.android.server.wm.KeyguardController.KeyguardDisplayState keyguardDisplayState2 = new com.android.server.wm.KeyguardController.KeyguardDisplayState(this.mService, i, this.mSleepTokenAcquirer);
            this.mDisplayStates.append(i, keyguardDisplayState2);
            return keyguardDisplayState2;
        }
        return keyguardDisplayState;
    }

    void onDisplayRemoved(int i) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState keyguardDisplayState = this.mDisplayStates.get(i);
        if (keyguardDisplayState != null) {
            keyguardDisplayState.onRemoved();
            this.mDisplayStates.remove(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWindowManager.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                updateDeferTransitionForAod(false);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void updateDeferTransitionForAod(boolean z) {
        if (this.mService.getTransitionController().useFullReadyTracking()) {
            if (z == (this.mWaitAodHide != null)) {
                return;
            }
        } else if (z == this.mWaitingForWakeTransition) {
            return;
        }
        if (!this.mService.getTransitionController().isCollecting()) {
            return;
        }
        if (z && isAodShowing(0)) {
            this.mWaitingForWakeTransition = true;
            this.mWindowManager.mAtmService.getTransitionController().deferTransitionReady();
            this.mWaitAodHide = new com.android.server.wm.Transition.ReadyCondition("AOD hidden");
            this.mWindowManager.mAtmService.getTransitionController().waitFor(this.mWaitAodHide);
            this.mWindowManager.mH.postDelayed(this.mResetWaitTransition, 5000L);
            return;
        }
        if (!z) {
            this.mWaitingForWakeTransition = false;
            this.mWindowManager.mAtmService.getTransitionController().continueTransitionReady();
            this.mWindowManager.mH.removeCallbacks(this.mResetWaitTransition);
            com.android.server.wm.Transition.ReadyCondition readyCondition = this.mWaitAodHide;
            this.mWaitAodHide = null;
            readyCondition.meet();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class KeyguardDisplayState {
        private boolean mAodShowing;
        private boolean mDismissalRequested;
        private com.android.server.wm.ActivityRecord mDismissingKeyguardActivity;
        private final int mDisplayId;
        private boolean mKeyguardGoingAway;
        private boolean mKeyguardShowing;
        private boolean mOccluded;
        private boolean mRequestDismissKeyguard;
        private final com.android.server.wm.ActivityTaskManagerService mService;
        private boolean mShowingDream;
        private final com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer mSleepTokenAcquirer;
        private com.android.server.wm.ActivityRecord mTopOccludesActivity;
        private com.android.server.wm.ActivityRecord mTopTurnScreenOnActivity;

        KeyguardDisplayState(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, int i, com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer sleepTokenAcquirer) {
            this.mService = activityTaskManagerService;
            this.mDisplayId = i;
            this.mSleepTokenAcquirer = sleepTokenAcquirer;
        }

        void onRemoved() {
            this.mTopOccludesActivity = null;
            this.mDismissingKeyguardActivity = null;
            this.mTopTurnScreenOnActivity = null;
            this.mSleepTokenAcquirer.release(this.mDisplayId);
        }

        void updateVisibility(com.android.server.wm.KeyguardController keyguardController, com.android.server.wm.DisplayContent displayContent) {
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            boolean z5;
            boolean z6;
            boolean z7 = this.mOccluded;
            boolean z8 = this.mKeyguardGoingAway;
            com.android.server.wm.ActivityRecord activityRecord = this.mDismissingKeyguardActivity;
            boolean z9 = false;
            this.mRequestDismissKeyguard = false;
            this.mOccluded = false;
            this.mShowingDream = false;
            this.mTopOccludesActivity = null;
            this.mDismissingKeyguardActivity = null;
            this.mTopTurnScreenOnActivity = null;
            com.android.server.wm.Task rootTaskForControllingOccluding = getRootTaskForControllingOccluding(displayContent);
            com.android.server.wm.ActivityRecord topNonFinishingActivity = rootTaskForControllingOccluding != null ? rootTaskForControllingOccluding.getTopNonFinishingActivity() : null;
            if (topNonFinishingActivity == null) {
                z = false;
            } else {
                if (topNonFinishingActivity.containsDismissKeyguardWindow()) {
                    this.mDismissingKeyguardActivity = topNonFinishingActivity;
                }
                if (topNonFinishingActivity.getTurnScreenOnFlag() && topNonFinishingActivity.currentLaunchCanTurnScreenOn()) {
                    this.mTopTurnScreenOnActivity = topNonFinishingActivity;
                }
                boolean isKeyguardSecure = keyguardController.mWindowManager.isKeyguardSecure(keyguardController.mService.getCurrentUserId());
                if (topNonFinishingActivity.mDismissKeyguardIfInsecure && this.mKeyguardShowing && !isKeyguardSecure) {
                    this.mKeyguardGoingAway = true;
                } else if (topNonFinishingActivity.canShowWhenLocked()) {
                    this.mTopOccludesActivity = topNonFinishingActivity;
                }
                topNonFinishingActivity.mDismissKeyguardIfInsecure = false;
                if (this.mTopOccludesActivity == null && (this.mDismissingKeyguardActivity == null || rootTaskForControllingOccluding.topRunningActivity() != this.mDismissingKeyguardActivity || !keyguardController.canShowWhileOccluded(true, false))) {
                    z5 = false;
                } else {
                    z5 = true;
                }
                z = z5;
                if (this.mDisplayId != 0) {
                    if (!displayContent.canShowWithInsecureKeyguard() || !keyguardController.canDismissKeyguard()) {
                        z6 = false;
                    } else {
                        z6 = true;
                    }
                    z = z5 | z6;
                }
            }
            if (!displayContent.getDisplayPolicy().isShowingDreamLw() || topNonFinishingActivity == null || topNonFinishingActivity.getActivityType() != 5) {
                z2 = false;
            } else {
                z2 = true;
            }
            this.mShowingDream = z2;
            if (!this.mShowingDream && !z) {
                z3 = false;
            } else {
                z3 = true;
            }
            this.mOccluded = z3;
            if (activityRecord == this.mDismissingKeyguardActivity || this.mOccluded || this.mKeyguardGoingAway || this.mDismissingKeyguardActivity == null) {
                z4 = false;
            } else {
                z4 = true;
            }
            this.mRequestDismissKeyguard = z4;
            if (this.mOccluded && this.mKeyguardShowing && !displayContent.isSleeping() && !topNonFinishingActivity.fillsParent() && displayContent.mWallpaperController.getWallpaperTarget() == null) {
                displayContent.pendingLayoutChanges |= 4;
            }
            if (this.mTopTurnScreenOnActivity != null && !this.mService.mWindowManager.mPowerManager.isInteractive() && (this.mRequestDismissKeyguard || z)) {
                keyguardController.mTaskSupervisor.wakeUp("handleTurnScreenOn");
                this.mTopTurnScreenOnActivity.setCurrentLaunchCanTurnScreenOn(false);
            }
            if (z7 != this.mOccluded) {
                if (this.mDisplayId == 0) {
                    com.android.server.wm.EventLogTags.writeWmSetKeyguardShown(this.mDisplayId, this.mKeyguardShowing ? 1 : 0, this.mAodShowing ? 1 : 0, this.mKeyguardGoingAway ? 1 : 0, this.mOccluded ? 1 : 0, "updateVisibility");
                }
                keyguardController.handleOccludedChanged(this.mDisplayId, this.mTopOccludesActivity);
                z9 = true;
            } else if (!z8 && this.mKeyguardGoingAway) {
                keyguardController.handleKeyguardGoingAwayChanged(displayContent);
                z9 = true;
            }
            if (!z9 || topNonFinishingActivity == null) {
                return;
            }
            if (this.mOccluded || this.mKeyguardGoingAway) {
                displayContent.mTransitionController.collect(topNonFinishingActivity);
            }
        }

        @android.annotation.Nullable
        private com.android.server.wm.Task getRootTaskForControllingOccluding(com.android.server.wm.DisplayContent displayContent) {
            return displayContent.getRootTask(new java.util.function.Predicate() { // from class: com.android.server.wm.KeyguardController$KeyguardDisplayState$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$getRootTaskForControllingOccluding$0;
                    lambda$getRootTaskForControllingOccluding$0 = com.android.server.wm.KeyguardController.KeyguardDisplayState.lambda$getRootTaskForControllingOccluding$0((com.android.server.wm.Task) obj);
                    return lambda$getRootTaskForControllingOccluding$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$getRootTaskForControllingOccluding$0(com.android.server.wm.Task task) {
            return (task == null || !task.isFocusableAndVisible() || task.inPinnedWindowingMode()) ? false : true;
        }

        void dumpStatus(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.println(str + " KeyguardShowing=" + this.mKeyguardShowing + " AodShowing=" + this.mAodShowing + " KeyguardGoingAway=" + this.mKeyguardGoingAway + " DismissalRequested=" + this.mDismissalRequested + "  Occluded=" + this.mOccluded + " DismissingKeyguardActivity=" + this.mDismissingKeyguardActivity + " TurnScreenOnActivity=" + this.mTopTurnScreenOnActivity + " at display=" + this.mDisplayId);
        }

        void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.mDisplayId);
            protoOutputStream.write(1133871366146L, this.mKeyguardShowing);
            protoOutputStream.write(1133871366147L, this.mAodShowing);
            protoOutputStream.write(1133871366148L, this.mOccluded);
            protoOutputStream.write(1133871366149L, this.mKeyguardGoingAway);
            protoOutputStream.end(start);
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(0);
        printWriter.println(str + "KeyguardController:");
        printWriter.println(str + "  mKeyguardShowing=" + displayState.mKeyguardShowing);
        printWriter.println(str + "  mAodShowing=" + displayState.mAodShowing);
        printWriter.println(str + "  mKeyguardGoingAway=" + displayState.mKeyguardGoingAway);
        dumpDisplayStates(printWriter, str);
        printWriter.println(str + "  mDismissalRequested=" + displayState.mDismissalRequested);
        printWriter.println();
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        com.android.server.wm.KeyguardController.KeyguardDisplayState displayState = getDisplayState(0);
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366147L, displayState.mAodShowing);
        protoOutputStream.write(1133871366145L, displayState.mKeyguardShowing);
        protoOutputStream.write(1133871366149L, displayState.mKeyguardGoingAway);
        writeDisplayStatesToProto(protoOutputStream, 2246267895812L);
        protoOutputStream.end(start);
    }

    private void dumpDisplayStates(java.io.PrintWriter printWriter, java.lang.String str) {
        for (int i = 0; i < this.mDisplayStates.size(); i++) {
            this.mDisplayStates.valueAt(i).dumpStatus(printWriter, str);
        }
    }

    private void writeDisplayStatesToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        for (int i = 0; i < this.mDisplayStates.size(); i++) {
            this.mDisplayStates.valueAt(i).dumpDebug(protoOutputStream, j);
        }
    }
}
