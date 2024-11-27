package com.android.server.wm;

/* loaded from: classes3.dex */
class TransitionController {
    private static final int CHANGE_TIMEOUT_MS = 2000;
    private static final int DEFAULT_TIMEOUT_MS = 5000;
    private static final int LEGACY_STATE_IDLE = 0;
    private static final int LEGACY_STATE_READY = 1;
    private static final int LEGACY_STATE_RUNNING = 2;
    private static final boolean SHELL_TRANSITIONS_ROTATION = android.os.SystemProperties.getBoolean("persist.wm.debug.shell_transit_rotate", false);
    static final int SYNC_METHOD;
    private static final java.lang.String TAG = "TransitionController";
    final com.android.server.wm.ActivityTaskManagerService mAtm;
    com.android.server.wm.Transition mFinishingTransition;
    final com.android.server.wm.TransitionController.RemotePlayer mRemotePlayer;
    com.android.server.wm.SnapshotController mSnapshotController;
    com.android.server.wm.BLASTSyncEngine mSyncEngine;
    private android.window.ITransitionPlayer mTransitionPlayer;
    private com.android.server.wm.WindowProcessController mTransitionPlayerProc;
    com.android.server.wm.TransitionTracer mTransitionTracer;
    final com.android.server.wm.TransitionController.TransitionMetricsReporter mTransitionMetricsReporter = new com.android.server.wm.TransitionController.TransitionMetricsReporter();
    private boolean mFullReadyTracking = false;
    private final java.util.ArrayList<com.android.server.wm.WindowManagerInternal.AppTransitionListener> mLegacyListeners = new java.util.ArrayList<>();
    final java.util.ArrayList<java.lang.Runnable> mStateValidators = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.wm.ActivityRecord> mValidateCommitVis = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.wm.ActivityRecord> mValidateActivityCompat = new java.util.ArrayList<>();
    final java.util.ArrayList<com.android.server.wm.DisplayArea> mValidateDisplayVis = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.Transition> mPlayingTransitions = new java.util.ArrayList<>();
    int mTrackCount = 0;
    final java.util.ArrayList<com.android.server.wm.WindowState> mAnimatingExitWindows = new java.util.ArrayList<>();
    final com.android.server.wm.TransitionController.Lock mRunningLock = new com.android.server.wm.TransitionController.Lock();
    private final java.util.ArrayList<com.android.server.wm.TransitionController.QueuedTransition> mQueuedTransitions = new java.util.ArrayList<>();
    private com.android.server.wm.Transition mCollectingTransition = null;
    final java.util.ArrayList<com.android.server.wm.Transition> mWaitingTransitions = new java.util.ArrayList<>();
    final android.util.SparseArray<java.util.ArrayList<com.android.server.wm.Task>> mLatestOnTopTasksReported = new android.util.SparseArray<>();
    boolean mBuildingFinishLayers = false;
    boolean mNavigationBarAttachedToApp = false;
    private boolean mAnimatingState = false;
    final android.os.Handler mLoggerHandler = com.android.server.FgThread.getHandler();
    boolean mIsWaitingForDisplayEnabled = false;
    private final android.os.IBinder.DeathRecipient mTransitionPlayerDeath = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda7
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            com.android.server.wm.TransitionController.this.lambda$new$0();
        }
    };

    interface OnStartCollect {
        void onCollectStarted(boolean z);
    }

    static {
        SYNC_METHOD = android.os.SystemProperties.getBoolean("persist.wm.debug.shell_transit_blast", false) ? 1 : 0;
    }

    static class QueuedTransition {
        final com.android.server.wm.BLASTSyncEngine.SyncGroup mLegacySync;
        final com.android.server.wm.TransitionController.OnStartCollect mOnStartCollect;
        final com.android.server.wm.Transition mTransition;

        QueuedTransition(com.android.server.wm.Transition transition, com.android.server.wm.TransitionController.OnStartCollect onStartCollect) {
            this.mTransition = transition;
            this.mOnStartCollect = onStartCollect;
            this.mLegacySync = null;
        }

        QueuedTransition(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup, com.android.server.wm.TransitionController.OnStartCollect onStartCollect) {
            this.mTransition = null;
            this.mOnStartCollect = onStartCollect;
            this.mLegacySync = syncGroup;
        }
    }

    TransitionController(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mRemotePlayer = new com.android.server.wm.TransitionController.RemotePlayer(activityTaskManagerService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                detachPlayer();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void setWindowManager(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mSnapshotController = windowManagerService.mSnapshotController;
        this.mTransitionTracer = windowManagerService.mTransitionTracer;
        this.mIsWaitingForDisplayEnabled = !windowManagerService.mDisplayEnabled;
        registerLegacyListener(windowManagerService.mActivityManagerAppTransitionNotifier);
        setSyncEngine(windowManagerService.mSyncEngine);
        this.mFullReadyTracking = com.android.window.flags.Flags.transitReadyTracking();
    }

    @com.android.internal.annotations.VisibleForTesting
    void setSyncEngine(com.android.server.wm.BLASTSyncEngine bLASTSyncEngine) {
        this.mSyncEngine = bLASTSyncEngine;
        this.mSyncEngine.addOnIdleListener(new java.lang.Runnable() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.TransitionController.this.tryStartCollectFromQueue();
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    void detachPlayer() {
        if (this.mTransitionPlayer == null) {
            return;
        }
        this.mTransitionPlayer = null;
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            this.mPlayingTransitions.get(size).cleanUpOnFailure();
        }
        this.mPlayingTransitions.clear();
        for (int size2 = this.mWaitingTransitions.size() - 1; size2 >= 0; size2--) {
            this.mWaitingTransitions.get(size2).abort();
        }
        this.mWaitingTransitions.clear();
        if (this.mCollectingTransition != null) {
            this.mCollectingTransition.abort();
        }
        this.mTransitionPlayerProc = null;
        this.mRemotePlayer.clear();
        this.mRunningLock.doNotifyLocked();
    }

    @android.annotation.NonNull
    com.android.server.wm.Transition createTransition(int i) {
        return createTransition(i, 0);
    }

    @android.annotation.NonNull
    private com.android.server.wm.Transition createTransition(int i, int i2) {
        if (this.mTransitionPlayer == null) {
            throw new java.lang.IllegalStateException("Shell Transitions not enabled");
        }
        if (this.mCollectingTransition != null) {
            throw new java.lang.IllegalStateException("Trying to directly start transition collection while  collection is already ongoing. Use {@link #startCollectOrQueue} if possible.");
        }
        com.android.server.wm.Transition transition = new com.android.server.wm.Transition(i, i2, this, this.mSyncEngine);
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -233096875591058130L, 0, null, java.lang.String.valueOf(transition));
        moveToCollecting(transition);
        return transition;
    }

    void moveToCollecting(@android.annotation.NonNull com.android.server.wm.Transition transition) {
        if (this.mCollectingTransition != null) {
            throw new java.lang.IllegalStateException("Simultaneous transition collection not supported.");
        }
        if (this.mTransitionPlayer == null) {
            transition.abort();
            return;
        }
        this.mCollectingTransition = transition;
        this.mCollectingTransition.startCollecting(transition.mType == 6 ? 2000L : 5000L);
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 2154694726162725342L, 0, null, java.lang.String.valueOf(this.mCollectingTransition));
        dispatchLegacyAppTransitionPending();
    }

    void registerTransitionPlayer(@android.annotation.Nullable android.window.ITransitionPlayer iTransitionPlayer, @android.annotation.Nullable com.android.server.wm.WindowProcessController windowProcessController) {
        try {
            if (this.mTransitionPlayer != null) {
                if (this.mTransitionPlayer.asBinder() != null) {
                    this.mTransitionPlayer.asBinder().unlinkToDeath(this.mTransitionPlayerDeath, 0);
                }
                detachPlayer();
            }
            if (iTransitionPlayer.asBinder() != null) {
                iTransitionPlayer.asBinder().linkToDeath(this.mTransitionPlayerDeath, 0);
            }
            this.mTransitionPlayer = iTransitionPlayer;
            this.mTransitionPlayerProc = windowProcessController;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unable to set transition player");
        }
    }

    @android.annotation.Nullable
    android.window.ITransitionPlayer getTransitionPlayer() {
        return this.mTransitionPlayer;
    }

    boolean isShellTransitionsEnabled() {
        return this.mTransitionPlayer != null;
    }

    boolean useShellTransitionsRotation() {
        return isShellTransitionsEnabled() && SHELL_TRANSITIONS_ROTATION;
    }

    boolean useFullReadyTracking() {
        return this.mFullReadyTracking;
    }

    void setFullReadyTrackingForTest(boolean z) {
        this.mFullReadyTracking = z;
    }

    boolean isCollecting() {
        return this.mCollectingTransition != null;
    }

    @android.annotation.Nullable
    com.android.server.wm.Transition getCollectingTransition() {
        return this.mCollectingTransition;
    }

    int getCollectingTransitionId() {
        if (this.mCollectingTransition == null) {
            throw new java.lang.IllegalStateException("There is no collecting transition");
        }
        return this.mCollectingTransition.getSyncId();
    }

    boolean isCollecting(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (this.mCollectingTransition == null) {
            return false;
        }
        if (this.mCollectingTransition.mParticipants.contains(windowContainer)) {
            return true;
        }
        for (int i = 0; i < this.mWaitingTransitions.size(); i++) {
            if (this.mWaitingTransitions.get(i).mParticipants.contains(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    boolean inCollectingTransition(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (!isCollecting()) {
            return false;
        }
        if (this.mCollectingTransition.isInTransition(windowContainer)) {
            return true;
        }
        for (int i = 0; i < this.mWaitingTransitions.size(); i++) {
            if (this.mWaitingTransitions.get(i).isInTransition(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    boolean isPlaying() {
        return !this.mPlayingTransitions.isEmpty();
    }

    boolean inPlayingTransition(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            if (this.mPlayingTransitions.get(size).isInTransition(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    boolean inFinishingTransition(com.android.server.wm.WindowContainer<?> windowContainer) {
        return this.mFinishingTransition != null && this.mFinishingTransition.isInTransition(windowContainer);
    }

    boolean inTransition() {
        return isCollecting() || isPlaying() || !this.mQueuedTransitions.isEmpty();
    }

    boolean inTransition(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        return inCollectingTransition(windowContainer) || inPlayingTransition(windowContainer);
    }

    boolean inTransition(int i) {
        if (this.mCollectingTransition != null && this.mCollectingTransition.getSyncId() == i) {
            return true;
        }
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            if (this.mPlayingTransitions.get(size).getSyncId() == i) {
                return true;
            }
        }
        return false;
    }

    boolean isTransitionOnDisplay(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        if (this.mCollectingTransition != null && this.mCollectingTransition.isOnDisplay(displayContent)) {
            return true;
        }
        for (int size = this.mWaitingTransitions.size() - 1; size >= 0; size--) {
            if (this.mWaitingTransitions.get(size).isOnDisplay(displayContent)) {
                return true;
            }
        }
        for (int size2 = this.mPlayingTransitions.size() - 1; size2 >= 0; size2--) {
            if (this.mPlayingTransitions.get(size2).isOnDisplay(displayContent)) {
                return true;
            }
        }
        return false;
    }

    boolean isTransientHide(@android.annotation.NonNull com.android.server.wm.Task task) {
        if (this.mCollectingTransition != null && this.mCollectingTransition.isInTransientHide(task)) {
            return true;
        }
        for (int size = this.mWaitingTransitions.size() - 1; size >= 0; size--) {
            if (this.mWaitingTransitions.get(size).isInTransientHide(task)) {
                return true;
            }
        }
        for (int size2 = this.mPlayingTransitions.size() - 1; size2 >= 0; size2--) {
            if (this.mPlayingTransitions.get(size2).isInTransientHide(task)) {
                return true;
            }
        }
        return false;
    }

    boolean isTransientVisible(@android.annotation.NonNull com.android.server.wm.Task task) {
        if (this.mCollectingTransition != null && this.mCollectingTransition.isTransientVisible(task)) {
            return true;
        }
        for (int size = this.mWaitingTransitions.size() - 1; size >= 0; size--) {
            if (this.mWaitingTransitions.get(size).isTransientVisible(task)) {
                return true;
            }
        }
        for (int size2 = this.mPlayingTransitions.size() - 1; size2 >= 0; size2--) {
            if (this.mPlayingTransitions.get(size2).isTransientVisible(task)) {
                return true;
            }
        }
        return false;
    }

    boolean canApplyDim(@android.annotation.Nullable com.android.server.wm.Task task) {
        if (task == null) {
            return true;
        }
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            if (!this.mPlayingTransitions.get(size).canApplyDim(task)) {
                return false;
            }
        }
        return true;
    }

    boolean shouldKeepFocus(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (this.mCollectingTransition != null) {
            if (this.mPlayingTransitions.isEmpty()) {
                return this.mCollectingTransition.isInTransientHide(windowContainer);
            }
            return false;
        }
        if (this.mPlayingTransitions.size() == 1) {
            return this.mPlayingTransitions.get(0).isInTransientHide(windowContainer);
        }
        return false;
    }

    boolean isTransientCollect(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        return this.mCollectingTransition != null && this.mCollectingTransition.isTransientLaunch(activityRecord);
    }

    boolean isTransientLaunch(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        if (isTransientCollect(activityRecord)) {
            return true;
        }
        for (int size = this.mPlayingTransitions.size() - 1; size >= 0; size--) {
            if (this.mPlayingTransitions.get(size).isTransientLaunch(activityRecord)) {
                return true;
            }
        }
        return false;
    }

    boolean canAssignLayers(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (this.mBuildingFinishLayers) {
            return windowContainer.asWindowState() == null;
        }
        if (windowContainer.asWindowState() == null) {
            if (isPlaying()) {
                return false;
            }
            if (windowContainer.asTask() != null && isCollecting()) {
                return false;
            }
        }
        return true;
    }

    int getWindowingModeAtStart(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (this.mCollectingTransition == null) {
            return windowContainer.getWindowingMode();
        }
        com.android.server.wm.Transition.ChangeInfo changeInfo = this.mCollectingTransition.mChanges.get(windowContainer);
        if (changeInfo == null) {
            return windowContainer.getWindowingMode();
        }
        return changeInfo.mWindowingMode;
    }

    int getCollectingTransitionType() {
        if (this.mCollectingTransition != null) {
            return this.mCollectingTransition.mType;
        }
        return 0;
    }

    boolean hasCollectingRotationChange(@android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer, int i) {
        com.android.server.wm.Transition.ChangeInfo changeInfo;
        com.android.server.wm.Transition transition = this.mCollectingTransition;
        return (transition == null || !transition.mParticipants.contains(windowContainer) || (changeInfo = transition.mChanges.get(windowContainer)) == null || changeInfo.mRotation == i) ? false : true;
    }

    @android.annotation.Nullable
    com.android.server.wm.Transition requestTransitionIfNeeded(int i, @android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        return requestTransitionIfNeeded(i, 0, windowContainer, windowContainer);
    }

    @android.annotation.Nullable
    com.android.server.wm.Transition requestTransitionIfNeeded(int i, int i2, @android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer2) {
        return requestTransitionIfNeeded(i, i2, windowContainer, windowContainer2, null, null);
    }

    private static boolean isExistenceType(int i) {
        return i == 1 || i == 2;
    }

    private void setDisplaySyncMethod(@android.annotation.NonNull android.window.TransitionRequestInfo.DisplayChange displayChange, @android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        android.graphics.Rect startAbsBounds = displayChange.getStartAbsBounds();
        android.graphics.Rect endAbsBounds = displayChange.getEndAbsBounds();
        if (startAbsBounds == null || endAbsBounds == null) {
            return;
        }
        setDisplaySyncMethod(startAbsBounds, endAbsBounds, displayContent);
    }

    void setDisplaySyncMethod(@android.annotation.NonNull android.graphics.Rect rect, @android.annotation.NonNull android.graphics.Rect rect2, @android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        int width = rect.width();
        int height = rect.height();
        int width2 = rect2.width();
        int height2 = rect2.height();
        if ((width2 > width) == (height2 > height)) {
            if (width2 != width || height2 != height) {
                displayContent.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.TransitionController.lambda$setDisplaySyncMethod$1((com.android.server.wm.WindowState) obj);
                    }
                }, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setDisplaySyncMethod$1(com.android.server.wm.WindowState windowState) {
        if (windowState.mToken.mRoundedCornerOverlay && windowState.mHasSurface) {
            windowState.mSyncMethodOverride = 1;
        }
    }

    @android.annotation.Nullable
    com.android.server.wm.Transition requestTransitionIfNeeded(int i, int i2, @android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer2, @android.annotation.Nullable android.window.RemoteTransition remoteTransition, @android.annotation.Nullable android.window.TransitionRequestInfo.DisplayChange displayChange) {
        r1 = null;
        com.android.server.wm.Transition requestStartTransition = null;
        if (this.mTransitionPlayer == null) {
            return null;
        }
        if (isCollecting()) {
            if (displayChange != null) {
                android.util.Slog.e(TAG, "Provided displayChange for a non-new request", new java.lang.Throwable());
            }
            this.mCollectingTransition.setReady(windowContainer2, false);
            int i3 = i2 & 14592;
            if (i3 != 0) {
                this.mCollectingTransition.addFlag(i3);
            }
        } else {
            requestStartTransition = requestStartTransition(createTransition(i, i2), windowContainer != null ? windowContainer.asTask() : null, remoteTransition, displayChange);
            if (requestStartTransition != null && displayChange != null && windowContainer != null && windowContainer.asDisplayContent() != null) {
                setDisplaySyncMethod(displayChange, windowContainer.asDisplayContent());
            }
        }
        if (windowContainer != null) {
            if (isExistenceType(i)) {
                collectExistenceChange(windowContainer);
            } else {
                collect(windowContainer);
            }
        }
        return requestStartTransition;
    }

    @android.annotation.NonNull
    com.android.server.wm.Transition requestStartTransition(@android.annotation.NonNull final com.android.server.wm.Transition transition, @android.annotation.Nullable com.android.server.wm.Task task, @android.annotation.Nullable android.window.RemoteTransition remoteTransition, @android.annotation.Nullable android.window.TransitionRequestInfo.DisplayChange displayChange) {
        android.app.ActivityManager.RunningTaskInfo runningTaskInfo;
        android.app.ActivityManager.RunningTaskInfo runningTaskInfo2;
        if (this.mIsWaitingForDisplayEnabled) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -4235778637051052061L, 1, null, java.lang.Long.valueOf(transition.getSyncId()));
            transition.mIsPlayerEnabled = false;
            transition.mLogger.mRequestTimeNs = android.os.SystemClock.uptimeNanos();
            this.mAtm.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.TransitionController.this.lambda$requestStartTransition$2(transition);
                }
            });
            return transition;
        }
        if (this.mTransitionPlayer == null || transition.isAborted()) {
            if (transition.isCollecting()) {
                transition.abort();
            }
            return transition;
        }
        try {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 4005704720444963797L, 0, null, java.lang.String.valueOf(transition));
            if (task == null) {
                runningTaskInfo = null;
            } else {
                runningTaskInfo = task.getTaskInfo();
            }
            if (transition.getPipActivity() == null) {
                runningTaskInfo2 = null;
            } else {
                android.app.ActivityManager.RunningTaskInfo taskInfo = transition.getPipActivity().getTask().getTaskInfo();
                transition.setPipActivity(null);
                runningTaskInfo2 = taskInfo;
            }
            android.window.TransitionRequestInfo transitionRequestInfo = new android.window.TransitionRequestInfo(transition.mType, runningTaskInfo, runningTaskInfo2, remoteTransition, displayChange, transition.getFlags(), transition.getSyncId());
            transition.mLogger.mRequestTimeNs = android.os.SystemClock.elapsedRealtimeNanos();
            transition.mLogger.mRequest = transitionRequestInfo;
            this.mTransitionPlayer.requestStartTransition(transition.getToken(), transitionRequestInfo);
            if (remoteTransition != null) {
                transition.setRemoteAnimationApp(remoteTransition.getAppThread());
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error requesting transition", e);
            transition.start();
        }
        return transition;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestStartTransition$2(com.android.server.wm.Transition transition) {
        this.mAtm.mWindowOrganizerController.startTransition(transition.getToken(), null);
    }

    com.android.server.wm.Transition requestCloseTransitionIfNeeded(@android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer) {
        if (this.mTransitionPlayer == null) {
            return null;
        }
        if (windowContainer.isVisibleRequested()) {
            r1 = isCollecting() ? null : requestStartTransition(createTransition(2, 0), windowContainer.asTask(), null, null);
            collectExistenceChange(windowContainer);
        } else {
            collect(windowContainer);
        }
        return r1;
    }

    void collect(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (this.mCollectingTransition == null) {
            return;
        }
        this.mCollectingTransition.collect(windowContainer);
    }

    void collectExistenceChange(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (this.mCollectingTransition == null) {
            return;
        }
        this.mCollectingTransition.collectExistenceChange(windowContainer);
    }

    void recordTaskOrder(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (this.mCollectingTransition == null) {
            return;
        }
        this.mCollectingTransition.recordTaskOrder(windowContainer);
    }

    boolean hasOrderChanges() {
        if (this.mCollectingTransition == null) {
            return false;
        }
        return this.mCollectingTransition.hasOrderChanges();
    }

    void collectForDisplayAreaChange(@android.annotation.NonNull com.android.server.wm.DisplayArea<?> displayArea) {
        final com.android.server.wm.Transition transition = this.mCollectingTransition;
        if (transition == null || !transition.mParticipants.contains(displayArea)) {
            return;
        }
        transition.collectVisibleChange(displayArea);
        displayArea.forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.TransitionController.lambda$collectForDisplayAreaChange$3(com.android.server.wm.Transition.this, (com.android.server.wm.Task) obj);
            }
        }, true);
        com.android.server.wm.DisplayContent asDisplayContent = displayArea.asDisplayContent();
        if (asDisplayContent != null) {
            final boolean z = asDisplayContent.getAsyncRotationController() == null;
            displayArea.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.TransitionController.this.lambda$collectForDisplayAreaChange$4(z, transition, (com.android.server.wm.WindowState) obj);
                }
            }, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$collectForDisplayAreaChange$3(com.android.server.wm.Transition transition, com.android.server.wm.Task task) {
        if (task.isVisible()) {
            transition.collect(task);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$collectForDisplayAreaChange$4(boolean z, com.android.server.wm.Transition transition, com.android.server.wm.WindowState windowState) {
        if (windowState.mActivityRecord == null && windowState.isVisible() && !isCollecting(windowState.mToken)) {
            if (z || !com.android.server.wm.AsyncRotationController.canBeAsync(windowState.mToken)) {
                transition.collect(windowState.mToken);
            }
        }
    }

    void collectVisibleChange(com.android.server.wm.WindowContainer windowContainer) {
        if (isCollecting()) {
            this.mCollectingTransition.collectVisibleChange(windowContainer);
        }
    }

    void collectReparentChange(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer2) {
        if (isCollecting()) {
            this.mCollectingTransition.collectReparentChange(windowContainer, windowContainer2);
        }
    }

    void setStatusBarTransitionDelay(long j) {
        if (this.mCollectingTransition == null) {
            return;
        }
        this.mCollectingTransition.mStatusBarTransitionDelay = j;
    }

    void setOverrideAnimation(android.window.TransitionInfo.AnimationOptions animationOptions, @android.annotation.Nullable android.os.IRemoteCallback iRemoteCallback, @android.annotation.Nullable android.os.IRemoteCallback iRemoteCallback2) {
        if (this.mCollectingTransition == null) {
            return;
        }
        this.mCollectingTransition.setOverrideAnimation(animationOptions, iRemoteCallback, iRemoteCallback2);
    }

    void setNoAnimation(com.android.server.wm.WindowContainer windowContainer) {
        if (this.mCollectingTransition == null) {
            return;
        }
        this.mCollectingTransition.setNoAnimation(windowContainer);
    }

    void setReady(com.android.server.wm.WindowContainer windowContainer, boolean z) {
        if (this.mCollectingTransition == null) {
            return;
        }
        this.mCollectingTransition.setReady(windowContainer, z);
    }

    void setReady(com.android.server.wm.WindowContainer windowContainer) {
        setReady(windowContainer, true);
    }

    void deferTransitionReady() {
        if (isShellTransitionsEnabled()) {
            if (this.mCollectingTransition == null) {
                throw new java.lang.IllegalStateException("No collecting transition to defer readiness for.");
            }
            this.mCollectingTransition.deferTransitionReady();
        }
    }

    void continueTransitionReady() {
        if (isShellTransitionsEnabled()) {
            if (this.mCollectingTransition == null) {
                throw new java.lang.IllegalStateException("No collecting transition to defer readiness for.");
            }
            this.mCollectingTransition.continueTransitionReady();
        }
    }

    void finishTransition(com.android.server.wm.Transition transition) {
        this.mTransitionMetricsReporter.reportAnimationStart(transition.getToken(), 0L);
        this.mAtm.endPowerMode(2);
        if (!this.mPlayingTransitions.contains(transition)) {
            android.util.Slog.e(TAG, "Trying to finish a non-playing transition " + transition);
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -6030030735787868329L, 0, null, java.lang.String.valueOf(transition));
        this.mPlayingTransitions.remove(transition);
        if (!inTransition()) {
            this.mTrackCount = 0;
        }
        updateRunningRemoteAnimation(transition, false);
        transition.finishTransition();
        for (int size = this.mAnimatingExitWindows.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowState windowState = this.mAnimatingExitWindows.get(size);
            if (windowState.mAnimatingExit && windowState.mHasSurface && !windowState.inTransition()) {
                windowState.onExitAnimationDone();
            }
            if (!windowState.mAnimatingExit || !windowState.mHasSurface) {
                this.mAnimatingExitWindows.remove(size);
            }
        }
        this.mRunningLock.doNotifyLocked();
        if (!inTransition()) {
            validateStates();
            this.mAtm.mWindowManager.onAnimationFinished();
        }
    }

    void onCommittedInvisibles() {
        if (this.mCollectingTransition != null) {
            this.mCollectingTransition.mPriorVisibilityMightBeDirty = true;
        }
        for (int size = this.mWaitingTransitions.size() - 1; size >= 0; size--) {
            this.mWaitingTransitions.get(size).mPriorVisibilityMightBeDirty = true;
        }
    }

    private void validateStates() {
        for (int i = 0; i < this.mStateValidators.size(); i++) {
            this.mStateValidators.get(i).run();
            if (inTransition()) {
                this.mStateValidators.subList(0, i + 1).clear();
                return;
            }
        }
        this.mStateValidators.clear();
        for (int i2 = 0; i2 < this.mValidateCommitVis.size(); i2++) {
            com.android.server.wm.ActivityRecord activityRecord = this.mValidateCommitVis.get(i2);
            if (!activityRecord.isVisibleRequested() && activityRecord.isVisible()) {
                android.util.Slog.e(TAG, "Uncommitted visibility change: " + activityRecord);
                activityRecord.commitVisibility(activityRecord.isVisibleRequested(), false, false);
            }
        }
        this.mValidateCommitVis.clear();
        for (int i3 = 0; i3 < this.mValidateActivityCompat.size(); i3++) {
            com.android.server.wm.ActivityRecord activityRecord2 = this.mValidateActivityCompat.get(i3);
            if (activityRecord2.getSurfaceControl() != null) {
                activityRecord2.getRelativePosition(new android.graphics.Point());
                activityRecord2.getSyncTransaction().setPosition(activityRecord2.getSurfaceControl(), r4.x, r4.y);
            }
        }
        this.mValidateActivityCompat.clear();
        for (int i4 = 0; i4 < this.mValidateDisplayVis.size(); i4++) {
            com.android.server.wm.DisplayArea displayArea = this.mValidateDisplayVis.get(i4);
            if (displayArea.isAttached() && displayArea.getSurfaceControl() != null && displayArea.isVisibleRequested()) {
                android.util.Slog.e(TAG, "DisplayArea became visible outside of a transition: " + displayArea);
                displayArea.getSyncTransaction().show(displayArea.getSurfaceControl());
            }
        }
        this.mValidateDisplayVis.clear();
    }

    void onVisibleWithoutCollectingTransition(final com.android.server.wm.WindowContainer<?> windowContainer, java.lang.String str) {
        boolean z = !this.mPlayingTransitions.isEmpty();
        android.util.Slog.e(TAG, "Set visible without transition " + windowContainer + " playing=" + z + " caller=" + str);
        if (!z) {
            com.android.server.wm.WindowContainer.enforceSurfaceVisible(windowContainer);
        } else {
            this.mStateValidators.add(new java.lang.Runnable() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.TransitionController.lambda$onVisibleWithoutCollectingTransition$5(com.android.server.wm.WindowContainer.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onVisibleWithoutCollectingTransition$5(com.android.server.wm.WindowContainer windowContainer) {
        if (windowContainer.isVisibleRequested()) {
            com.android.server.wm.WindowContainer.enforceSurfaceVisible(windowContainer);
        }
    }

    void onTransitionPopulated(com.android.server.wm.Transition transition) {
        tryStartCollectFromQueue();
    }

    private boolean canStartCollectingNow(@android.annotation.Nullable com.android.server.wm.Transition transition) {
        if (this.mCollectingTransition == null) {
            return true;
        }
        if (!this.mCollectingTransition.isPopulated() || !getCanBeIndependent(this.mCollectingTransition, transition)) {
            return false;
        }
        for (int i = 0; i < this.mWaitingTransitions.size(); i++) {
            if (!getCanBeIndependent(this.mWaitingTransitions.get(i), transition)) {
                return false;
            }
        }
        return true;
    }

    void tryStartCollectFromQueue() {
        if (this.mQueuedTransitions.isEmpty()) {
            return;
        }
        final com.android.server.wm.TransitionController.QueuedTransition queuedTransition = this.mQueuedTransitions.get(0);
        if (this.mCollectingTransition != null) {
            if (queuedTransition.mTransition == null || !canStartCollectingNow(queuedTransition.mTransition)) {
                return;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, -1611886029896664304L, 1, "Moving #%d from collecting to waiting.", java.lang.Long.valueOf(this.mCollectingTransition.getSyncId()));
            this.mWaitingTransitions.add(this.mCollectingTransition);
            this.mCollectingTransition = null;
        } else if (this.mSyncEngine.hasActiveSync()) {
            return;
        }
        this.mQueuedTransitions.remove(0);
        if (queuedTransition.mTransition != null) {
            moveToCollecting(queuedTransition.mTransition);
        } else {
            this.mSyncEngine.startSyncSet(queuedTransition.mLegacySync);
        }
        if (queuedTransition.mTransition != null && queuedTransition.mTransition.mType == 12) {
            queuedTransition.mOnStartCollect.onCollectStarted(true);
        } else {
            this.mAtm.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.TransitionController.this.lambda$tryStartCollectFromQueue$6(queuedTransition);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryStartCollectFromQueue$6(com.android.server.wm.TransitionController.QueuedTransition queuedTransition) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                queuedTransition.mOnStartCollect.onCollectStarted(true);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void moveToPlaying(com.android.server.wm.Transition transition) {
        if (transition == this.mCollectingTransition) {
            this.mCollectingTransition = null;
            if (!this.mWaitingTransitions.isEmpty()) {
                this.mCollectingTransition = this.mWaitingTransitions.remove(0);
            }
            if (this.mCollectingTransition == null) {
                this.mLatestOnTopTasksReported.clear();
            }
        } else if (!this.mWaitingTransitions.remove(transition)) {
            throw new java.lang.IllegalStateException("Trying to move non-collecting transition toplaying " + transition.getSyncId());
        }
        this.mPlayingTransitions.add(transition);
        updateRunningRemoteAnimation(transition, true);
    }

    boolean getCanBeIndependent(com.android.server.wm.Transition transition, @android.annotation.Nullable com.android.server.wm.Transition transition2) {
        if (transition2 != null && transition2.mParallelCollectType == 1 && transition.mParallelCollectType == 1) {
            return true;
        }
        if (transition2 == null || transition2.mParallelCollectType != 2) {
            return transition.mParallelCollectType == 2;
        }
        if (transition.mParallelCollectType == 2) {
            return false;
        }
        for (int i = 0; i < transition.mParticipants.size(); i++) {
            com.android.server.wm.WindowContainer valueAt = transition.mParticipants.valueAt(i);
            com.android.server.wm.ActivityRecord asActivityRecord = valueAt.asActivityRecord();
            if (asActivityRecord == null && valueAt.asWindowState() == null && valueAt.asWindowToken() == null) {
                return false;
            }
            if (asActivityRecord != null && asActivityRecord.isActivityTypeHomeOrRecents()) {
                return false;
            }
        }
        return true;
    }

    static boolean getIsIndependent(com.android.server.wm.Transition transition, com.android.server.wm.Transition transition2) {
        if (transition.mParallelCollectType == 1 && transition2.mParallelCollectType == 1) {
            return true;
        }
        if (transition.mParallelCollectType == 2 && transition.hasTransientLaunch()) {
            if (transition2.mParallelCollectType == 2) {
                return false;
            }
            transition2 = transition;
            transition = transition2;
        } else if (transition2.mParallelCollectType != 2 || !transition2.hasTransientLaunch()) {
            return false;
        }
        for (int i = 0; i < transition.mTargets.size(); i++) {
            com.android.server.wm.WindowContainer windowContainer = transition.mTargets.get(i).mContainer;
            com.android.server.wm.ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
            if (asActivityRecord == null && windowContainer.asWindowState() == null && windowContainer.asWindowToken() == null) {
                return false;
            }
            if (asActivityRecord != null && transition2.isTransientLaunch(asActivityRecord)) {
                return false;
            }
        }
        return true;
    }

    void assignTrack(com.android.server.wm.Transition transition, android.window.TransitionInfo transitionInfo) {
        boolean z;
        int i = -1;
        int i2 = 0;
        while (true) {
            if (i2 >= this.mPlayingTransitions.size()) {
                z = false;
                break;
            }
            if (this.mPlayingTransitions.get(i2) != transition && !getIsIndependent(this.mPlayingTransitions.get(i2), transition)) {
                if (i >= 0) {
                    z = true;
                    break;
                }
                i = this.mPlayingTransitions.get(i2).mAnimationTrack;
            }
            i2++;
        }
        int i3 = z ? 0 : i;
        if (i3 < 0 && (i3 = this.mTrackCount) > 0) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -7097461682459496366L, 5, null, java.lang.Long.valueOf(transition.getSyncId()), java.lang.Long.valueOf(i3));
        }
        transition.mAnimationTrack = i3;
        transitionInfo.setTrack(i3);
        this.mTrackCount = java.lang.Math.max(this.mTrackCount, i3 + 1);
        if (z && this.mTrackCount > 1) {
            transitionInfo.setFlags(transitionInfo.getFlags() | 2097152);
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -7364464699035275052L, 1, null, java.lang.Long.valueOf(transition.getSyncId()));
        }
    }

    boolean isAnimating() {
        return this.mAnimatingState;
    }

    void updateAnimatingState() {
        boolean z = !this.mPlayingTransitions.isEmpty() || (this.mCollectingTransition != null && this.mCollectingTransition.isStarted());
        if (z && !this.mAnimatingState) {
            for (int childCount = this.mAtm.mRootWindowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
                com.android.server.wm.DisplayContent childAt = this.mAtm.mRootWindowContainer.getChildAt(childCount);
                if (this.mCollectingTransition != null && this.mCollectingTransition.shouldUsePerfHint(childAt)) {
                    childAt.enableHighPerfTransition(true);
                } else {
                    int size = this.mPlayingTransitions.size() - 1;
                    while (true) {
                        if (size < 0) {
                            break;
                        }
                        if (this.mPlayingTransitions.get(size).shouldUsePerfHint(childAt)) {
                            childAt.enableHighPerfTransition(true);
                            break;
                        }
                        size--;
                    }
                }
            }
            this.mSnapshotController.setPause(true);
            this.mAnimatingState = true;
            com.android.server.wm.Transition.asyncTraceBegin("animating", 68942577);
            return;
        }
        if (!z && this.mAnimatingState) {
            for (int childCount2 = this.mAtm.mRootWindowContainer.getChildCount() - 1; childCount2 >= 0; childCount2--) {
                this.mAtm.mRootWindowContainer.getChildAt(childCount2).enableHighPerfTransition(false);
            }
            this.mAtm.mWindowManager.scheduleAnimationLocked();
            this.mSnapshotController.setPause(false);
            this.mAnimatingState = false;
            com.android.server.wm.Transition.asyncTraceEnd(68942577);
        }
    }

    private void updateRunningRemoteAnimation(com.android.server.wm.Transition transition, boolean z) {
        if (this.mTransitionPlayerProc == null) {
            return;
        }
        if (z) {
            this.mTransitionPlayerProc.setRunningRemoteAnimation(true);
        } else if (this.mPlayingTransitions.isEmpty()) {
            this.mTransitionPlayerProc.setRunningRemoteAnimation(false);
            this.mRemotePlayer.clear();
        }
    }

    void onAbort(com.android.server.wm.Transition transition) {
        if (transition != this.mCollectingTransition) {
            int indexOf = this.mWaitingTransitions.indexOf(transition);
            if (indexOf < 0) {
                throw new java.lang.IllegalStateException("Too late for abort.");
            }
            this.mWaitingTransitions.remove(indexOf);
            return;
        }
        this.mCollectingTransition = null;
        if (!this.mWaitingTransitions.isEmpty()) {
            this.mCollectingTransition = this.mWaitingTransitions.remove(0);
        }
        if (this.mCollectingTransition == null) {
            this.mLatestOnTopTasksReported.clear();
        }
    }

    void setTransientLaunch(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.Task task) {
        if (this.mCollectingTransition == null) {
            return;
        }
        this.mCollectingTransition.setTransientLaunch(activityRecord, task);
        if (activityRecord.isActivityTypeHomeOrRecents()) {
            this.mCollectingTransition.addFlag(128);
            activityRecord.getTask().setCanAffectSystemUiFlags(false);
        }
    }

    void setCanPipOnFinish(boolean z) {
        if (this.mCollectingTransition == null) {
            return;
        }
        this.mCollectingTransition.setCanPipOnFinish(z);
    }

    void legacyDetachNavigationBarFromApp(@android.annotation.NonNull android.os.IBinder iBinder) {
        com.android.server.wm.Transition fromBinder = com.android.server.wm.Transition.fromBinder(iBinder);
        if (fromBinder == null || !this.mPlayingTransitions.contains(fromBinder)) {
            android.util.Slog.e(TAG, "Transition isn't playing: " + iBinder);
            return;
        }
        fromBinder.legacyRestoreNavigationBarFromApp();
    }

    void registerLegacyListener(com.android.server.wm.WindowManagerInternal.AppTransitionListener appTransitionListener) {
        this.mLegacyListeners.add(appTransitionListener);
    }

    void unregisterLegacyListener(com.android.server.wm.WindowManagerInternal.AppTransitionListener appTransitionListener) {
        this.mLegacyListeners.remove(appTransitionListener);
    }

    void dispatchLegacyAppTransitionPending() {
        for (int i = 0; i < this.mLegacyListeners.size(); i++) {
            this.mLegacyListeners.get(i).onAppTransitionPendingLocked();
        }
    }

    void dispatchLegacyAppTransitionStarting(android.window.TransitionInfo transitionInfo, long j) {
        for (int i = 0; i < this.mLegacyListeners.size(); i++) {
            this.mLegacyListeners.get(i).onAppTransitionStartingLocked(android.os.SystemClock.uptimeMillis() + j, 120L);
        }
    }

    void dispatchLegacyAppTransitionFinished(com.android.server.wm.ActivityRecord activityRecord) {
        for (int i = 0; i < this.mLegacyListeners.size(); i++) {
            this.mLegacyListeners.get(i).onAppTransitionFinishedLocked(activityRecord.token);
        }
    }

    void dispatchLegacyAppTransitionCancelled() {
        for (int i = 0; i < this.mLegacyListeners.size(); i++) {
            this.mLegacyListeners.get(i).onAppTransitionCancelledLocked(false);
        }
    }

    void dumpDebugLegacy(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        int i;
        long start = protoOutputStream.start(j);
        if (!this.mPlayingTransitions.isEmpty()) {
            i = 2;
        } else if ((this.mCollectingTransition == null || !this.mCollectingTransition.getLegacyIsReady()) && !this.mSyncEngine.hasPendingSyncSets()) {
            i = 0;
        } else {
            i = 1;
        }
        protoOutputStream.write(1159641169921L, i);
        protoOutputStream.end(start);
    }

    private void queueTransition(com.android.server.wm.Transition transition, com.android.server.wm.TransitionController.OnStartCollect onStartCollect) {
        this.mQueuedTransitions.add(new com.android.server.wm.TransitionController.QueuedTransition(transition, onStartCollect));
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, -5509640937151643757L, 0, "Queueing transition: %s", java.lang.String.valueOf(transition));
    }

    boolean startCollectOrQueue(com.android.server.wm.Transition transition, com.android.server.wm.TransitionController.OnStartCollect onStartCollect) {
        if (!this.mQueuedTransitions.isEmpty()) {
            queueTransition(transition, onStartCollect);
            return false;
        }
        if (this.mSyncEngine.hasActiveSync()) {
            if (isCollecting()) {
                if (canStartCollectingNow(transition)) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, -1611886029896664304L, 1, "Moving #%d from collecting to waiting.", java.lang.Long.valueOf(this.mCollectingTransition.getSyncId()));
                    this.mWaitingTransitions.add(this.mCollectingTransition);
                    this.mCollectingTransition = null;
                    moveToCollecting(transition);
                    onStartCollect.onCollectStarted(false);
                    return true;
                }
            } else {
                android.util.Slog.w(TAG, "Ongoing Sync outside of transition.");
            }
            queueTransition(transition, onStartCollect);
            return false;
        }
        moveToCollecting(transition);
        onStartCollect.onCollectStarted(false);
        return true;
    }

    @android.annotation.NonNull
    com.android.server.wm.Transition createAndStartCollecting(int i) {
        if (this.mTransitionPlayer == null || !this.mQueuedTransitions.isEmpty()) {
            return null;
        }
        if (this.mSyncEngine.hasActiveSync()) {
            if (isCollecting()) {
                if (canStartCollectingNow(null)) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, -1611886029896664304L, 1, "Moving #%d from collecting to waiting.", java.lang.Long.valueOf(this.mCollectingTransition.getSyncId()));
                    this.mWaitingTransitions.add(this.mCollectingTransition);
                    this.mCollectingTransition = null;
                    com.android.server.wm.Transition transition = new com.android.server.wm.Transition(i, 0, this, this.mSyncEngine);
                    moveToCollecting(transition);
                    return transition;
                }
            } else {
                android.util.Slog.w(TAG, "Ongoing Sync outside of transition.");
            }
            return null;
        }
        com.android.server.wm.Transition transition2 = new com.android.server.wm.Transition(i, 0, this, this.mSyncEngine);
        moveToCollecting(transition2);
        return transition2;
    }

    void startLegacySyncOrQueue(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup, final java.util.function.Consumer<java.lang.Boolean> consumer) {
        if (!this.mQueuedTransitions.isEmpty() || this.mSyncEngine.hasActiveSync()) {
            this.mQueuedTransitions.add(new com.android.server.wm.TransitionController.QueuedTransition(syncGroup, new com.android.server.wm.TransitionController.OnStartCollect() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda1
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z) {
                    com.android.server.wm.TransitionController.lambda$startLegacySyncOrQueue$7(consumer, z);
                }
            }));
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, -2741593375634604522L, 0, "Queueing legacy sync-set: %s", java.lang.String.valueOf(syncGroup.mSyncId));
        } else {
            this.mSyncEngine.startSyncSet(syncGroup);
            consumer.accept(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startLegacySyncOrQueue$7(java.util.function.Consumer consumer, boolean z) {
        consumer.accept(true);
    }

    void waitFor(@android.annotation.NonNull com.android.server.wm.Transition.ReadyCondition readyCondition) {
        if (this.mCollectingTransition == null) {
            android.util.Slog.e(TAG, "No collecting transition available to wait for " + readyCondition);
            readyCondition.mTracker = com.android.server.wm.Transition.ReadyTracker.NULL_TRACKER;
            return;
        }
        this.mCollectingTransition.mReadyTracker.add(readyCondition);
    }

    static class RemotePlayer {
        private static final long REPORT_RUNNING_GRACE_PERIOD_MS = 200;
        private final com.android.server.wm.ActivityTaskManagerService mAtm;

        @com.android.internal.annotations.GuardedBy({"itself"})
        private final android.util.ArrayMap<android.os.IBinder, com.android.server.wm.TransitionController.RemotePlayer.DelegateProcess> mDelegateProcesses = new android.util.ArrayMap<>();

        private class DelegateProcess implements java.lang.Runnable {
            boolean mNeedReport;
            final com.android.server.wm.WindowProcessController mProc;

            DelegateProcess(com.android.server.wm.WindowProcessController windowProcessController) {
                this.mProc = windowProcessController;
            }

            @Override // java.lang.Runnable
            public void run() {
                synchronized (com.android.server.wm.TransitionController.RemotePlayer.this.mAtm.mGlobalLockWithoutBoost) {
                    com.android.server.wm.TransitionController.RemotePlayer.this.update(this.mProc, false, false);
                }
            }
        }

        RemotePlayer(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
            this.mAtm = activityTaskManagerService;
        }

        void update(@android.annotation.NonNull com.android.server.wm.WindowProcessController windowProcessController, boolean z, boolean z2) {
            boolean z3 = true;
            if (!z) {
                synchronized (this.mDelegateProcesses) {
                    try {
                        int size = this.mDelegateProcesses.size() - 1;
                        while (true) {
                            if (size < 0) {
                                z3 = false;
                                break;
                            } else if (this.mDelegateProcesses.valueAt(size).mProc != windowProcessController) {
                                size--;
                            } else {
                                this.mDelegateProcesses.removeAt(size);
                                break;
                            }
                        }
                        if (z3) {
                            windowProcessController.setRunningRemoteAnimation(false);
                            return;
                        }
                        return;
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
            if (windowProcessController.isRunningRemoteTransition() || !windowProcessController.hasThread()) {
                return;
            }
            windowProcessController.setRunningRemoteAnimation(true);
            com.android.server.wm.TransitionController.RemotePlayer.DelegateProcess delegateProcess = new com.android.server.wm.TransitionController.RemotePlayer.DelegateProcess(windowProcessController);
            if (z2) {
                delegateProcess.mNeedReport = true;
                this.mAtm.mH.postDelayed(delegateProcess, REPORT_RUNNING_GRACE_PERIOD_MS);
            }
            synchronized (this.mDelegateProcesses) {
                this.mDelegateProcesses.put(windowProcessController.getThread().asBinder(), delegateProcess);
            }
        }

        void clear() {
            synchronized (this.mDelegateProcesses) {
                try {
                    for (int size = this.mDelegateProcesses.size() - 1; size >= 0; size--) {
                        this.mDelegateProcesses.valueAt(size).mProc.setRunningRemoteAnimation(false);
                    }
                    this.mDelegateProcesses.clear();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        boolean reportRunning(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread) {
            com.android.server.wm.TransitionController.RemotePlayer.DelegateProcess delegateProcess;
            synchronized (this.mDelegateProcesses) {
                try {
                    delegateProcess = this.mDelegateProcesses.get(iApplicationThread.asBinder());
                    if (delegateProcess != null && delegateProcess.mNeedReport) {
                        delegateProcess.mNeedReport = false;
                        this.mAtm.mH.removeCallbacks(delegateProcess);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return delegateProcess != null;
        }
    }

    static class Logger implements java.lang.Runnable {
        long mAbortTimeNs;
        long mCollectTimeNs;
        long mCreateTimeNs;
        long mCreateWallTimeMs;
        long mFinishTimeNs;
        android.window.TransitionInfo mInfo;
        long mReadyTimeNs;
        android.window.TransitionRequestInfo mRequest;
        long mRequestTimeNs;
        long mSendTimeNs;
        long mStartTimeNs;
        android.window.WindowContainerTransaction mStartWCT;
        int mSyncId;

        Logger() {
        }

        private java.lang.String buildOnSendLog() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("Sent Transition (#");
            sb.append(this.mSyncId);
            sb.append(") createdAt=");
            sb.append(android.util.TimeUtils.logTimeOfDay(this.mCreateWallTimeMs));
            if (this.mRequest != null) {
                sb.append(" via request=");
                sb.append(this.mRequest);
            }
            return sb.toString();
        }

        void logOnSendAsync(android.os.Handler handler) {
            handler.post(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                logOnSend();
            } catch (java.lang.Exception e) {
                android.util.Slog.w(com.android.server.wm.TransitionController.TAG, "Failed to log transition", e);
            }
        }

        void logOnSend() {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, -5051723169912572741L, 0, "%s", java.lang.String.valueOf(buildOnSendLog()));
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, 4281568181321808508L, 0, "    startWCT=%s", java.lang.String.valueOf(this.mStartWCT));
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, 5141999957143860655L, 0, "    info=%s", java.lang.String.valueOf(this.mInfo.toString("    ")));
        }

        private static java.lang.String toMsString(long j) {
            return (java.lang.Math.round(j / 1000.0d) / 1000.0d) + "ms";
        }

        private java.lang.String buildOnFinishLog() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("Finish Transition (#");
            sb.append(this.mSyncId);
            sb.append("): created at ");
            sb.append(android.util.TimeUtils.logTimeOfDay(this.mCreateWallTimeMs));
            sb.append(" collect-started=");
            sb.append(toMsString(this.mCollectTimeNs - this.mCreateTimeNs));
            if (this.mRequestTimeNs != 0) {
                sb.append(" request-sent=");
                sb.append(toMsString(this.mRequestTimeNs - this.mCreateTimeNs));
            }
            sb.append(" started=");
            sb.append(toMsString(this.mStartTimeNs - this.mCreateTimeNs));
            sb.append(" ready=");
            sb.append(toMsString(this.mReadyTimeNs - this.mCreateTimeNs));
            sb.append(" sent=");
            sb.append(toMsString(this.mSendTimeNs - this.mCreateTimeNs));
            sb.append(" finished=");
            sb.append(toMsString(this.mFinishTimeNs - this.mCreateTimeNs));
            return sb.toString();
        }

        void logOnFinish() {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS_MIN, -5051723169912572741L, 0, "%s", java.lang.String.valueOf(buildOnFinishLog()));
        }
    }

    static class TransitionMetricsReporter extends android.window.ITransitionMetricsReporter.Stub {
        private final android.util.ArrayMap<android.os.IBinder, java.util.function.LongConsumer> mMetricConsumers = new android.util.ArrayMap<>();

        TransitionMetricsReporter() {
        }

        void associate(android.os.IBinder iBinder, java.util.function.LongConsumer longConsumer) {
            synchronized (this.mMetricConsumers) {
                this.mMetricConsumers.put(iBinder, longConsumer);
            }
        }

        public void reportAnimationStart(android.os.IBinder iBinder, long j) {
            synchronized (this.mMetricConsumers) {
                try {
                    if (this.mMetricConsumers.isEmpty()) {
                        return;
                    }
                    java.util.function.LongConsumer remove = this.mMetricConsumers.remove(iBinder);
                    if (remove != null) {
                        remove.accept(j);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    class Lock {
        private int mTransitionWaiters = 0;

        Lock() {
        }

        void runWhenIdle(long j, java.lang.Runnable runnable) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.TransitionController.this.mAtm.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!com.android.server.wm.TransitionController.this.inTransition()) {
                        runnable.run();
                        return;
                    }
                    this.mTransitionWaiters++;
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    long uptimeMillis = android.os.SystemClock.uptimeMillis() + j;
                    while (true) {
                        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = com.android.server.wm.TransitionController.this.mAtm.mGlobalLock;
                        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock2) {
                            try {
                                if (!com.android.server.wm.TransitionController.this.inTransition() || android.os.SystemClock.uptimeMillis() > uptimeMillis) {
                                    break;
                                }
                            } finally {
                                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        synchronized (this) {
                            try {
                                try {
                                    wait(j);
                                } catch (java.lang.InterruptedException e) {
                                    return;
                                }
                            } finally {
                            }
                        }
                    }
                    this.mTransitionWaiters--;
                    runnable.run();
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } finally {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
        }

        void doNotifyLocked() {
            synchronized (this) {
                try {
                    if (this.mTransitionWaiters > 0) {
                        notifyAll();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
