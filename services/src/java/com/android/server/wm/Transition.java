package com.android.server.wm;

/* loaded from: classes3.dex */
class Transition implements com.android.server.wm.BLASTSyncEngine.TransactionReadyListener {
    private static final java.lang.String DEFAULT_PACKAGE = "android";
    static final int PARALLEL_TYPE_MUTUAL = 1;
    static final int PARALLEL_TYPE_NONE = 0;
    static final int PARALLEL_TYPE_RECENTS = 2;
    private static final int STATE_ABORT = 3;
    private static final int STATE_COLLECTING = 0;
    private static final int STATE_FINISHED = 4;
    private static final int STATE_PENDING = -1;
    private static final int STATE_PLAYING = 2;
    private static final int STATE_STARTED = 1;
    private static final java.lang.String TAG = "Transition";
    private static final java.lang.String TRACE_NAME_PLAY_TRANSITION = "playing";
    private final com.android.server.wm.TransitionController mController;
    private int mFlags;
    private android.window.TransitionInfo.AnimationOptions mOverrideOptions;

    @android.annotation.Nullable
    private com.android.server.wm.ActivityRecord mPipActivity;
    long mStatusBarTransitionDelay;
    private final com.android.server.wm.BLASTSyncEngine mSyncEngine;
    java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> mTargets;
    private java.util.ArrayList<com.android.server.wm.Task> mTransientHideTasks;
    final int mType;
    private int mSyncId = -1;
    private android.view.SurfaceControl.Transaction mStartTransaction = null;
    private android.view.SurfaceControl.Transaction mFinishTransaction = null;
    private android.view.SurfaceControl.Transaction mCleanupTransaction = null;
    final android.util.ArrayMap<com.android.server.wm.WindowContainer, com.android.server.wm.Transition.ChangeInfo> mChanges = new android.util.ArrayMap<>();
    final android.util.ArraySet<com.android.server.wm.WindowContainer> mParticipants = new android.util.ArraySet<>();
    private final java.util.ArrayList<com.android.server.wm.DisplayContent> mTargetDisplays = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.Task> mOnTopTasksStart = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.Task> mOnTopTasksAtReady = new java.util.ArrayList<>();
    private final android.util.ArraySet<com.android.server.wm.WindowToken> mVisibleAtTransitionEndTokens = new android.util.ArraySet<>();
    private android.util.ArrayMap<com.android.server.wm.ActivityRecord, com.android.server.wm.Task> mTransientLaunches = null;
    private android.os.IRemoteCallback mClientAnimationStartCallback = null;
    private android.os.IRemoteCallback mClientAnimationFinishCallback = null;
    private int mState = -1;
    private final com.android.server.wm.Transition.ReadyTrackerOld mReadyTrackerOld = new com.android.server.wm.Transition.ReadyTrackerOld();
    final com.android.server.wm.Transition.ReadyTracker mReadyTracker = new com.android.server.wm.Transition.ReadyTracker(this);
    private int mRecentsDisplayId = -1;
    private boolean mCanPipOnFinish = true;
    private boolean mIsSeamlessRotation = false;
    private com.android.server.wm.Transition.IContainerFreezer mContainerFreezer = null;
    boolean mPriorVisibilityMightBeDirty = false;
    final com.android.server.wm.TransitionController.Logger mLogger = new com.android.server.wm.TransitionController.Logger();
    private boolean mForcePlaying = false;
    boolean mIsPlayerEnabled = true;
    int mParallelCollectType = 0;
    int mAnimationTrack = 0;
    java.util.ArrayList<com.android.server.wm.ActivityRecord> mConfigAtEndActivities = null;
    private final com.android.server.wm.Transition.Token mToken = new com.android.server.wm.Transition.Token(this);

    interface IContainerFreezer {
        void cleanUp(android.view.SurfaceControl.Transaction transaction);

        boolean freeze(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull android.graphics.Rect rect);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ParallelType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface TransitionState {
    }

    Transition(int i, int i2, com.android.server.wm.TransitionController transitionController, com.android.server.wm.BLASTSyncEngine bLASTSyncEngine) {
        this.mType = i;
        this.mFlags = i2;
        this.mController = transitionController;
        this.mSyncEngine = bLASTSyncEngine;
        this.mLogger.mCreateWallTimeMs = java.lang.System.currentTimeMillis();
        this.mLogger.mCreateTimeNs = android.os.SystemClock.elapsedRealtimeNanos();
    }

    @android.annotation.Nullable
    static com.android.server.wm.Transition fromBinder(@android.annotation.Nullable android.os.IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        try {
            return ((com.android.server.wm.Transition.Token) iBinder).mTransition.get();
        } catch (java.lang.ClassCastException e) {
            android.util.Slog.w(TAG, "Invalid transition token: " + iBinder, e);
            return null;
        }
    }

    @android.annotation.NonNull
    android.os.IBinder getToken() {
        return this.mToken;
    }

    void addFlag(int i) {
        this.mFlags = i | this.mFlags;
    }

    void calcParallelCollectType(android.window.WindowContainerTransaction windowContainerTransaction) {
        android.os.Bundle launchOptions;
        for (int i = 0; i < windowContainerTransaction.getHierarchyOps().size(); i++) {
            android.window.WindowContainerTransaction.HierarchyOp hierarchyOp = (android.window.WindowContainerTransaction.HierarchyOp) windowContainerTransaction.getHierarchyOps().get(i);
            if (hierarchyOp.getType() == 7 && (launchOptions = hierarchyOp.getLaunchOptions()) != null && !launchOptions.isEmpty() && launchOptions.getBoolean("android.activity.transientLaunch")) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -2700498872917476567L, 0, null, null);
                this.mParallelCollectType = 2;
            }
        }
    }

    void setTransientLaunch(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable final com.android.server.wm.Task task) {
        com.android.server.wm.WindowContainer parent;
        if (this.mTransientLaunches == null) {
            this.mTransientLaunches = new android.util.ArrayMap<>();
            this.mTransientHideTasks = new java.util.ArrayList<>();
        }
        this.mTransientLaunches.put(activityRecord, task);
        setTransientLaunchToChanges(activityRecord);
        final com.android.server.wm.Task rootTask = activityRecord.getRootTask();
        if (task != null) {
            parent = task.getParent();
        } else {
            parent = rootTask != null ? rootTask.getParent() : null;
        }
        if (parent != null) {
            parent.forAllTasks(new java.util.function.Predicate() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$setTransientLaunch$0;
                    lambda$setTransientLaunch$0 = com.android.server.wm.Transition.this.lambda$setTransientLaunch$0(rootTask, task, (com.android.server.wm.Task) obj);
                    return lambda$setTransientLaunch$0;
                }
            });
            for (int size = this.mChanges.size() - 1; size >= 0; size--) {
                updateTransientFlags(this.mChanges.valueAt(size));
            }
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -8676279589273455859L, 1, null, java.lang.Long.valueOf(this.mSyncId), java.lang.String.valueOf(activityRecord));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setTransientLaunch$0(com.android.server.wm.Task task, com.android.server.wm.Task task2, com.android.server.wm.Task task3) {
        if (task3 == task) {
            return false;
        }
        if (task3.isVisibleRequested() && !task3.isAlwaysOnTop()) {
            if (task3.isRootTask()) {
                this.mTransientHideTasks.add(task3);
            }
            if (task3.isLeafTask()) {
                collect(task3);
            }
        }
        if (task2 != null) {
            if (task3 != task2) {
                return false;
            }
            return true;
        }
        if (!task3.isRootTask() || !task3.fillsParent()) {
            return false;
        }
        return true;
    }

    boolean isInTransientHide(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (this.mTransientHideTasks == null) {
            return false;
        }
        for (int size = this.mTransientHideTasks.size() - 1; size >= 0; size--) {
            com.android.server.wm.Task task = this.mTransientHideTasks.get(size);
            if (windowContainer == task || windowContainer.isDescendantOf(task)) {
                return true;
            }
        }
        return false;
    }

    boolean isTransientVisible(@android.annotation.NonNull com.android.server.wm.Task task) {
        com.android.server.wm.WindowContainer<?> parent;
        if (this.mTransientLaunches == null) {
            return false;
        }
        int size = this.mTransientLaunches.size();
        int i = 0;
        for (int i2 = size - 1; i2 >= 0; i2--) {
            com.android.server.wm.Task rootTask = this.mTransientLaunches.keyAt(i2).getRootTask();
            if (rootTask != null && (parent = rootTask.getParent()) != null && parent.getTopChild() != rootTask && rootTask.compareTo((com.android.server.wm.WindowContainer) this.mController.mAtm.mTaskSupervisor.mOpaqueActivityHelper.getOpaqueActivity(parent, true).getRootTask()) < 0) {
                i++;
            }
        }
        if (i == size) {
            for (int size2 = this.mTransientLaunches.size() - 1; size2 >= 0; size2--) {
                if (this.mTransientLaunches.keyAt(size2).isDescendantOf(task)) {
                    return true;
                }
            }
            return false;
        }
        return isInTransientHide(task);
    }

    boolean canApplyDim(@android.annotation.NonNull com.android.server.wm.Task task) {
        if (this.mTransientLaunches == null) {
            return true;
        }
        com.android.server.wm.Dimmer dimmer = task.getDimmer();
        if (dimmer == null) {
            return false;
        }
        if (dimmer.getHost().asTask() != null) {
            return true;
        }
        for (int size = this.mTransientLaunches.size() - 1; size >= 0; size--) {
            com.android.server.wm.Task task2 = this.mTransientLaunches.keyAt(size).getTask();
            if (task2 != null && task2.canAffectSystemUiFlags()) {
                return false;
            }
        }
        return true;
    }

    boolean hasTransientLaunch() {
        return (this.mTransientLaunches == null || this.mTransientLaunches.isEmpty()) ? false : true;
    }

    boolean isTransientLaunch(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        return this.mTransientLaunches != null && this.mTransientLaunches.containsKey(activityRecord);
    }

    com.android.server.wm.Task getTransientLaunchRestoreTarget(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (this.mTransientLaunches == null) {
            return null;
        }
        for (int i = 0; i < this.mTransientLaunches.size(); i++) {
            if (this.mTransientLaunches.keyAt(i).isDescendantOf(windowContainer)) {
                return this.mTransientLaunches.valueAt(i);
            }
        }
        return null;
    }

    boolean isOnDisplay(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        return this.mTargetDisplays.contains(displayContent);
    }

    void setConfigAtEnd(@android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer) {
        windowContainer.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.Transition.this.lambda$setConfigAtEnd$1((com.android.server.wm.ActivityRecord) obj);
            }
        });
        snapshotStartState(windowContainer);
        this.mChanges.get(windowContainer).mFlags |= 64;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setConfigAtEnd$1(com.android.server.wm.ActivityRecord activityRecord) {
        if (!activityRecord.isVisible() || !activityRecord.isVisibleRequested()) {
            return;
        }
        if (this.mConfigAtEndActivities == null) {
            this.mConfigAtEndActivities = new java.util.ArrayList<>();
        }
        if (this.mConfigAtEndActivities.contains(activityRecord)) {
            return;
        }
        this.mConfigAtEndActivities.add(activityRecord);
        activityRecord.pauseConfigurationDispatch();
    }

    void setSeamlessRotation(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        com.android.server.wm.Transition.ChangeInfo changeInfo = this.mChanges.get(windowContainer);
        if (changeInfo == null) {
            return;
        }
        changeInfo.mFlags |= 1;
        onSeamlessRotating(windowContainer.getDisplayContent());
    }

    void onSeamlessRotating(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        if (this.mSyncEngine.getSyncSet(this.mSyncId).mSyncMethod == 1) {
            return;
        }
        if (this.mContainerFreezer == null) {
            this.mContainerFreezer = new com.android.server.wm.Transition.ScreenshotFreezer();
        }
        com.android.server.wm.WindowState topFullscreenOpaqueWindow = displayContent.getDisplayPolicy().getTopFullscreenOpaqueWindow();
        if (topFullscreenOpaqueWindow != null) {
            this.mIsSeamlessRotation = true;
            topFullscreenOpaqueWindow.mSyncMethodOverride = 1;
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 2734227875286695843L, 0, null, java.lang.String.valueOf(topFullscreenOpaqueWindow.getName()));
        }
    }

    void setPipActivity(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        this.mPipActivity = activityRecord;
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord getPipActivity() {
        return this.mPipActivity;
    }

    private void setTransientLaunchToChanges(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        while (windowContainer != null && this.mChanges.containsKey(windowContainer)) {
            if (windowContainer.asTask() == null && windowContainer.asActivityRecord() == null) {
                return;
            }
            this.mChanges.get(windowContainer).mFlags |= 2;
            windowContainer = windowContainer.getParent();
        }
    }

    void setContainerFreezer(com.android.server.wm.Transition.IContainerFreezer iContainerFreezer) {
        this.mContainerFreezer = iContainerFreezer;
    }

    int getState() {
        return this.mState;
    }

    int getSyncId() {
        return this.mSyncId;
    }

    int getFlags() {
        return this.mFlags;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.view.SurfaceControl.Transaction getStartTransaction() {
        return this.mStartTransaction;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.view.SurfaceControl.Transaction getFinishTransaction() {
        return this.mFinishTransaction;
    }

    boolean isPending() {
        return this.mState == -1;
    }

    boolean isCollecting() {
        return this.mState == 0 || this.mState == 1;
    }

    boolean isAborted() {
        return this.mState == 3;
    }

    boolean isStarted() {
        return this.mState == 1;
    }

    boolean isPlaying() {
        return this.mState == 2;
    }

    boolean isFinished() {
        return this.mState == 4;
    }

    void startCollecting(long j) {
        if (this.mState != -1) {
            throw new java.lang.IllegalStateException("Attempting to re-use a transition");
        }
        this.mState = 0;
        this.mSyncId = this.mSyncEngine.startSyncSet(this, j, TAG, this.mParallelCollectType != 0);
        this.mSyncEngine.setSyncMethod(this.mSyncId, com.android.server.wm.TransitionController.SYNC_METHOD);
        this.mLogger.mSyncId = this.mSyncId;
        this.mLogger.mCollectTimeNs = android.os.SystemClock.elapsedRealtimeNanos();
    }

    void start() {
        if (this.mState < 0) {
            throw new java.lang.IllegalStateException("Can't start Transition which isn't collecting.");
        }
        if (this.mState >= 1) {
            android.util.Slog.w(TAG, "Transition already started id=" + this.mSyncId + " state=" + this.mState);
            return;
        }
        this.mState = 1;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 2808217645990556209L, 1, null, java.lang.Long.valueOf(this.mSyncId));
        applyReady();
        this.mLogger.mStartTimeNs = android.os.SystemClock.elapsedRealtimeNanos();
        this.mController.updateAnimatingState();
    }

    void collect(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (this.mState < 0) {
            throw new java.lang.IllegalStateException("Transition hasn't started collecting.");
        }
        if (!isCollecting()) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -4672522645315112127L, 1, null, java.lang.Long.valueOf(this.mSyncId), java.lang.String.valueOf(windowContainer));
        snapshotStartState(getAnimatableParent(windowContainer));
        if (this.mParticipants.contains(windowContainer)) {
            return;
        }
        if (!isInTransientHide(windowContainer)) {
            this.mSyncEngine.addToSyncSet(this.mSyncId, windowContainer);
        }
        if (windowContainer.asWindowToken() != null && windowContainer.asWindowToken().mRoundedCornerOverlay) {
            return;
        }
        com.android.server.wm.Transition.ChangeInfo changeInfo = this.mChanges.get(windowContainer);
        if (changeInfo == null) {
            changeInfo = new com.android.server.wm.Transition.ChangeInfo(windowContainer);
            updateTransientFlags(changeInfo);
            this.mChanges.put(windowContainer, changeInfo);
        }
        this.mParticipants.add(windowContainer);
        recordDisplay(windowContainer.getDisplayContent());
        if (changeInfo.mShowWallpaper) {
            windowContainer.mDisplayContent.mWallpaperController.collectTopWallpapers(this);
        }
    }

    private void snapshotStartState(@android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer) {
        while (windowContainer != null && !this.mChanges.containsKey(windowContainer)) {
            com.android.server.wm.Transition.ChangeInfo changeInfo = new com.android.server.wm.Transition.ChangeInfo(windowContainer);
            updateTransientFlags(changeInfo);
            this.mChanges.put(windowContainer, changeInfo);
            if (isReadyGroup(windowContainer)) {
                this.mReadyTrackerOld.addGroup(windowContainer);
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 65881049096729394L, 1, null, java.lang.Long.valueOf(this.mSyncId), java.lang.String.valueOf(windowContainer));
            }
            windowContainer = getAnimatableParent(windowContainer);
        }
    }

    private void updateTransientFlags(@android.annotation.NonNull com.android.server.wm.Transition.ChangeInfo changeInfo) {
        com.android.server.wm.WindowContainer windowContainer = changeInfo.mContainer;
        if (!(windowContainer.asTaskFragment() == null && windowContainer.asActivityRecord() == null) && isInTransientHide(windowContainer)) {
            changeInfo.mFlags |= 4;
        }
    }

    private void recordDisplay(com.android.server.wm.DisplayContent displayContent) {
        if (displayContent == null || this.mTargetDisplays.contains(displayContent)) {
            return;
        }
        this.mTargetDisplays.add(displayContent);
        addOnTopTasks(displayContent, this.mOnTopTasksStart);
        if (this.mController.isAnimating()) {
            displayContent.enableHighPerfTransition(true);
        }
    }

    void recordTaskOrder(com.android.server.wm.WindowContainer windowContainer) {
        recordDisplay(windowContainer.getDisplayContent());
    }

    private static void addOnTopTasks(com.android.server.wm.Task task, java.util.ArrayList<com.android.server.wm.Task> arrayList) {
        for (int childCount = task.getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.Task asTask = task.getChildAt(childCount).asTask();
            if (asTask == null) {
                return;
            }
            if (!asTask.getWindowConfiguration().isAlwaysOnTop()) {
                arrayList.add(asTask);
                addOnTopTasks(asTask, arrayList);
                return;
            }
        }
    }

    private static void addOnTopTasks(com.android.server.wm.DisplayContent displayContent, java.util.ArrayList<com.android.server.wm.Task> arrayList) {
        com.android.server.wm.Task rootTask = displayContent.getRootTask(new java.util.function.Predicate() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$addOnTopTasks$2;
                lambda$addOnTopTasks$2 = com.android.server.wm.Transition.lambda$addOnTopTasks$2((com.android.server.wm.Task) obj);
                return lambda$addOnTopTasks$2;
            }
        });
        if (rootTask == null) {
            return;
        }
        arrayList.add(rootTask);
        addOnTopTasks(rootTask, arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$addOnTopTasks$2(com.android.server.wm.Task task) {
        return !task.getWindowConfiguration().isAlwaysOnTop();
    }

    void collectExistenceChange(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (this.mState >= 2) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1101215730201607371L, 1, null, java.lang.Long.valueOf(this.mSyncId), java.lang.String.valueOf(windowContainer));
        collect(windowContainer);
        this.mChanges.get(windowContainer).mExistenceChanged = true;
    }

    void collectVisibleChange(com.android.server.wm.WindowContainer windowContainer) {
        if (this.mSyncEngine.getSyncSet(this.mSyncId).mSyncMethod == 1 || windowContainer.mDisplayContent == null || !isInTransition(windowContainer)) {
            return;
        }
        if (!windowContainer.mDisplayContent.getDisplayPolicy().isScreenOnFully() || windowContainer.mDisplayContent.getDisplayInfo().state == 1) {
            this.mFlags |= 1024;
            return;
        }
        if (windowContainer.asActivityRecord() != null) {
            com.android.server.wm.ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
            if (asActivityRecord.mStartingData != null && asActivityRecord.mStartingData.mAssociatedTask != null) {
                return;
            }
        }
        if (this.mContainerFreezer == null) {
            this.mContainerFreezer = new com.android.server.wm.Transition.ScreenshotFreezer();
        }
        com.android.server.wm.Transition.ChangeInfo changeInfo = this.mChanges.get(windowContainer);
        if (changeInfo == null || !changeInfo.mVisible || !windowContainer.isVisibleRequested()) {
            return;
        }
        this.mContainerFreezer.freeze(windowContainer, changeInfo.mAbsoluteBounds);
    }

    void collectReparentChange(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer2) {
        com.android.server.wm.WindowContainer windowContainer3;
        if (!this.mChanges.containsKey(windowContainer)) {
            return;
        }
        com.android.server.wm.Transition.ChangeInfo changeInfo = this.mChanges.get(windowContainer);
        if (changeInfo.mStartParent == null || changeInfo.mStartParent.isAttached()) {
            windowContainer3 = changeInfo.mStartParent;
        } else {
            windowContainer3 = changeInfo.mCommonAncestor;
        }
        if (windowContainer3 == null || !windowContainer3.isAttached()) {
            android.util.Slog.w(TAG, "Trying to collect reparenting of a window after the previous parent has been detached: " + windowContainer);
            return;
        }
        if (windowContainer3 == windowContainer2) {
            android.util.Slog.w(TAG, "Trying to collect reparenting of a window that has not been reparented: " + windowContainer);
            return;
        }
        if (!windowContainer2.isAttached()) {
            android.util.Slog.w(TAG, "Trying to collect reparenting of a window that is not attached after reparenting: " + windowContainer);
            return;
        }
        while (windowContainer3 != windowContainer2 && !windowContainer3.isDescendantOf(windowContainer2)) {
            windowContainer2 = windowContainer2.getParent();
        }
        changeInfo.mCommonAncestor = windowContainer2;
    }

    boolean isInTransition(com.android.server.wm.WindowContainer windowContainer) {
        while (windowContainer != null) {
            if (this.mParticipants.contains(windowContainer)) {
                return true;
            }
            windowContainer = windowContainer.getParent();
        }
        return false;
    }

    void setKnownConfigChanges(com.android.server.wm.WindowContainer<?> windowContainer, int i) {
        com.android.server.wm.Transition.ChangeInfo changeInfo = this.mChanges.get(windowContainer);
        if (changeInfo != null) {
            changeInfo.mKnownConfigChanges = i;
        }
    }

    private void sendRemoteCallback(@android.annotation.Nullable android.os.IRemoteCallback iRemoteCallback) {
        if (iRemoteCallback == null) {
            return;
        }
        this.mController.mAtm.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.Transition.lambda$sendRemoteCallback$3((android.os.IRemoteCallback) obj);
            }
        }, iRemoteCallback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendRemoteCallback$3(android.os.IRemoteCallback iRemoteCallback) {
        try {
            iRemoteCallback.sendResult((android.os.Bundle) null);
        } catch (android.os.RemoteException e) {
        }
    }

    void setOverrideAnimation(android.window.TransitionInfo.AnimationOptions animationOptions, @android.annotation.Nullable android.os.IRemoteCallback iRemoteCallback, @android.annotation.Nullable android.os.IRemoteCallback iRemoteCallback2) {
        if (isCollecting()) {
            this.mOverrideOptions = animationOptions;
            sendRemoteCallback(this.mClientAnimationStartCallback);
            this.mClientAnimationStartCallback = iRemoteCallback;
            this.mClientAnimationFinishCallback = iRemoteCallback2;
        }
    }

    void setReady(com.android.server.wm.WindowContainer windowContainer, boolean z) {
        if (!isCollecting() || this.mSyncId < 0) {
            return;
        }
        this.mReadyTrackerOld.setReadyFrom(windowContainer, z);
        applyReady();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyReady() {
        boolean allReady;
        if (this.mState < 1) {
            return;
        }
        if (this.mController.useFullReadyTracking()) {
            allReady = this.mReadyTracker.isReady();
        } else {
            allReady = this.mReadyTrackerOld.allReady();
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -3942072270654590479L, 7, null, java.lang.Boolean.valueOf(allReady), java.lang.Long.valueOf(this.mSyncId));
        if (this.mSyncEngine.setReady(this.mSyncId, allReady) && allReady) {
            this.mLogger.mReadyTimeNs = android.os.SystemClock.elapsedRealtimeNanos();
            this.mOnTopTasksAtReady.clear();
            for (int i = 0; i < this.mTargetDisplays.size(); i++) {
                addOnTopTasks(this.mTargetDisplays.get(i), this.mOnTopTasksAtReady);
            }
            this.mController.onTransitionPopulated(this);
        }
    }

    void setAllReady() {
        if (!isCollecting() || this.mSyncId < 0) {
            return;
        }
        this.mReadyTrackerOld.setAllReady();
        applyReady();
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean allReady() {
        return this.mReadyTrackerOld.allReady();
    }

    boolean isPopulated() {
        return this.mState >= 1 && this.mReadyTrackerOld.allReady();
    }

    private void resetSurfaceTransform(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.WindowContainer windowContainer, android.view.SurfaceControl surfaceControl) {
        windowContainer.getRelativePosition(new android.graphics.Point());
        transaction.setPosition(surfaceControl, r0.x, r0.y);
        if (windowContainer.asTaskFragment() == null) {
            transaction.setCrop(surfaceControl, null);
        } else {
            android.graphics.Rect resolvedOverrideBounds = windowContainer.getResolvedOverrideBounds();
            transaction.setWindowCrop(surfaceControl, resolvedOverrideBounds.width(), resolvedOverrideBounds.height());
        }
        transaction.setMatrix(surfaceControl, 1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f);
        if (windowContainer.isOrganized() && windowContainer.matchParentBounds()) {
            transaction.setWindowCrop(surfaceControl, -1, -1);
        }
    }

    private void buildFinishTransaction(android.view.SurfaceControl.Transaction transaction, android.window.TransitionInfo transitionInfo) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int size = this.mTargets.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            com.android.server.wm.WindowContainer windowContainer = this.mTargets.get(size).mContainer;
            if (windowContainer.getParent() != null) {
                android.view.SurfaceControl leashSurface = getLeashSurface(windowContainer, null);
                transaction.reparent(leashSurface, getOrigParentSurface(windowContainer));
                transaction.setLayer(leashSurface, windowContainer.getLastLayer());
                transaction.setCornerRadius(leashSurface, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                transaction.setShadowRadius(leashSurface, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                transaction.setAlpha(leashSurface, 1.0f);
                arraySet.add(windowContainer.getDisplayContent());
                if ((this.mTargets.get(size).mFlags & 64) == 0) {
                    resetSurfaceTransform(transaction, windowContainer, leashSurface);
                }
            }
        }
        if (this.mContainerFreezer != null) {
            this.mContainerFreezer.cleanUp(transaction);
        }
        for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
            if (arraySet.valueAt(size2) != null) {
                updateDisplayLayers((com.android.server.wm.DisplayContent) arraySet.valueAt(size2), transaction);
            }
        }
        for (int i = 0; i < transitionInfo.getRootCount(); i++) {
            transaction.reparent(transitionInfo.getRoot(i).getLeash(), null);
        }
    }

    private static void updateDisplayLayers(com.android.server.wm.DisplayContent displayContent, android.view.SurfaceControl.Transaction transaction) {
        displayContent.mTransitionController.mBuildingFinishLayers = true;
        try {
            displayContent.assignChildLayers(transaction);
        } finally {
            displayContent.mTransitionController.mBuildingFinishLayers = false;
        }
    }

    private static void buildCleanupTransaction(android.view.SurfaceControl.Transaction transaction, android.window.TransitionInfo transitionInfo) {
        int size = transitionInfo.getChanges().size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            android.window.TransitionInfo.Change change = (android.window.TransitionInfo.Change) transitionInfo.getChanges().get(size);
            if (change.getSnapshot() != null) {
                transaction.reparent(change.getSnapshot(), null);
            }
            if (change.hasFlags(32) && change.getStartRotation() != change.getEndRotation() && change.getContainer() != null) {
                transaction.unsetFixedTransformHint(com.android.server.wm.WindowContainer.fromBinder(change.getContainer().asBinder()).asDisplayContent().mSurfaceControl);
            }
        }
        for (int rootCount = transitionInfo.getRootCount() - 1; rootCount >= 0; rootCount--) {
            android.view.SurfaceControl leash = transitionInfo.getRoot(rootCount).getLeash();
            if (leash != null) {
                transaction.reparent(leash, null);
            }
        }
    }

    void setCanPipOnFinish(boolean z) {
        this.mCanPipOnFinish = z;
    }

    private boolean didCommitTransientLaunch() {
        if (this.mTransientLaunches == null) {
            return false;
        }
        for (int i = 0; i < this.mTransientLaunches.size(); i++) {
            if (this.mTransientLaunches.keyAt(i).isVisibleRequested()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkEnterPipOnFinish(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        if (!this.mCanPipOnFinish || !activityRecord.isVisible() || activityRecord.getTask() == null || !activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
            return false;
        }
        com.android.server.wm.ActivityRecord visibleTransientLaunch = getVisibleTransientLaunch(activityRecord.getTaskDisplayArea());
        if (activityRecord.pictureInPictureArgs != null && activityRecord.pictureInPictureArgs.isAutoEnterEnabled()) {
            if (!activityRecord.getTask().isVisibleRequested() || didCommitTransientLaunch()) {
                activityRecord.supportsEnterPipOnTaskSwitch = true;
            }
            if (!activityRecord.checkEnterPictureInPictureState("enterPictureInPictureMode", true)) {
                return false;
            }
            int windowingMode = activityRecord.getTask().getWindowingMode();
            boolean enterPictureInPictureMode = this.mController.mAtm.enterPictureInPictureMode(activityRecord, activityRecord.pictureInPictureArgs, false, true);
            int windowingMode2 = activityRecord.getTask().getWindowingMode();
            if (windowingMode == 1 && windowingMode2 == 2 && this.mTransientLaunches != null && activityRecord.mDisplayContent.hasTopFixedRotationLaunchingApp()) {
                activityRecord.mDisplayContent.mPinnedTaskController.setEnterPipTransaction(null);
            }
            return enterPictureInPictureMode;
        }
        if ((!activityRecord.getTask().isVisibleRequested() || didCommitTransientLaunch()) && activityRecord.supportsPictureInPicture()) {
            activityRecord.supportsEnterPipOnTaskSwitch = true;
        }
        try {
            this.mController.mAtm.mTaskSupervisor.mUserLeaving = true;
            activityRecord.getTaskFragment().startPausing(false, visibleTransientLaunch, "finishTransition");
            return false;
        } finally {
            this.mController.mAtm.mTaskSupervisor.mUserLeaving = false;
        }
    }

    void finishTransition() {
        com.android.server.wm.WindowState windowState;
        com.android.server.wm.TaskDisplayArea taskDisplayArea;
        com.android.server.wm.Transition.ChangeInfo changeInfo;
        com.android.server.wm.ActivityRecord topNonFinishingActivity;
        if (android.os.Trace.isTagEnabled(32L) && this.mIsPlayerEnabled) {
            asyncTraceEnd(java.lang.System.identityHashCode(this));
        }
        this.mLogger.mFinishTimeNs = android.os.SystemClock.elapsedRealtimeNanos();
        android.os.Handler handler = this.mController.mLoggerHandler;
        final com.android.server.wm.TransitionController.Logger logger = this.mLogger;
        java.util.Objects.requireNonNull(logger);
        handler.post(new java.lang.Runnable() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.TransitionController.Logger.this.logOnFinish();
            }
        });
        this.mController.mTransitionTracer.logFinishedTransition(this);
        if (this.mStartTransaction != null) {
            this.mStartTransaction.close();
        }
        if (this.mFinishTransaction != null) {
            this.mFinishTransaction.close();
        }
        this.mFinishTransaction = null;
        this.mStartTransaction = null;
        if (this.mCleanupTransaction != null) {
            this.mCleanupTransaction.apply();
            this.mCleanupTransaction = null;
        }
        if (this.mState >= 2) {
            this.mController.mFinishingTransition = this;
            if (this.mTransientHideTasks != null && !this.mTransientHideTasks.isEmpty()) {
                this.mController.mAtm.mRootWindowContainer.ensureActivitiesVisible();
                for (int i = 0; i < this.mTransientHideTasks.size(); i++) {
                    final com.android.server.wm.Task task = this.mTransientHideTasks.get(i);
                    task.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.Transition.this.lambda$finishTransition$4(task, (com.android.server.wm.ActivityRecord) obj);
                        }
                    });
                }
            }
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            boolean z4 = false;
            for (int i2 = 0; i2 < this.mParticipants.size(); i2++) {
                com.android.server.wm.WindowContainer valueAt = this.mParticipants.valueAt(i2);
                com.android.server.wm.ActivityRecord asActivityRecord = valueAt.asActivityRecord();
                if (asActivityRecord != null) {
                    com.android.server.wm.Task task2 = asActivityRecord.getTask();
                    if (task2 != null) {
                        boolean contains = this.mVisibleAtTransitionEndTokens.contains(asActivityRecord);
                        if (isTransientLaunch(asActivityRecord) && !asActivityRecord.isVisibleRequested() && this.mController.inCollectingTransition(asActivityRecord)) {
                            contains = true;
                        }
                        boolean z5 = asActivityRecord.mDisplayContent == null || asActivityRecord.mDisplayContent.getDisplayInfo().state == 1;
                        if ((!contains || z5) && !asActivityRecord.isVisibleRequested()) {
                            if (!checkEnterPipOnFinish(asActivityRecord)) {
                                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -4688704756793656554L, 0, null, java.lang.String.valueOf(asActivityRecord));
                                com.android.server.wm.SnapshotController snapshotController = this.mController.mSnapshotController;
                                if (this.mTransientLaunches != null && !task2.isVisibleRequested() && !task2.isActivityTypeHome()) {
                                    if (snapshotController.mTaskSnapshotController.getSnapshotCaptureTime(task2.mTaskId) < this.mLogger.mSendTimeNs) {
                                        snapshotController.mTaskSnapshotController.recordSnapshot(task2);
                                    } else {
                                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1817207111271920503L, 1, null, java.lang.Long.valueOf(task2.mTaskId));
                                    }
                                }
                                asActivityRecord.commitVisibility(false, false, true);
                                z3 = true;
                            } else {
                                z2 = true;
                            }
                        }
                        com.android.server.wm.Transition.ChangeInfo changeInfo2 = this.mChanges.get(asActivityRecord);
                        if (changeInfo2 != null && changeInfo2.mVisible != contains) {
                            asActivityRecord.mEnteringAnimation = contains;
                        } else if (this.mTransientLaunches != null && this.mTransientLaunches.containsKey(asActivityRecord) && asActivityRecord.isVisible()) {
                            asActivityRecord.mEnteringAnimation = true;
                            if (!task2.isFocused() && asActivityRecord.isTopRunningActivity()) {
                                this.mController.mAtm.setLastResumedActivityUncheckLocked(asActivityRecord, "transitionFinished");
                            }
                            z = true;
                        }
                    }
                } else if (valueAt.asDisplayContent() != null) {
                    z4 = true;
                } else {
                    final com.android.server.wm.Task asTask = valueAt.asTask();
                    if (asTask != null && asTask.isVisibleRequested() && asTask.inPinnedWindowingMode() && (topNonFinishingActivity = asTask.getTopNonFinishingActivity()) != null && !topNonFinishingActivity.inPinnedWindowingMode()) {
                        this.mController.mStateValidators.add(new java.lang.Runnable() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.wm.Transition.lambda$finishTransition$5(com.android.server.wm.Task.this);
                            }
                        });
                    }
                }
            }
            for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
                com.android.server.wm.WallpaperWindowToken asWallpaperToken = this.mParticipants.valueAt(size).asWallpaperToken();
                if (asWallpaperToken != null) {
                    com.android.server.wm.WindowState wallpaperTarget = asWallpaperToken.mDisplayContent.mWallpaperController.getWallpaperTarget();
                    boolean z6 = wallpaperTarget == null || !wallpaperTarget.mToken.isVisible();
                    boolean z7 = asWallpaperToken.isVisibleRequested() || this.mVisibleAtTransitionEndTokens.contains(asWallpaperToken);
                    if (z6 || !z7) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -2960171012238790176L, 0, null, java.lang.String.valueOf(asWallpaperToken));
                        asWallpaperToken.commitVisibility(false);
                    }
                    if (z6) {
                        com.android.server.wm.DisplayContent displayContent = asWallpaperToken.mDisplayContent;
                        displayContent.pendingLayoutChanges = 4 | displayContent.pendingLayoutChanges;
                    }
                }
            }
            if (z3) {
                this.mController.onCommittedInvisibles();
            }
            if (z) {
                if (z2) {
                    this.mController.mAtm.getTaskChangeNotificationController().notifyTaskStackChanged();
                }
                this.mController.mAtm.stopAppSwitches();
                this.mController.mAtm.mRootWindowContainer.rankTaskLayers();
            }
            commitConfigAtEndActivities();
            for (int i3 = 0; i3 < this.mParticipants.size(); i3++) {
                com.android.server.wm.ActivityRecord asActivityRecord2 = this.mParticipants.valueAt(i3).asActivityRecord();
                if (asActivityRecord2 != null && (asActivityRecord2.isVisibleRequested() || !asActivityRecord2.isState(com.android.server.wm.ActivityRecord.State.INITIALIZING))) {
                    this.mController.dispatchLegacyAppTransitionFinished(asActivityRecord2);
                }
            }
            android.view.SurfaceControl.Transaction transaction = null;
            for (int i4 = 0; i4 < this.mParticipants.size(); i4++) {
                com.android.server.wm.ActivityRecord asActivityRecord3 = this.mParticipants.valueAt(i4).asActivityRecord();
                if (asActivityRecord3 != null && asActivityRecord3.isVisible() && asActivityRecord3.getParent() != null) {
                    if (transaction == null) {
                        transaction = asActivityRecord3.mWmService.mTransactionFactory.get();
                    }
                    asActivityRecord3.mActivityRecordInputSink.applyChangesToSurfaceIfChanged(transaction);
                }
            }
            if (transaction != null) {
                transaction.apply();
            }
            this.mController.mAtm.mTaskSupervisor.scheduleProcessStoppingAndFinishingActivitiesIfNeeded();
            sendRemoteCallback(this.mClientAnimationFinishCallback);
            legacyRestoreNavigationBarFromApp();
            if (this.mRecentsDisplayId != -1) {
                com.android.server.wm.DisplayContent displayContent2 = this.mController.mAtm.mRootWindowContainer.getDisplayContent(this.mRecentsDisplayId);
                displayContent2.getInputMonitor().setActiveRecents(null, null);
                displayContent2.getInputMonitor().updateInputWindowsLw(false);
            }
            if (this.mTransientLaunches != null) {
                for (int size2 = this.mTransientLaunches.size() - 1; size2 >= 0; size2--) {
                    com.android.server.wm.Task task3 = this.mTransientLaunches.keyAt(size2).getTask();
                    if (task3 != null) {
                        task3.setCanAffectSystemUiFlags(true);
                    }
                }
            }
            for (int i5 = 0; i5 < this.mTargetDisplays.size(); i5++) {
                com.android.server.wm.DisplayContent displayContent3 = this.mTargetDisplays.get(i5);
                com.android.server.wm.AsyncRotationController asyncRotationController = displayContent3.getAsyncRotationController();
                if (asyncRotationController != null && containsChangeFor(displayContent3, this.mTargets)) {
                    asyncRotationController.onTransitionFinished();
                }
                displayContent3.onTransitionFinished();
                if (z4 && displayContent3.mDisplayRotationCompatPolicy != null && (changeInfo = this.mChanges.get(displayContent3)) != null && changeInfo.mRotation != displayContent3.getWindowConfiguration().getRotation()) {
                    displayContent3.mDisplayRotationCompatPolicy.onScreenRotationAnimationFinished();
                }
                if (this.mTransientLaunches != null) {
                    com.android.server.wm.InsetsControlTarget imeTarget = displayContent3.getImeTarget(2);
                    int i6 = 0;
                    while (true) {
                        if (i6 >= this.mTransientLaunches.size()) {
                            windowState = null;
                            taskDisplayArea = null;
                            break;
                        } else if (this.mTransientLaunches.keyAt(i6).getDisplayContent() != displayContent3) {
                            i6++;
                        } else {
                            windowState = displayContent3.computeImeTarget(true);
                            taskDisplayArea = this.mTransientLaunches.keyAt(i5).getTaskDisplayArea();
                            break;
                        }
                    }
                    if (this.mRecentsDisplayId != -1 && imeTarget == windowState) {
                        com.android.server.inputmethod.InputMethodManagerInternal.get().updateImeWindowStatus(false, displayContent3.getDisplayId());
                    }
                    if (!z && taskDisplayArea != null) {
                        taskDisplayArea.pauseBackTasks(null);
                    }
                }
                displayContent3.removeImeSurfaceImmediately();
                displayContent3.handleCompleteDeferredRemoval();
            }
            validateKeyguardOcclusion();
            this.mState = 4;
            if (z4 && !this.mController.useShellTransitionsRotation()) {
                this.mController.mAtm.mWindowManager.updateRotation(false, false);
            }
            cleanUpInternal();
            this.mController.mAtm.mBackNavigationController.onTransitionFinish(this.mTargets, this);
            this.mController.mFinishingTransition = null;
            this.mController.mSnapshotController.onTransitionFinish(this.mType, this.mTargets);
            this.mController.updateAnimatingState();
            return;
        }
        throw new java.lang.IllegalStateException("Can't finish a non-playing transition " + this.mSyncId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishTransition$4(com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mParticipants.contains(activityRecord.getTask())) {
            if (task.isVisibleRequested()) {
                if (!activityRecord.isVisibleRequested()) {
                    this.mController.mValidateCommitVis.add(activityRecord);
                    return;
                } else {
                    this.mParticipants.add(activityRecord);
                    return;
                }
            }
            this.mParticipants.add(activityRecord);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$finishTransition$5(com.android.server.wm.Task task) {
        if (!task.isAttached() || !task.isVisibleRequested() || !task.inPinnedWindowingMode()) {
            return;
        }
        com.android.server.wm.ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity();
        if (topNonFinishingActivity.inPinnedWindowingMode()) {
            return;
        }
        android.util.Slog.e(TAG, "Enter-PIP was started but not completed, this is a Shell/SysUI bug. This state breaks gesture-nav, so attempting clean-up.");
        task.abortPipEnter(topNonFinishingActivity);
    }

    private void commitConfigAtEndActivities() {
        if (this.mConfigAtEndActivities == null || this.mConfigAtEndActivities.isEmpty()) {
            return;
        }
        final android.view.SurfaceControl.Transaction transaction = this.mController.mAtm.mWindowManager.mTransactionFactory.get();
        for (int i = 0; i < this.mTargets.size(); i++) {
            com.android.server.wm.WindowContainer windowContainer = this.mTargets.get(i).mContainer;
            if (windowContainer.getParent() != null && (this.mTargets.get(i).mFlags & 64) != 0) {
                resetSurfaceTransform(transaction, windowContainer, getLeashSurface(windowContainer, null));
            }
        }
        com.android.server.wm.BLASTSyncEngine.SyncGroup prepareSyncSet = this.mSyncEngine.prepareSyncSet(new com.android.server.wm.BLASTSyncEngine.TransactionReadyListener() { // from class: com.android.server.wm.Transition.1
            @Override // com.android.server.wm.BLASTSyncEngine.TransactionReadyListener
            public void onTransactionReady(int i2, android.view.SurfaceControl.Transaction transaction2) {
                transaction.merge(transaction2);
                transaction.apply();
            }

            @Override // com.android.server.wm.BLASTSyncEngine.TransactionReadyListener
            public void onTransactionCommitTimeout() {
                transaction.apply();
            }
        }, "ConfigAtTransitEnd");
        int i2 = prepareSyncSet.mSyncId;
        this.mSyncEngine.startSyncSet(prepareSyncSet, 5000L, true);
        this.mSyncEngine.setSyncMethod(i2, 1);
        for (int i3 = 0; i3 < this.mConfigAtEndActivities.size(); i3++) {
            com.android.server.wm.ActivityRecord activityRecord = this.mConfigAtEndActivities.get(i3);
            this.mSyncEngine.addToSyncSet(i2, activityRecord);
            activityRecord.resumeConfigurationDispatch();
        }
        this.mSyncEngine.setReady(i2);
    }

    @android.annotation.Nullable
    private com.android.server.wm.ActivityRecord getVisibleTransientLaunch(com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        if (this.mTransientLaunches == null) {
            return null;
        }
        for (int size = this.mTransientLaunches.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord keyAt = this.mTransientLaunches.keyAt(size);
            if (keyAt.getTaskDisplayArea() == taskDisplayArea && keyAt.isVisibleRequested()) {
                return keyAt;
            }
        }
        return null;
    }

    void abort() {
        if (this.mState == 3) {
            return;
        }
        if (this.mState == -1) {
            this.mState = 3;
            return;
        }
        if (this.mState != 0 && this.mState != 1) {
            throw new java.lang.IllegalStateException("Too late to abort. state=" + this.mState);
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1230784960534033968L, 1, null, java.lang.Long.valueOf(this.mSyncId));
        this.mState = 3;
        this.mLogger.mAbortTimeNs = android.os.SystemClock.elapsedRealtimeNanos();
        this.mController.mTransitionTracer.logAbortedTransition(this);
        this.mSyncEngine.abort(this.mSyncId);
        this.mController.dispatchLegacyAppTransitionCancelled();
    }

    void playNow() {
        if (this.mState != 0 && this.mState != 1) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -892865733969888022L, 1, null, java.lang.Long.valueOf(this.mSyncId));
        this.mForcePlaying = true;
        for (int size = this.mReadyTracker.mConditions.size() - 1; size >= 0; size--) {
            this.mReadyTracker.mConditions.get(size).meetAlternate("play-now");
        }
        com.android.server.wm.Transition.ReadyCondition readyCondition = new com.android.server.wm.Transition.ReadyCondition("force-play-now");
        this.mReadyTracker.add(readyCondition);
        readyCondition.meet();
        setAllReady();
        if (this.mState == 0) {
            start();
        }
        this.mSyncEngine.onSurfacePlacement();
    }

    boolean isForcePlaying() {
        return this.mForcePlaying;
    }

    void setRemoteAnimationApp(android.app.IApplicationThread iApplicationThread) {
        com.android.server.wm.WindowProcessController processController = this.mController.mAtm.getProcessController(iApplicationThread);
        if (processController != null) {
            this.mController.mRemotePlayer.update(processController, true, true);
        }
    }

    void setNoAnimation(com.android.server.wm.WindowContainer windowContainer) {
        com.android.server.wm.Transition.ChangeInfo changeInfo = this.mChanges.get(windowContainer);
        if (changeInfo == null) {
            throw new java.lang.IllegalStateException("Can't set no-animation property of non-participant");
        }
        changeInfo.mFlags |= 8;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static boolean containsChangeFor(com.android.server.wm.WindowContainer windowContainer, java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).mContainer == windowContainer) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.wm.BLASTSyncEngine.TransactionReadyListener
    public void onTransactionReady(int i, android.view.SurfaceControl.Transaction transaction) {
        int i2;
        if (i != this.mSyncId) {
            android.util.Slog.e(TAG, "Unexpected Sync ID " + i + ". Expected " + this.mSyncId);
            return;
        }
        int i3 = 0;
        if (this.mController.useFullReadyTracking()) {
            if (this.mReadyTracker.mMet.isEmpty()) {
                android.util.Slog.e(TAG, "#" + this.mSyncId + ": No conditions provided");
            } else {
                for (int i4 = 0; i4 < this.mReadyTracker.mConditions.size(); i4++) {
                    android.util.Slog.e(TAG, "#" + this.mSyncId + ": unmet condition at ready: " + this.mReadyTracker.mConditions.get(i4));
                }
            }
            for (int i5 = 0; i5 < this.mReadyTracker.mMet.size(); i5++) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1354622424895965634L, 1, null, java.lang.Long.valueOf(this.mSyncId), java.lang.String.valueOf(this.mReadyTracker.mMet.get(i5)));
            }
        }
        commitVisibleActivities(transaction);
        commitVisibleWallpapers();
        com.android.server.wm.DisplayContent defaultDisplay = !this.mTargetDisplays.isEmpty() ? this.mTargetDisplays.get(0) : this.mController.mAtm.mRootWindowContainer.getDefaultDisplay();
        if (this.mState == 3) {
            this.mController.onAbort(this);
            if (this.mConfigAtEndActivities != null) {
                while (i3 < this.mConfigAtEndActivities.size()) {
                    this.mConfigAtEndActivities.get(i3).resumeConfigurationDispatch();
                    i3++;
                }
                this.mConfigAtEndActivities = null;
            }
            defaultDisplay.getPendingTransaction().merge(transaction);
            this.mSyncId = -1;
            this.mOverrideOptions = null;
            cleanUpInternal();
            return;
        }
        if (this.mState != 1) {
            android.util.Slog.e(TAG, "Playing a Transition which hasn't started! #" + this.mSyncId + " This will likely cause an exception in Shell");
        }
        this.mState = 2;
        this.mStartTransaction = transaction;
        this.mFinishTransaction = this.mController.mAtm.mWindowManager.mTransactionFactory.get();
        if (defaultDisplay.isKeyguardLocked()) {
            this.mFlags |= 64;
        }
        collectOrderChanges(this.mController.mWaitingTransitions.isEmpty());
        if (this.mPriorVisibilityMightBeDirty) {
            updatePriorVisibility();
        }
        this.mTargets = calculateTargets(this.mParticipants, this.mChanges);
        this.mController.mAtm.mBackNavigationController.onTransactionReady(this, this.mTargets, transaction);
        android.window.TransitionInfo calculateTransitionInfo = calculateTransitionInfo(this.mType, this.mFlags, this.mTargets, transaction);
        calculateTransitionInfo.setDebugId(this.mSyncId);
        this.mController.assignTrack(this, calculateTransitionInfo);
        this.mController.moveToPlaying(this);
        this.mTargetDisplays.clear();
        for (int i6 = 0; i6 < calculateTransitionInfo.getRootCount(); i6++) {
            this.mTargetDisplays.add(this.mController.mAtm.mRootWindowContainer.getDisplayContent(calculateTransitionInfo.getRoot(i6).getDisplayId()));
        }
        for (int i7 = 0; i7 < this.mTargets.size(); i7++) {
            com.android.server.wm.DisplayArea asDisplayArea = this.mTargets.get(i7).mContainer.asDisplayArea();
            if (asDisplayArea != null) {
                if (asDisplayArea.isVisibleRequested()) {
                    this.mController.mValidateDisplayVis.remove(asDisplayArea);
                } else {
                    this.mController.mValidateDisplayVis.add(asDisplayArea);
                }
            }
        }
        if (this.mOverrideOptions != null) {
            calculateTransitionInfo.setAnimationOptions(this.mOverrideOptions);
            if (this.mOverrideOptions.getType() == 12) {
                int i8 = 0;
                while (true) {
                    if (i8 >= this.mTargets.size()) {
                        break;
                    }
                    android.window.TransitionInfo.Change change = (android.window.TransitionInfo.Change) calculateTransitionInfo.getChanges().get(i8);
                    com.android.server.wm.ActivityRecord asActivityRecord = this.mTargets.get(i8).mContainer.asActivityRecord();
                    if (asActivityRecord == null || change.getMode() != 1) {
                        i8++;
                    } else {
                        int flags = change.getFlags();
                        if (asActivityRecord.mUserId == asActivityRecord.mWmService.mCurrentUserId) {
                            i2 = 4096;
                        } else {
                            i2 = 8192;
                        }
                        change.setFlags(flags | i2);
                    }
                }
            }
        }
        for (int i9 = 0; i9 < this.mTargetDisplays.size(); i9++) {
            handleLegacyRecentsStartBehavior(this.mTargetDisplays.get(i9), calculateTransitionInfo);
            if (this.mRecentsDisplayId != -1) {
                break;
            }
        }
        sendRemoteCallback(this.mClientAnimationStartCallback);
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord asActivityRecord2 = this.mParticipants.valueAt(size).asActivityRecord();
            if (asActivityRecord2 != null && asActivityRecord2.isVisibleRequested()) {
                transaction.show(asActivityRecord2.getSurfaceControl());
                for (com.android.server.wm.WindowContainer parent = asActivityRecord2.getParent(); parent != null && !containsChangeFor(parent, this.mTargets); parent = parent.getParent()) {
                    if (parent.getSurfaceControl() != null) {
                        transaction.show(parent.getSurfaceControl());
                    }
                }
            }
        }
        if (this.mTransientLaunches == null) {
            for (int size2 = this.mParticipants.size() - 1; size2 >= 0; size2--) {
                com.android.server.wm.WindowContainer valueAt = this.mParticipants.valueAt(size2);
                if (valueAt.asWindowToken() != null && valueAt.isVisibleRequested()) {
                    this.mVisibleAtTransitionEndTokens.add(valueAt.asWindowToken());
                }
            }
        }
        for (int i10 = 0; i10 < this.mTargetDisplays.size(); i10++) {
            com.android.server.wm.DisplayContent displayContent = this.mTargetDisplays.get(i10);
            com.android.server.wm.AsyncRotationController asyncRotationController = displayContent.getAsyncRotationController();
            if (asyncRotationController != null && containsChangeFor(displayContent, this.mTargets)) {
                asyncRotationController.setupStartTransaction(transaction);
            }
        }
        buildFinishTransaction(this.mFinishTransaction, calculateTransitionInfo);
        this.mCleanupTransaction = this.mController.mAtm.mWindowManager.mTransactionFactory.get();
        buildCleanupTransaction(this.mCleanupTransaction, calculateTransitionInfo);
        if (this.mController.getTransitionPlayer() != null && this.mIsPlayerEnabled) {
            this.mController.dispatchLegacyAppTransitionStarting(calculateTransitionInfo, this.mStatusBarTransitionDelay);
            try {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -5350671621840749173L, 0, null, java.lang.String.valueOf(calculateTransitionInfo));
                this.mLogger.mSendTimeNs = android.os.SystemClock.elapsedRealtimeNanos();
                this.mLogger.mInfo = calculateTransitionInfo;
                this.mController.getTransitionPlayer().onTransitionReady(this.mToken, calculateTransitionInfo, transaction, this.mFinishTransaction);
                if (android.os.Trace.isTagEnabled(32L)) {
                    asyncTraceBegin(TRACE_NAME_PLAY_TRANSITION, java.lang.System.identityHashCode(this));
                }
            } catch (android.os.RemoteException e) {
                postCleanupOnFailure();
            }
            while (i3 < this.mTargetDisplays.size()) {
                com.android.server.wm.DisplayContent displayContent2 = this.mTargetDisplays.get(i3);
                com.android.server.wm.AccessibilityController accessibilityController = displayContent2.mWmService.mAccessibilityController;
                if (accessibilityController.hasCallbacks()) {
                    accessibilityController.onWMTransition(displayContent2.getDisplayId(), this.mType);
                }
                i3++;
            }
        } else {
            if (!this.mIsPlayerEnabled) {
                this.mLogger.mSendTimeNs = android.os.SystemClock.uptimeNanos();
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1830385055586991567L, 1, null, java.lang.Long.valueOf(this.mSyncId));
            }
            postCleanupOnFailure();
        }
        this.mOverrideOptions = null;
        reportStartReasonsToLogger();
        if (this.mTransientLaunches == null) {
            this.mController.mSnapshotController.onTransactionReady(this.mType, this.mTargets);
        }
        calculateTransitionInfo.releaseAnimSurfaces();
        if (this.mLogger.mInfo != null) {
            this.mLogger.logOnSendAsync(this.mController.mLoggerHandler);
            this.mController.mTransitionTracer.logSentTransition(this, this.mTargets);
        }
    }

    @Override // com.android.server.wm.BLASTSyncEngine.TransactionReadyListener
    public void onTransactionCommitTimeout() {
        if (this.mCleanupTransaction == null) {
            return;
        }
        for (int size = this.mTargetDisplays.size() - 1; size >= 0; size--) {
            com.android.server.wm.DisplayContent displayContent = this.mTargetDisplays.get(size);
            com.android.server.wm.AsyncRotationController asyncRotationController = displayContent.getAsyncRotationController();
            if (asyncRotationController != null && containsChangeFor(displayContent, this.mTargets)) {
                asyncRotationController.onTransactionCommitTimeout(this.mCleanupTransaction);
            }
        }
    }

    boolean hasOrderChanges() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.android.server.wm.DisplayContent> it = this.mTargetDisplays.iterator();
        while (it.hasNext()) {
            addOnTopTasks(it.next(), (java.util.ArrayList<com.android.server.wm.Task>) arrayList);
        }
        java.util.Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            if (!this.mOnTopTasksStart.contains((com.android.server.wm.Task) it2.next())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0091  */
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void collectOrderChanges(boolean z) {
        boolean z2;
        int indexOfKey;
        if (this.mOnTopTasksStart.isEmpty()) {
            return;
        }
        int i = 0;
        while (true) {
            if (i >= this.mOnTopTasksAtReady.size()) {
                z2 = false;
                break;
            }
            if (this.mOnTopTasksStart.contains(this.mOnTopTasksAtReady.get(i))) {
                i++;
            } else {
                z2 = true;
                break;
            }
        }
        if (!z2 && !z) {
            return;
        }
        java.util.ArrayList<com.android.server.wm.Task> arrayList = new java.util.ArrayList<>();
        for (int i2 = 0; i2 < this.mTargetDisplays.size(); i2++) {
            addOnTopTasks(this.mTargetDisplays.get(i2), arrayList);
            int i3 = this.mTargetDisplays.get(i2).mDisplayId;
            java.util.ArrayList<com.android.server.wm.Task> arrayList2 = this.mController.mLatestOnTopTasksReported.get(i3);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                com.android.server.wm.Task task = arrayList.get(size);
                if (task.getDisplayId() == i3) {
                    if (arrayList2 == null) {
                        if (this.mOnTopTasksStart.contains(task)) {
                        }
                        this.mParticipants.add(task);
                        indexOfKey = this.mChanges.indexOfKey(task);
                        if (indexOfKey < 0) {
                            this.mChanges.put(task, new com.android.server.wm.Transition.ChangeInfo(task));
                            indexOfKey = this.mChanges.indexOfKey(task);
                        }
                        this.mChanges.valueAt(indexOfKey).mFlags |= 32;
                    } else {
                        if (arrayList2.contains(task)) {
                        }
                        this.mParticipants.add(task);
                        indexOfKey = this.mChanges.indexOfKey(task);
                        if (indexOfKey < 0) {
                        }
                        this.mChanges.valueAt(indexOfKey).mFlags |= 32;
                    }
                }
            }
            this.mController.mLatestOnTopTasksReported.put(i3, arrayList);
            arrayList = arrayList2 != null ? arrayList2 : new java.util.ArrayList<>();
            arrayList.clear();
        }
    }

    private void postCleanupOnFailure() {
        this.mController.mAtm.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.Transition.this.lambda$postCleanupOnFailure$6();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postCleanupOnFailure$6() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mController.mAtm.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                cleanUpOnFailure();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void cleanUpOnFailure() {
        if (this.mState < 2) {
            return;
        }
        if (this.mStartTransaction != null) {
            this.mStartTransaction.apply();
        }
        if (this.mFinishTransaction != null) {
            this.mFinishTransaction.apply();
        }
        this.mController.finishTransition(this);
    }

    private void cleanUpInternal() {
        for (int i = 0; i < this.mChanges.size(); i++) {
            com.android.server.wm.Transition.ChangeInfo valueAt = this.mChanges.valueAt(i);
            if (valueAt.mSnapshot != null) {
                valueAt.mSnapshot.release();
            }
        }
        if (this.mCleanupTransaction != null) {
            this.mCleanupTransaction.apply();
            this.mCleanupTransaction = null;
        }
    }

    private void commitVisibleActivities(android.view.SurfaceControl.Transaction transaction) {
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord asActivityRecord = this.mParticipants.valueAt(size).asActivityRecord();
            if (asActivityRecord != null && asActivityRecord.getTask() != null) {
                if (asActivityRecord.isVisibleRequested()) {
                    asActivityRecord.commitVisibility(true, false, true);
                    asActivityRecord.commitFinishDrawing(transaction);
                }
                asActivityRecord.getTask().setDeferTaskAppear(false);
            }
        }
    }

    private void commitVisibleWallpapers() {
        boolean shouldWallpaperBeVisible = shouldWallpaperBeVisible();
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            com.android.server.wm.WallpaperWindowToken asWallpaperToken = this.mParticipants.valueAt(size).asWallpaperToken();
            if (asWallpaperToken != null && !asWallpaperToken.isVisible() && asWallpaperToken.isVisibleRequested()) {
                asWallpaperToken.commitVisibility(shouldWallpaperBeVisible);
            }
        }
    }

    private boolean shouldWallpaperBeVisible() {
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            if (this.mParticipants.valueAt(size).showWallpaper()) {
                return true;
            }
        }
        return false;
    }

    private void handleLegacyRecentsStartBehavior(com.android.server.wm.DisplayContent displayContent, android.window.TransitionInfo transitionInfo) {
        com.android.server.wm.ActivityRecord activityRecord;
        com.android.server.wm.WindowState navigationBar;
        com.android.server.wm.Task fromWindowContainerToken;
        if ((this.mFlags & 128) == 0) {
            return;
        }
        com.android.server.wm.InputConsumerImpl inputConsumer = displayContent.getInputMonitor().getInputConsumer("recents_animation_input_consumer");
        com.android.server.wm.WindowContainer windowContainer = null;
        if (inputConsumer == null) {
            activityRecord = null;
        } else {
            activityRecord = null;
            com.android.server.wm.Task task = null;
            for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
                android.app.ActivityManager.RunningTaskInfo taskInfo = ((android.window.TransitionInfo.Change) transitionInfo.getChanges().get(i)).getTaskInfo();
                if (taskInfo != null && (fromWindowContainerToken = com.android.server.wm.Task.fromWindowContainerToken(taskInfo.token)) != null) {
                    int i2 = taskInfo.topActivityType;
                    boolean z = i2 == 2 || i2 == 3;
                    if (z && activityRecord == null) {
                        activityRecord = fromWindowContainerToken.getTopVisibleActivity();
                    } else if (!z && task == null) {
                        task = fromWindowContainerToken;
                    }
                }
            }
            if (activityRecord != null && task != null) {
                inputConsumer.mWindowHandle.touchableRegion.set(task.getBounds());
                displayContent.getInputMonitor().setActiveRecents(activityRecord, task);
            }
        }
        if (activityRecord == null) {
            return;
        }
        this.mRecentsDisplayId = displayContent.mDisplayId;
        if (!displayContent.getDisplayPolicy().shouldAttachNavBarToAppDuringTransition() || displayContent.getAsyncRotationController() != null) {
            return;
        }
        for (int i3 = 0; i3 < transitionInfo.getChanges().size(); i3++) {
            android.window.TransitionInfo.Change change = (android.window.TransitionInfo.Change) transitionInfo.getChanges().get(i3);
            if (change.getTaskInfo() != null && change.getTaskInfo().displayId == this.mRecentsDisplayId && change.getTaskInfo().getActivityType() == 1 && (change.getMode() == 2 || change.getMode() == 4)) {
                windowContainer = com.android.server.wm.WindowContainer.fromBinder(change.getContainer().asBinder());
                break;
            }
        }
        if (windowContainer == null || windowContainer.inMultiWindowMode() || (navigationBar = displayContent.getDisplayPolicy().getNavigationBar()) == null || navigationBar.mToken == null) {
            return;
        }
        this.mController.mNavigationBarAttachedToApp = true;
        navigationBar.mToken.cancelAnimation();
        android.view.SurfaceControl.Transaction pendingTransaction = navigationBar.mToken.getPendingTransaction();
        android.view.SurfaceControl surfaceControl = navigationBar.mToken.getSurfaceControl();
        pendingTransaction.reparent(surfaceControl, windowContainer.getSurfaceControl());
        pendingTransaction.show(surfaceControl);
        com.android.server.wm.DisplayArea.Tokens imeContainer = displayContent.getImeContainer();
        if (imeContainer.isVisible()) {
            pendingTransaction.setRelativeLayer(surfaceControl, imeContainer.getSurfaceControl(), 1);
        } else {
            pendingTransaction.setLayer(surfaceControl, Integer.MAX_VALUE);
        }
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = displayContent.getDisplayPolicy().getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.setNavigationBarLumaSamplingEnabled(this.mRecentsDisplayId, false);
        }
    }

    void legacyRestoreNavigationBarFromApp() {
        if (!this.mController.mNavigationBarAttachedToApp) {
            return;
        }
        boolean z = false;
        this.mController.mNavigationBarAttachedToApp = false;
        int i = this.mRecentsDisplayId;
        if (i == -1) {
            android.util.Slog.i(TAG, "Restore parent surface of navigation bar by another transition");
            i = 0;
        }
        com.android.server.wm.DisplayContent displayContent = this.mController.mAtm.mRootWindowContainer.getDisplayContent(i);
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = displayContent.getDisplayPolicy().getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.setNavigationBarLumaSamplingEnabled(i, true);
        }
        com.android.server.wm.WindowState navigationBar = displayContent.getDisplayPolicy().getNavigationBar();
        if (navigationBar == null) {
            return;
        }
        navigationBar.setSurfaceTranslationY(0);
        com.android.server.wm.WindowToken windowToken = navigationBar.mToken;
        if (windowToken == null) {
            return;
        }
        android.view.SurfaceControl.Transaction pendingTransaction = displayContent.getPendingTransaction();
        com.android.server.wm.WindowContainer parent = windowToken.getParent();
        pendingTransaction.setLayer(windowToken.getSurfaceControl(), windowToken.getLastLayer());
        int i2 = 0;
        while (true) {
            if (i2 < this.mTargets.size()) {
                com.android.server.wm.Task asTask = this.mTargets.get(i2).mContainer.asTask();
                if (asTask == null || !asTask.isActivityTypeHomeOrRecents()) {
                    i2++;
                } else {
                    z = asTask.isVisibleRequested();
                    break;
                }
            } else {
                break;
            }
        }
        if (z) {
            new com.android.server.wm.NavBarFadeAnimationController(displayContent).fadeWindowToken(true);
        } else {
            pendingTransaction.reparent(windowToken.getSurfaceControl(), parent.getSurfaceControl());
        }
        displayContent.mWmService.scheduleAnimationLocked();
    }

    private void reportStartReasonsToLogger() {
        int i;
        android.util.ArrayMap<com.android.server.wm.WindowContainer, java.lang.Integer> arrayMap = new android.util.ArrayMap<>();
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord asActivityRecord = this.mParticipants.valueAt(size).asActivityRecord();
            if (asActivityRecord != null && asActivityRecord.isVisibleRequested()) {
                if ((asActivityRecord.mStartingData instanceof com.android.server.wm.SplashScreenStartingData) && !asActivityRecord.mLastAllReadyAtSync) {
                    i = 1;
                } else if (asActivityRecord.isActivityTypeHomeOrRecents() && isTransientLaunch(asActivityRecord)) {
                    i = 5;
                } else {
                    i = 2;
                }
                arrayMap.put(asActivityRecord, java.lang.Integer.valueOf(i));
            }
        }
        this.mController.mAtm.mTaskSupervisor.getActivityMetricsLogger().notifyTransitionStarting(arrayMap);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
        sb.append("TransitionRecord{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(" id=" + this.mSyncId);
        sb.append(" type=" + android.view.WindowManager.transitTypeToString(this.mType));
        sb.append(" flags=0x" + java.lang.Integer.toHexString(this.mFlags));
        sb.append('}');
        return sb.toString();
    }

    private static com.android.server.wm.WindowContainer<?> getAnimatableParent(com.android.server.wm.WindowContainer<?> windowContainer) {
        com.android.server.wm.WindowContainer<?> parent = windowContainer.getParent();
        while (parent != null && !parent.canCreateRemoteAnimationTarget() && !parent.isOrganized()) {
            parent = parent.getParent();
        }
        return parent;
    }

    private static boolean reportIfNotTop(com.android.server.wm.WindowContainer windowContainer) {
        return windowContainer.isOrganized();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isWallpaper(com.android.server.wm.WindowContainer windowContainer) {
        return windowContainer.asWallpaperToken() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isInputMethod(com.android.server.wm.WindowContainer windowContainer) {
        return windowContainer.getWindowType() == 2011;
    }

    private static boolean occludesKeyguard(com.android.server.wm.WindowContainer windowContainer) {
        com.android.server.wm.ActivityRecord activity;
        com.android.server.wm.ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
        if (asActivityRecord != null) {
            return asActivityRecord.canShowWhenLocked();
        }
        com.android.server.wm.Task asTask = windowContainer.asTask();
        return (asTask == null || (activity = asTask.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return ((com.android.server.wm.ActivityRecord) obj).isClientVisible();
            }
        })) == null || !activity.canShowWhenLocked()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isTranslucent(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        com.android.server.wm.TaskFragment asTaskFragment = windowContainer.asTaskFragment();
        if (asTaskFragment == null) {
            return !windowContainer.fillsParent();
        }
        if (asTaskFragment.isTranslucentForTransition()) {
            return true;
        }
        com.android.server.wm.TaskFragment adjacentTaskFragment = asTaskFragment.getAdjacentTaskFragment();
        if (adjacentTaskFragment == null) {
            return !windowContainer.fillsParent();
        }
        return adjacentTaskFragment.isTranslucentForTransition();
    }

    private void updatePriorVisibility() {
        for (int i = 0; i < this.mChanges.size(); i++) {
            com.android.server.wm.Transition.ChangeInfo valueAt = this.mChanges.valueAt(i);
            if ((valueAt.mContainer.asActivityRecord() != null || valueAt.mContainer.asTask() != null) && valueAt.mVisible) {
                valueAt.mVisible = valueAt.mContainer.isVisible();
            }
        }
    }

    private static boolean canPromote(com.android.server.wm.Transition.ChangeInfo changeInfo, com.android.server.wm.Transition.Targets targets, android.util.ArrayMap<com.android.server.wm.WindowContainer, com.android.server.wm.Transition.ChangeInfo> arrayMap) {
        com.android.server.wm.WindowContainer windowContainer = changeInfo.mContainer;
        com.android.server.wm.WindowContainer parent = windowContainer.getParent();
        com.android.server.wm.Transition.ChangeInfo changeInfo2 = arrayMap.get(parent);
        if (!parent.canCreateRemoteAnimationTarget() || changeInfo2 == null || !changeInfo2.hasChanged()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -758501334967569539L, 0, null, java.lang.String.valueOf("parent can't be target " + parent));
            return false;
        }
        if (isWallpaper(windowContainer)) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -2714847784842612086L, 0, null, null);
            return false;
        }
        if (changeInfo.mStartParent != null && windowContainer.getParent() != changeInfo.mStartParent) {
            return false;
        }
        int transitMode = changeInfo.getTransitMode(windowContainer);
        for (int childCount = parent.getChildCount() - 1; childCount >= 0; childCount--) {
            com.android.server.wm.WindowContainer childAt = parent.getChildAt(childCount);
            if (windowContainer != childAt) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 1855461834864671586L, 0, null, java.lang.String.valueOf(childAt));
                com.android.server.wm.Transition.ChangeInfo changeInfo3 = arrayMap.get(childAt);
                if (changeInfo3 == null || !targets.wasParticipated(changeInfo3)) {
                    if (childAt.isVisibleRequested()) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -6292043690918793069L, 0, null, null);
                        return false;
                    }
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 7897657428993391672L, 0, null, java.lang.String.valueOf(childAt));
                } else {
                    int transitMode2 = changeInfo3.getTransitMode(childAt);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 3873493605120555608L, 0, null, java.lang.String.valueOf(android.window.TransitionInfo.modeToString(transitMode2)));
                    if (reduceMode(transitMode) != reduceMode(transitMode2)) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 7665553560859456426L, 0, null, java.lang.String.valueOf(android.window.TransitionInfo.modeToString(transitMode)));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @android.window.TransitionInfo.TransitionMode
    private static int reduceMode(@android.window.TransitionInfo.TransitionMode int i) {
        switch (i) {
            case 3:
                return 1;
            case 4:
                return 2;
            default:
                return i;
        }
    }

    private static void tryPromote(com.android.server.wm.Transition.Targets targets, android.util.ArrayMap<com.android.server.wm.WindowContainer, com.android.server.wm.Transition.ChangeInfo> arrayMap) {
        int size = targets.mArray.size() - 1;
        com.android.server.wm.WindowContainer windowContainer = null;
        while (size >= 0) {
            com.android.server.wm.Transition.ChangeInfo valueAt = targets.mArray.valueAt(size);
            com.android.server.wm.WindowContainer windowContainer2 = valueAt.mContainer;
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -8916099332247176657L, 0, null, java.lang.String.valueOf(windowContainer2));
            com.android.server.wm.WindowContainer parent = windowContainer2.getParent();
            if (parent == windowContainer) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -6818387694968032301L, 0, null, null);
            } else if (!canPromote(valueAt, targets, arrayMap)) {
                windowContainer = parent;
            } else {
                if (reportIfNotTop(windowContainer2)) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -7326702978448933012L, 0, null, java.lang.String.valueOf(windowContainer2));
                } else {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 943961036184959431L, 0, null, java.lang.String.valueOf(windowContainer2));
                    targets.remove(size);
                }
                com.android.server.wm.Transition.ChangeInfo changeInfo = arrayMap.get(parent);
                if (targets.mArray.indexOfValue(changeInfo) < 0) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 841543868388687804L, 0, null, java.lang.String.valueOf(parent));
                    size++;
                    targets.add(changeInfo);
                }
                if ((valueAt.mFlags & 8) != 0) {
                    changeInfo.mFlags |= 8;
                } else {
                    changeInfo.mFlags |= 16;
                }
                com.android.server.wm.ActivityRecord asActivityRecord = valueAt.mContainer.asActivityRecord();
                if ((asActivityRecord != null && asActivityRecord.isConfigurationDispatchPaused()) || (valueAt.mFlags & 64) != 0) {
                    changeInfo.mFlags |= 64;
                }
            }
            size--;
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    static java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> calculateTargets(android.util.ArraySet<com.android.server.wm.WindowContainer> arraySet, android.util.ArrayMap<com.android.server.wm.WindowContainer, com.android.server.wm.Transition.ChangeInfo> arrayMap) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 743586316159041023L, 0, null, java.lang.String.valueOf(arraySet));
        com.android.server.wm.Transition.Targets targets = new com.android.server.wm.Transition.Targets();
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer valueAt = arraySet.valueAt(size);
            if (!valueAt.isAttached()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -7247430213293162757L, 0, null, java.lang.String.valueOf(valueAt));
            } else if (valueAt.asWindowState() == null) {
                com.android.server.wm.Transition.ChangeInfo changeInfo = arrayMap.get(valueAt);
                if (changeInfo.hasChanged()) {
                    targets.add(changeInfo);
                } else {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -5811837191094192313L, 0, null, java.lang.String.valueOf(valueAt));
                }
            }
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -1153926883525904120L, 0, null, java.lang.String.valueOf(targets.mArray));
        tryPromote(targets, arrayMap);
        populateParentChanges(targets, arrayMap);
        java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> listSortedByZ = targets.getListSortedByZ();
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -9191328656870721224L, 0, null, java.lang.String.valueOf(listSortedByZ));
        return listSortedByZ;
    }

    private static void populateParentChanges(com.android.server.wm.Transition.Targets targets, android.util.ArrayMap<com.android.server.wm.WindowContainer, com.android.server.wm.Transition.ChangeInfo> arrayMap) {
        int i;
        boolean z;
        com.android.server.wm.Transition.ChangeInfo changeInfo;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList(targets.mArray.size());
        for (int size = targets.mArray.size() - 1; size >= 0; size--) {
            arrayList2.add(targets.mArray.valueAt(size));
        }
        for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
            com.android.server.wm.Transition.ChangeInfo changeInfo2 = (com.android.server.wm.Transition.ChangeInfo) arrayList2.get(size2);
            com.android.server.wm.WindowContainer windowContainer = changeInfo2.mContainer;
            boolean isWallpaper = isWallpaper(windowContainer);
            arrayList.clear();
            com.android.server.wm.WindowContainer<?> animatableParent = getAnimatableParent(windowContainer);
            while (true) {
                i = 0;
                if (animatableParent == null || (changeInfo = arrayMap.get(animatableParent)) == null || !changeInfo.hasChanged()) {
                    break;
                }
                if (animatableParent.mRemoteToken != null) {
                    if (changeInfo.mEndParent != null && !isWallpaper) {
                        changeInfo2.mEndParent = animatableParent;
                        break;
                    }
                    if (arrayList2.contains(changeInfo)) {
                        if (isWallpaper) {
                            changeInfo2.mEndParent = animatableParent;
                        } else {
                            arrayList.add(changeInfo);
                        }
                        z = true;
                    } else if (reportIfNotTop(animatableParent) && !isWallpaper) {
                        arrayList.add(changeInfo);
                    }
                }
                animatableParent = getAnimatableParent(animatableParent);
            }
            z = false;
            if (z && !arrayList.isEmpty()) {
                changeInfo2.mEndParent = ((com.android.server.wm.Transition.ChangeInfo) arrayList.get(0)).mContainer;
                while (i < arrayList.size() - 1) {
                    com.android.server.wm.Transition.ChangeInfo changeInfo3 = (com.android.server.wm.Transition.ChangeInfo) arrayList.get(i);
                    i++;
                    changeInfo3.mEndParent = ((com.android.server.wm.Transition.ChangeInfo) arrayList.get(i)).mContainer;
                    targets.add(changeInfo3);
                }
            }
        }
    }

    private static android.view.SurfaceControl getLeashSurface(com.android.server.wm.WindowContainer windowContainer, @android.annotation.Nullable android.view.SurfaceControl.Transaction transaction) {
        com.android.server.wm.WindowToken asWindowToken;
        com.android.server.wm.DisplayContent asDisplayContent = windowContainer.asDisplayContent();
        if (asDisplayContent != null) {
            return asDisplayContent.getWindowingLayer();
        }
        if (!windowContainer.mTransitionController.useShellTransitionsRotation() && (asWindowToken = windowContainer.asWindowToken()) != null) {
            android.view.SurfaceControl orCreateFixedRotationLeash = transaction != null ? asWindowToken.getOrCreateFixedRotationLeash(transaction) : asWindowToken.getFixedRotationLeash();
            if (orCreateFixedRotationLeash != null) {
                return orCreateFixedRotationLeash;
            }
        }
        return windowContainer.getSurfaceControl();
    }

    private static android.view.SurfaceControl getOrigParentSurface(com.android.server.wm.WindowContainer windowContainer) {
        if (windowContainer.asDisplayContent() != null) {
            return windowContainer.getSurfaceControl();
        }
        if (windowContainer.getParent().asDisplayContent() != null) {
            return windowContainer.getParent().asDisplayContent().getWindowingLayer();
        }
        return windowContainer.getParent().getSurfaceControl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isReadyGroup(com.android.server.wm.WindowContainer windowContainer) {
        return windowContainer instanceof com.android.server.wm.DisplayContent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getDisplayId(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
        if (windowContainer.getDisplayContent() != null) {
            return windowContainer.getDisplayContent().getDisplayId();
        }
        return -1;
    }

    @com.android.internal.annotations.VisibleForTesting
    static void calculateTransitionRoots(@android.annotation.NonNull android.window.TransitionInfo transitionInfo, java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList, @android.annotation.NonNull android.view.SurfaceControl.Transaction transaction) {
        com.android.server.wm.DisplayContent displayContent;
        for (int i = 0; i < arrayList.size(); i++) {
            com.android.server.wm.WindowContainer windowContainer = arrayList.get(i).mContainer;
            if (!isWallpaper(windowContainer) && (displayContent = windowContainer.getDisplayContent()) != null) {
                int displayId = displayContent.getDisplayId();
                if (transitionInfo.findRootIndex(displayId) < 0) {
                    com.android.server.wm.WindowContainer<?> findCommonAncestor = findCommonAncestor(arrayList, windowContainer);
                    if (!windowContainer.isDescendantOf(findCommonAncestor)) {
                        android.util.Slog.e(TAG, "Did not find common ancestor! Ancestor= " + findCommonAncestor + " target= " + windowContainer);
                    } else {
                        while (windowContainer.getParent() != findCommonAncestor) {
                            windowContainer = windowContainer.getParent();
                        }
                    }
                    android.view.SurfaceControl build = windowContainer.makeAnimationLeash().setName("Transition Root: " + windowContainer.getName()).build();
                    build.setUnreleasedWarningCallSite("Transition.calculateTransitionRoots");
                    updateDisplayLayers(displayContent, transaction);
                    transaction.setLayer(build, windowContainer.getLastLayer());
                    transitionInfo.addRootLeash(displayId, build, findCommonAncestor.getBounds().left, findCommonAncestor.getBounds().top);
                }
            }
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    static android.window.TransitionInfo calculateTransitionInfo(int i, int i2, java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList, @android.annotation.NonNull android.view.SurfaceControl.Transaction transaction) {
        android.window.WindowContainerToken windowContainerToken;
        com.android.server.wm.WindowContainer windowContainer;
        com.android.server.wm.TaskFragment organizedTaskFragment;
        com.android.server.wm.Task task;
        int backgroundColor;
        android.view.SurfaceControl.Transaction transaction2 = transaction;
        android.window.TransitionInfo transitionInfo = new android.window.TransitionInfo(i, i2);
        calculateTransitionRoots(transitionInfo, arrayList, transaction2);
        if (transitionInfo.getRootCount() == 0) {
            return transitionInfo;
        }
        int size = arrayList.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                break;
            }
            com.android.server.wm.Transition.ChangeInfo changeInfo = arrayList.get(i3);
            com.android.server.wm.WindowContainer windowContainer2 = changeInfo.mContainer;
            android.window.TransitionInfo.Change change = new android.window.TransitionInfo.Change(windowContainer2.mRemoteToken != null ? windowContainer2.mRemoteToken.toWindowContainerToken() : null, getLeashSurface(windowContainer2, transaction2));
            if (changeInfo.mEndParent != null) {
                change.setParent(changeInfo.mEndParent.mRemoteToken.toWindowContainerToken());
            }
            if (changeInfo.mStartParent != null && changeInfo.mStartParent.mRemoteToken != null && windowContainer2.getParent() != changeInfo.mStartParent) {
                change.setLastParent(changeInfo.mStartParent.mRemoteToken.toWindowContainerToken());
            }
            change.setMode(changeInfo.getTransitMode(windowContainer2));
            changeInfo.mReadyMode = change.getMode();
            change.setStartAbsBounds(changeInfo.mAbsoluteBounds);
            change.setFlags(changeInfo.getChangeFlags(windowContainer2));
            changeInfo.mReadyFlags = change.getFlags();
            change.setDisplayId(changeInfo.mDisplayId, getDisplayId(windowContainer2));
            com.android.server.wm.Task asTask = windowContainer2.asTask();
            com.android.server.wm.TaskFragment asTaskFragment = windowContainer2.asTaskFragment();
            com.android.server.wm.ActivityRecord asActivityRecord = windowContainer2.asActivityRecord();
            if (asTask != null) {
                android.app.ActivityManager.RunningTaskInfo runningTaskInfo = new android.app.ActivityManager.RunningTaskInfo();
                asTask.fillTaskInfo(runningTaskInfo);
                change.setTaskInfo(runningTaskInfo);
                change.setRotationAnimation(getTaskRotationAnimation(asTask));
                com.android.server.wm.ActivityRecord activityRecord = asTask.topRunningActivity();
                if (activityRecord != null) {
                    if (activityRecord.info.supportsPictureInPicture()) {
                        change.setAllowEnterPip(activityRecord.checkEnterPictureInPictureAppOpsState());
                    }
                    setEndFixedRotationIfNeeded(change, asTask, activityRecord);
                }
            } else if ((1 & changeInfo.mFlags) != 0) {
                change.setRotationAnimation(3);
            }
            com.android.server.wm.WindowContainer parent = windowContainer2.getParent();
            android.graphics.Rect bounds = windowContainer2.getBounds();
            android.graphics.Rect bounds2 = parent.getBounds();
            change.setEndRelOffset(bounds.left - bounds2.left, bounds.top - bounds2.top);
            int rotation = windowContainer2.getWindowConfiguration().getRotation();
            if (asActivityRecord != null) {
                change.setEndAbsBounds(bounds2);
                if (asActivityRecord.getRelativeDisplayRotation() != 0 && !asActivityRecord.mTransitionController.useShellTransitionsRotation()) {
                    rotation = parent.getWindowConfiguration().getRotation();
                }
            } else {
                change.setEndAbsBounds(bounds);
            }
            if (asActivityRecord != null || (asTaskFragment != null && asTaskFragment.isEmbedded())) {
                if (asActivityRecord != null) {
                    organizedTaskFragment = asActivityRecord.getOrganizedTaskFragment();
                } else {
                    organizedTaskFragment = asTaskFragment.getOrganizedTaskFragment();
                }
                if (organizedTaskFragment != null && organizedTaskFragment.getAnimationParams().getAnimationBackgroundColor() != 0) {
                    backgroundColor = organizedTaskFragment.getAnimationParams().getAnimationBackgroundColor();
                } else {
                    if (asActivityRecord != null) {
                        task = asActivityRecord.getTask();
                    } else {
                        task = asTaskFragment.getTask();
                    }
                    backgroundColor = task.getTaskDescription().getBackgroundColor();
                }
                change.setBackgroundColor(com.android.internal.graphics.ColorUtils.setAlphaComponent(backgroundColor, 255));
            }
            if (asActivityRecord != null) {
                change.setActivityComponent(asActivityRecord.mActivityComponent);
            }
            change.setRotation(changeInfo.mRotation, rotation);
            if (changeInfo.mSnapshot != null) {
                change.setSnapshot(changeInfo.mSnapshot, changeInfo.mSnapshotLuma);
            }
            transitionInfo.addChange(change);
            i3++;
            transaction2 = transaction;
        }
        int i4 = 0;
        while (true) {
            if (i4 >= arrayList.size()) {
                windowContainer = null;
                break;
            }
            if (isWallpaper(arrayList.get(i4).mContainer)) {
                i4++;
            } else {
                windowContainer = arrayList.get(i4).mContainer;
                break;
            }
        }
        if (windowContainer.asActivityRecord() != null) {
            com.android.server.wm.ActivityRecord asActivityRecord2 = windowContainer.asActivityRecord();
            windowContainerToken = addCustomActivityTransition(asActivityRecord2, false, addCustomActivityTransition(asActivityRecord2, true, null));
        }
        android.view.WindowManager.LayoutParams layoutParamsForAnimationsStyle = getLayoutParamsForAnimationsStyle(i, arrayList);
        if (layoutParamsForAnimationsStyle != null && layoutParamsForAnimationsStyle.type != 3 && layoutParamsForAnimationsStyle.windowAnimations != 0) {
            if (windowContainerToken != null) {
                windowContainerToken.addOptionsFromLayoutParameters(layoutParamsForAnimationsStyle);
            } else {
                windowContainerToken = android.window.TransitionInfo.AnimationOptions.makeAnimOptionsFromLayoutParameters(layoutParamsForAnimationsStyle);
            }
        }
        if (windowContainerToken != null) {
            transitionInfo.setAnimationOptions(windowContainerToken);
        }
        return transitionInfo;
    }

    static android.window.TransitionInfo.AnimationOptions addCustomActivityTransition(com.android.server.wm.ActivityRecord activityRecord, boolean z, android.window.TransitionInfo.AnimationOptions animationOptions) {
        com.android.server.wm.ActivityRecord.CustomAppTransition customAnimation = activityRecord.getCustomAnimation(z);
        if (customAnimation != null) {
            if (animationOptions == null) {
                animationOptions = android.window.TransitionInfo.AnimationOptions.makeCommonAnimOptions(activityRecord.packageName);
            }
            animationOptions.addCustomActivityTransition(z, customAnimation.mEnterAnim, customAnimation.mExitAnim, customAnimation.mBackgroundColor);
        }
        return animationOptions;
    }

    private static void setEndFixedRotationIfNeeded(@android.annotation.NonNull android.window.TransitionInfo.Change change, @android.annotation.NonNull com.android.server.wm.Task task, @android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.WindowContainer lastOrientationSource;
        int displayRotation;
        if (!activityRecord.isVisibleRequested()) {
            return;
        }
        if (task.inMultiWindowMode() && activityRecord.inMultiWindowMode()) {
            return;
        }
        int displayRotation2 = task.getWindowConfiguration().getDisplayRotation();
        int displayRotation3 = activityRecord.getWindowConfiguration().getDisplayRotation();
        if (displayRotation2 != displayRotation3) {
            change.setEndFixedRotation(displayRotation3);
        } else if (task.inPinnedWindowingMode() && !activityRecord.mDisplayContent.inTransition() && (lastOrientationSource = activityRecord.mDisplayContent.getLastOrientationSource()) != null && displayRotation2 != (displayRotation = lastOrientationSource.getWindowConfiguration().getDisplayRotation())) {
            change.setEndFixedRotation(displayRotation);
        }
    }

    @android.annotation.NonNull
    private static com.android.server.wm.WindowContainer<?> findCommonAncestor(@android.annotation.NonNull java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList, @android.annotation.NonNull com.android.server.wm.WindowContainer<?> windowContainer) {
        int transitMode;
        int displayId = getDisplayId(windowContainer);
        com.android.server.wm.WindowContainer<?> parent = windowContainer.getParent();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.wm.Transition.ChangeInfo changeInfo = arrayList.get(size);
            com.android.server.wm.WindowContainer windowContainer2 = changeInfo.mContainer;
            if (!isWallpaper(windowContainer2) && getDisplayId(windowContainer2) == displayId) {
                if (changeInfo.mStartParent != null && windowContainer2.getParent() != null && changeInfo.mStartParent.isAttached() && windowContainer2.getParent() != changeInfo.mStartParent && size == arrayList.size() - 1 && ((transitMode = changeInfo.getTransitMode(windowContainer2)) == 2 || transitMode == 4)) {
                    parent = changeInfo.mStartParent;
                } else {
                    while (!windowContainer2.isDescendantOf(parent)) {
                        parent = parent.getParent();
                    }
                    com.android.server.wm.WindowContainer<?> windowContainer3 = changeInfo.mCommonAncestor;
                    if (windowContainer3 != null && windowContainer3.isAttached()) {
                        while (windowContainer3 != parent && !windowContainer3.isDescendantOf(parent)) {
                            parent = parent.getParent();
                        }
                    }
                }
            }
        }
        return parent;
    }

    private static android.view.WindowManager.LayoutParams getLayoutParamsForAnimationsStyle(int i, java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.wm.WindowContainer windowContainer = arrayList.get(i2).mContainer;
            if (windowContainer.asActivityRecord() != null) {
                arraySet.add(java.lang.Integer.valueOf(windowContainer.getActivityType()));
            } else if (windowContainer.asWindowToken() == null && windowContainer.asWindowState() == null) {
                return null;
            }
        }
        if (arraySet.isEmpty()) {
            return null;
        }
        com.android.server.wm.ActivityRecord findAnimLayoutParamsActivityRecord = findAnimLayoutParamsActivityRecord(arrayList, i, arraySet);
        com.android.server.wm.WindowState findMainWindow = findAnimLayoutParamsActivityRecord != null ? findAnimLayoutParamsActivityRecord.findMainWindow() : null;
        if (findMainWindow != null) {
            return findMainWindow.mAttrs;
        }
        return null;
    }

    private static com.android.server.wm.ActivityRecord findAnimLayoutParamsActivityRecord(java.util.List<com.android.server.wm.Transition.ChangeInfo> list, final int i, final android.util.ArraySet<java.lang.Integer> arraySet) {
        com.android.server.wm.ActivityRecord lookForTopWindowWithFilter = lookForTopWindowWithFilter(list, new java.util.function.Predicate() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$findAnimLayoutParamsActivityRecord$7;
                lambda$findAnimLayoutParamsActivityRecord$7 = com.android.server.wm.Transition.lambda$findAnimLayoutParamsActivityRecord$7(i, arraySet, (com.android.server.wm.ActivityRecord) obj);
                return lambda$findAnimLayoutParamsActivityRecord$7;
            }
        });
        if (lookForTopWindowWithFilter != null) {
            return lookForTopWindowWithFilter;
        }
        com.android.server.wm.ActivityRecord lookForTopWindowWithFilter2 = lookForTopWindowWithFilter(list, new java.util.function.Predicate() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$findAnimLayoutParamsActivityRecord$8;
                lambda$findAnimLayoutParamsActivityRecord$8 = com.android.server.wm.Transition.lambda$findAnimLayoutParamsActivityRecord$8((com.android.server.wm.ActivityRecord) obj);
                return lambda$findAnimLayoutParamsActivityRecord$8;
            }
        });
        if (lookForTopWindowWithFilter2 != null) {
            return lookForTopWindowWithFilter2;
        }
        return lookForTopWindowWithFilter(list, new java.util.function.Predicate() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$findAnimLayoutParamsActivityRecord$9;
                lambda$findAnimLayoutParamsActivityRecord$9 = com.android.server.wm.Transition.lambda$findAnimLayoutParamsActivityRecord$9((com.android.server.wm.ActivityRecord) obj);
                return lambda$findAnimLayoutParamsActivityRecord$9;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$findAnimLayoutParamsActivityRecord$7(int i, android.util.ArraySet arraySet, com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.getRemoteAnimationDefinition() != null && activityRecord.getRemoteAnimationDefinition().hasTransition(i, arraySet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$findAnimLayoutParamsActivityRecord$8(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.fillsParent() && activityRecord.findMainWindow() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$findAnimLayoutParamsActivityRecord$9(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.findMainWindow() != null;
    }

    private static com.android.server.wm.ActivityRecord lookForTopWindowWithFilter(java.util.List<com.android.server.wm.Transition.ChangeInfo> list, java.util.function.Predicate<com.android.server.wm.ActivityRecord> predicate) {
        com.android.server.wm.ActivityRecord asActivityRecord;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            com.android.server.wm.WindowContainer windowContainer = list.get(i).mContainer;
            if (windowContainer.asTaskFragment() != null) {
                asActivityRecord = windowContainer.asTaskFragment().getTopNonFinishingActivity();
            } else {
                asActivityRecord = windowContainer.asActivityRecord();
            }
            if (asActivityRecord != null && predicate.test(asActivityRecord)) {
                return asActivityRecord;
            }
        }
        return null;
    }

    private static int getTaskRotationAnimation(@android.annotation.NonNull com.android.server.wm.Task task) {
        com.android.server.wm.WindowState findMainWindow;
        com.android.server.wm.ActivityRecord topVisibleActivity = task.getTopVisibleActivity();
        if (topVisibleActivity == null || (findMainWindow = topVisibleActivity.findMainWindow(false)) == null) {
            return -1;
        }
        int rotationAnimationHint = findMainWindow.getRotationAnimationHint();
        if (rotationAnimationHint >= 0) {
            return rotationAnimationHint;
        }
        int i = findMainWindow.getAttrs().rotationAnimation;
        if (i != 3) {
            return i;
        }
        if (findMainWindow != task.mDisplayContent.getDisplayPolicy().getTopFullscreenOpaqueWindow() || !topVisibleActivity.matchParentBounds()) {
            return -1;
        }
        return findMainWindow.getAttrs().rotationAnimation;
    }

    private void validateKeyguardOcclusion() {
        if ((this.mFlags & 14592) != 0) {
            java.util.ArrayList<java.lang.Runnable> arrayList = this.mController.mStateValidators;
            com.android.server.policy.WindowManagerPolicy windowManagerPolicy = this.mController.mAtm.mWindowManager.mPolicy;
            java.util.Objects.requireNonNull(windowManagerPolicy);
            arrayList.add(new com.android.server.wm.KeyguardController$$ExternalSyntheticLambda0(windowManagerPolicy));
        }
    }

    boolean shouldUsePerfHint(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        if (this.mOverrideOptions != null && this.mOverrideOptions.getType() == 5 && this.mType == 4 && this.mParticipants.size() == 1) {
            return false;
        }
        return this.mTargetDisplays.contains(displayContent);
    }

    boolean shouldApplyOnDisplayThread() {
        com.android.server.wm.Transition.ChangeInfo changeInfo;
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            com.android.server.wm.DisplayContent asDisplayContent = this.mParticipants.valueAt(size).asDisplayContent();
            if (asDisplayContent != null && (changeInfo = this.mChanges.get(asDisplayContent)) != null && changeInfo.mRotation != asDisplayContent.getRotation()) {
                return android.os.Looper.myLooper() != this.mController.mAtm.mWindowManager.mH.getLooper();
            }
        }
        return false;
    }

    @android.annotation.Nullable
    void applyDisplayChangeIfNeeded(@android.annotation.NonNull final android.util.ArraySet<com.android.server.wm.WindowContainer<?>> arraySet) {
        for (int size = this.mParticipants.size() - 1; size >= 0; size--) {
            com.android.server.wm.DisplayContent asDisplayContent = this.mParticipants.valueAt(size).asDisplayContent();
            if (asDisplayContent != null && this.mChanges.get(asDisplayContent).hasChanged()) {
                boolean sendNewConfiguration = asDisplayContent.sendNewConfiguration();
                if (!this.mReadyTrackerOld.mUsed) {
                    setReady(asDisplayContent, true);
                }
                if (sendNewConfiguration && this.mController.mAtm.mTaskSupervisor.isRootVisibilityUpdateDeferred()) {
                    asDisplayContent.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.Transition$$ExternalSyntheticLambda7
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.Transition.lambda$applyDisplayChangeIfNeeded$10(arraySet, (com.android.server.wm.ActivityRecord) obj);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$applyDisplayChangeIfNeeded$10(android.util.ArraySet arraySet, com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.isVisibleRequested()) {
            arraySet.add(activityRecord);
        }
    }

    boolean getLegacyIsReady() {
        return isCollecting() && this.mSyncId >= 0;
    }

    static void asyncTraceBegin(@android.annotation.NonNull java.lang.String str, int i) {
        android.os.Trace.asyncTraceForTrackBegin(32L, TAG, str, i);
    }

    static void asyncTraceEnd(int i) {
        android.os.Trace.asyncTraceForTrackEnd(32L, TAG, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class ChangeInfo {
        private static final int FLAG_ABOVE_TRANSIENT_LAUNCH = 4;
        private static final int FLAG_CHANGE_CONFIG_AT_END = 64;
        private static final int FLAG_CHANGE_MOVED_TO_TOP = 32;
        private static final int FLAG_CHANGE_NO_ANIMATION = 8;
        private static final int FLAG_CHANGE_YES_ANIMATION = 16;
        private static final int FLAG_NONE = 0;
        private static final int FLAG_SEAMLESS_ROTATION = 1;
        private static final int FLAG_TRANSIENT_LAUNCH = 2;
        final android.graphics.Rect mAbsoluteBounds;
        com.android.server.wm.WindowContainer mCommonAncestor;

        @android.annotation.NonNull
        final com.android.server.wm.WindowContainer mContainer;
        int mDisplayId;
        com.android.server.wm.WindowContainer mEndParent;
        boolean mExistenceChanged;
        int mFlags;
        int mKnownConfigChanges;

        @android.window.TransitionInfo.ChangeFlags
        int mReadyFlags;

        @android.window.TransitionInfo.TransitionMode
        int mReadyMode;
        int mRotation;
        boolean mShowWallpaper;
        android.view.SurfaceControl mSnapshot;
        float mSnapshotLuma;
        com.android.server.wm.WindowContainer mStartParent;
        boolean mVisible;
        int mWindowingMode;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface Flag {
        }

        ChangeInfo(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
            this.mExistenceChanged = false;
            this.mAbsoluteBounds = new android.graphics.Rect();
            this.mRotation = -1;
            this.mDisplayId = -1;
            this.mFlags = 0;
            this.mContainer = windowContainer;
            this.mVisible = windowContainer.isVisibleRequested();
            this.mWindowingMode = windowContainer.getWindowingMode();
            this.mAbsoluteBounds.set(windowContainer.getBounds());
            this.mShowWallpaper = windowContainer.showWallpaper();
            this.mRotation = windowContainer.getWindowConfiguration().getRotation();
            this.mStartParent = windowContainer.getParent();
            this.mDisplayId = com.android.server.wm.Transition.getDisplayId(windowContainer);
        }

        @com.android.internal.annotations.VisibleForTesting
        ChangeInfo(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, boolean z, boolean z2) {
            this(windowContainer);
            this.mVisible = z;
            this.mExistenceChanged = z2;
            this.mShowWallpaper = false;
        }

        public java.lang.String toString() {
            return this.mContainer.toString();
        }

        boolean hasChanged() {
            if ((this.mFlags & 2) != 0 || (this.mFlags & 4) != 0) {
                return true;
            }
            boolean isVisibleRequested = this.mContainer.isVisibleRequested();
            if (isVisibleRequested == this.mVisible && !this.mVisible) {
                return false;
            }
            if (isVisibleRequested == this.mVisible && this.mKnownConfigChanges == 0) {
                return ((this.mWindowingMode == 0 || this.mContainer.getWindowingMode() == this.mWindowingMode) && this.mContainer.getBounds().equals(this.mAbsoluteBounds) && this.mRotation == this.mContainer.getWindowConfiguration().getRotation() && this.mDisplayId == com.android.server.wm.Transition.getDisplayId(this.mContainer) && (this.mFlags & 32) == 0) ? false : true;
            }
            return true;
        }

        @android.window.TransitionInfo.TransitionMode
        int getTransitMode(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
            if ((this.mFlags & 4) != 0) {
                return this.mExistenceChanged ? 2 : 4;
            }
            boolean isVisibleRequested = windowContainer.isVisibleRequested();
            if (isVisibleRequested == this.mVisible) {
                return 6;
            }
            return this.mExistenceChanged ? isVisibleRequested ? 1 : 2 : isVisibleRequested ? 3 : 4;
        }

        @android.window.TransitionInfo.ChangeFlags
        int getChangeFlags(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer) {
            com.android.server.wm.Task task;
            int i = (this.mShowWallpaper || windowContainer.showWallpaper()) ? 1 : 0;
            if (com.android.server.wm.Transition.isTranslucent(windowContainer)) {
                i |= 4;
            }
            if (windowContainer.mWmService.mAtmService.mBackNavigationController.isMonitorTransitionTarget(windowContainer)) {
                i |= 131072;
            }
            com.android.server.wm.Task asTask = windowContainer.asTask();
            if (asTask != null) {
                com.android.server.wm.ActivityRecord topNonFinishingActivity = asTask.getTopNonFinishingActivity();
                if (topNonFinishingActivity != null) {
                    if (topNonFinishingActivity.mStartingData != null && topNonFinishingActivity.mStartingData.hasImeSurface()) {
                        i |= 2048;
                    }
                    if (topNonFinishingActivity.mLaunchTaskBehind) {
                        android.util.Slog.e(com.android.server.wm.Transition.TAG, "Unexpected launch-task-behind operation in shell transition");
                        i |= 524288;
                    }
                    if ((topNonFinishingActivity.mTransitionChangeFlags & 294912) == 294912) {
                        i |= 294912;
                    }
                }
                if (asTask.voiceSession != null) {
                    i |= 16;
                }
            }
            com.android.server.wm.ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
            if (asActivityRecord == null) {
                task = null;
            } else {
                task = asActivityRecord.getTask();
                if (asActivityRecord.mVoiceInteraction) {
                    i |= 16;
                }
                i |= asActivityRecord.mTransitionChangeFlags;
                if (asActivityRecord.isConfigurationDispatchPaused()) {
                    i |= 4194304;
                }
            }
            com.android.server.wm.TaskFragment asTaskFragment = windowContainer.asTaskFragment();
            if (asTaskFragment != null && asTask == null) {
                task = asTaskFragment.getTask();
            }
            if (task != null) {
                if (task.forAllLeafTaskFragments(new com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda5())) {
                    i |= 512;
                }
                if (task.forAllActivities(new com.android.server.wm.Transition$ChangeInfo$$ExternalSyntheticLambda0())) {
                    i |= 16384;
                }
                if (isWindowFillingTask(windowContainer, task)) {
                    i |= 1024;
                }
            } else {
                com.android.server.wm.DisplayContent asDisplayContent = windowContainer.asDisplayContent();
                if (asDisplayContent != null) {
                    i |= 32;
                    if (asDisplayContent.hasAlertWindowSurfaces()) {
                        i |= 128;
                    }
                } else if (com.android.server.wm.Transition.isWallpaper(windowContainer)) {
                    i |= 2;
                } else if (com.android.server.wm.Transition.isInputMethod(windowContainer)) {
                    i |= 256;
                } else {
                    int windowType = windowContainer.getWindowType();
                    if (windowType >= 2000 && windowType <= 2999) {
                        i |= 65536;
                    }
                }
            }
            if ((this.mFlags & 8) != 0 && (this.mFlags & 16) == 0) {
                i |= 262144;
            }
            if ((this.mFlags & 32) != 0) {
                i |= 1048576;
            }
            if ((this.mFlags & 64) != 0) {
                return i | 4194304;
            }
            return i;
        }

        private boolean isWindowFillingTask(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull com.android.server.wm.Task task) {
            android.graphics.Rect bounds = task.getBounds();
            int width = bounds.width();
            int height = bounds.height();
            android.graphics.Rect rect = this.mAbsoluteBounds;
            android.graphics.Rect bounds2 = windowContainer.getBounds();
            return (!this.mVisible || (width == rect.width() && height == rect.height())) && (!windowContainer.isVisibleRequested() || (width == bounds2.width() && height == bounds2.height()));
        }
    }

    void deferTransitionReady() {
        this.mReadyTrackerOld.mDeferReadyDepth++;
        this.mSyncEngine.setReady(this.mSyncId, false);
    }

    void continueTransitionReady() {
        com.android.server.wm.Transition.ReadyTrackerOld readyTrackerOld = this.mReadyTrackerOld;
        readyTrackerOld.mDeferReadyDepth--;
        applyReady();
    }

    static class ReadyCondition {
        java.lang.String mAlternate;
        final java.lang.Object mDebugTarget;
        boolean mMet;
        final java.lang.String mName;
        com.android.server.wm.Transition.ReadyTracker mTracker;

        ReadyCondition(@android.annotation.NonNull java.lang.String str) {
            this.mMet = false;
            this.mAlternate = null;
            this.mName = str;
            this.mDebugTarget = null;
        }

        ReadyCondition(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.Object obj) {
            this.mMet = false;
            this.mAlternate = null;
            this.mName = str;
            this.mDebugTarget = obj;
        }

        protected java.lang.String getDebugRep() {
            if (this.mDebugTarget != null) {
                return this.mName + ":" + this.mDebugTarget;
            }
            return this.mName;
        }

        public java.lang.String toString() {
            java.lang.String str;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("{");
            sb.append(getDebugRep());
            if (this.mAlternate != null) {
                str = " (" + this.mAlternate + ")";
            } else {
                str = "";
            }
            sb.append(str);
            sb.append("}");
            return sb.toString();
        }

        void startTracking() {
        }

        void meetAlternate(@android.annotation.NonNull java.lang.String str) {
            if (this.mMet) {
                return;
            }
            this.mAlternate = str;
            meet();
        }

        void meet() {
            if (this.mMet) {
                return;
            }
            if (this.mTracker == null) {
                throw new java.lang.IllegalStateException("Can't meet a condition before it is waited on");
            }
            this.mTracker.meet(this);
        }
    }

    static class ReadyTracker {
        static final com.android.server.wm.Transition.ReadyTracker NULL_TRACKER = new com.android.server.wm.Transition.ReadyTracker(null);
        final java.util.ArrayList<com.android.server.wm.Transition.ReadyCondition> mConditions = new java.util.ArrayList<>();
        final java.util.ArrayList<com.android.server.wm.Transition.ReadyCondition> mMet = new java.util.ArrayList<>();
        private final com.android.server.wm.Transition mTransition;

        ReadyTracker(com.android.server.wm.Transition transition) {
            this.mTransition = transition;
        }

        void add(@android.annotation.NonNull com.android.server.wm.Transition.ReadyCondition readyCondition) {
            if (this.mTransition == null || !this.mTransition.mController.useFullReadyTracking()) {
                readyCondition.mTracker = NULL_TRACKER;
                return;
            }
            this.mConditions.add(readyCondition);
            readyCondition.mTracker = this;
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -2971560715211489406L, 4, null, java.lang.String.valueOf(readyCondition), java.lang.Long.valueOf(this.mTransition.mSyncId));
            readyCondition.startTracking();
        }

        void meet(@android.annotation.NonNull com.android.server.wm.Transition.ReadyCondition readyCondition) {
            if (this.mTransition == null || !this.mTransition.mController.useFullReadyTracking()) {
                return;
            }
            if (this.mTransition.mState >= 2) {
                android.util.Slog.w(com.android.server.wm.Transition.TAG, "#%d: Condition met too late, already in state=" + this.mTransition.mState + ": " + readyCondition);
                return;
            }
            if (!this.mConditions.remove(readyCondition)) {
                if (this.mMet.contains(readyCondition)) {
                    throw new java.lang.IllegalStateException("Can't meet the same condition more than once: " + readyCondition + " #" + this.mTransition.mSyncId);
                }
                throw new java.lang.IllegalArgumentException("Can't meet a condition that isn't being waited on: " + readyCondition + " in #" + this.mTransition.mSyncId);
            }
            readyCondition.mMet = true;
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 7631061720069910622L, 20, null, java.lang.String.valueOf(readyCondition), java.lang.Long.valueOf(this.mTransition.mSyncId), java.lang.Long.valueOf(this.mConditions.size()));
            this.mMet.add(readyCondition);
            this.mTransition.applyReady();
        }

        boolean isReady() {
            return this.mConditions.isEmpty() && !this.mMet.isEmpty();
        }
    }

    private static class ReadyTrackerOld {
        private int mDeferReadyDepth;
        private final android.util.ArrayMap<com.android.server.wm.WindowContainer, java.lang.Boolean> mReadyGroups;
        private boolean mReadyOverride;
        private boolean mUsed;

        private ReadyTrackerOld() {
            this.mReadyGroups = new android.util.ArrayMap<>();
            this.mUsed = false;
            this.mReadyOverride = false;
            this.mDeferReadyDepth = 0;
        }

        void addGroup(com.android.server.wm.WindowContainer windowContainer) {
            if (this.mReadyGroups.containsKey(windowContainer)) {
                return;
            }
            this.mReadyGroups.put(windowContainer, false);
        }

        void setReadyFrom(com.android.server.wm.WindowContainer windowContainer, boolean z) {
            this.mUsed = true;
            for (com.android.server.wm.WindowContainer windowContainer2 = windowContainer; windowContainer2 != null; windowContainer2 = windowContainer2.getParent()) {
                if (com.android.server.wm.Transition.isReadyGroup(windowContainer2)) {
                    this.mReadyGroups.put(windowContainer2, java.lang.Boolean.valueOf(z));
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -4770394322045550928L, 3, null, java.lang.Boolean.valueOf(z), java.lang.String.valueOf(windowContainer2), java.lang.String.valueOf(windowContainer));
                    return;
                }
            }
        }

        void setAllReady() {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 6039132370452820927L, 0, null, null);
            this.mUsed = true;
            this.mReadyOverride = true;
        }

        boolean allReady() {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, -3263748870548668913L, 31, null, java.lang.Boolean.valueOf(this.mUsed), java.lang.Boolean.valueOf(this.mReadyOverride), java.lang.Long.valueOf(this.mDeferReadyDepth), java.lang.String.valueOf(groupsToString()));
            if (!this.mUsed || this.mDeferReadyDepth > 0) {
                return false;
            }
            if (this.mReadyOverride) {
                return true;
            }
            for (int size = this.mReadyGroups.size() - 1; size >= 0; size--) {
                com.android.server.wm.WindowContainer keyAt = this.mReadyGroups.keyAt(size);
                if (keyAt.isAttached() && keyAt.isVisibleRequested() && !this.mReadyGroups.valueAt(size).booleanValue()) {
                    return false;
                }
            }
            return true;
        }

        private java.lang.String groupsToString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (int i = 0; i < this.mReadyGroups.size(); i++) {
                if (i != 0) {
                    sb.append(',');
                }
                sb.append(this.mReadyGroups.keyAt(i));
                sb.append(':');
                sb.append(this.mReadyGroups.valueAt(i));
            }
            return sb.toString();
        }
    }

    private static class Targets {
        final android.util.SparseArray<com.android.server.wm.Transition.ChangeInfo> mArray;
        private int mDepthFactor;
        private java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> mRemovedTargets;

        private Targets() {
            this.mArray = new android.util.SparseArray<>();
        }

        void add(com.android.server.wm.Transition.ChangeInfo changeInfo) {
            if (this.mDepthFactor == 0) {
                this.mDepthFactor = changeInfo.mContainer.mWmService.mRoot.getTreeWeight() + 1;
            }
            int prefixOrderIndex = changeInfo.mContainer.getPrefixOrderIndex();
            com.android.server.wm.WindowContainer windowContainer = changeInfo.mContainer;
            while (windowContainer != null) {
                windowContainer = windowContainer.getParent();
                if (windowContainer != null) {
                    prefixOrderIndex += this.mDepthFactor;
                }
            }
            this.mArray.put(prefixOrderIndex, changeInfo);
        }

        void remove(int i) {
            com.android.server.wm.Transition.ChangeInfo valueAt = this.mArray.valueAt(i);
            this.mArray.removeAt(i);
            if (this.mRemovedTargets == null) {
                this.mRemovedTargets = new java.util.ArrayList<>();
            }
            this.mRemovedTargets.add(valueAt);
        }

        boolean wasParticipated(com.android.server.wm.Transition.ChangeInfo changeInfo) {
            return this.mArray.indexOfValue(changeInfo) >= 0 || (this.mRemovedTargets != null && this.mRemovedTargets.contains(changeInfo));
        }

        java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> getListSortedByZ() {
            android.util.SparseArray sparseArray = new android.util.SparseArray(this.mArray.size());
            for (int size = this.mArray.size() - 1; size >= 0; size--) {
                sparseArray.put(this.mArray.keyAt(size) % this.mDepthFactor, this.mArray.valueAt(size));
            }
            java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList = new java.util.ArrayList<>(sparseArray.size());
            for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
                arrayList.add((com.android.server.wm.Transition.ChangeInfo) sparseArray.valueAt(size2));
            }
            return arrayList;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    private class ScreenshotFreezer implements com.android.server.wm.Transition.IContainerFreezer {
        private final android.util.ArraySet<com.android.server.wm.WindowContainer> mFrozen;

        private ScreenshotFreezer() {
            this.mFrozen = new android.util.ArraySet<>();
        }

        @Override // com.android.server.wm.Transition.IContainerFreezer
        public boolean freeze(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, @android.annotation.NonNull android.graphics.Rect rect) {
            if (!windowContainer.isVisibleRequested()) {
                return false;
            }
            for (com.android.server.wm.WindowContainer windowContainer2 = windowContainer; windowContainer2 != null; windowContainer2 = windowContainer2.getParent()) {
                if (this.mFrozen.contains(windowContainer2)) {
                    return false;
                }
            }
            if (com.android.server.wm.Transition.this.mIsSeamlessRotation) {
                com.android.server.wm.WindowState topFullscreenOpaqueWindow = windowContainer.getDisplayContent() == null ? null : windowContainer.getDisplayContent().getDisplayPolicy().getTopFullscreenOpaqueWindow();
                if (topFullscreenOpaqueWindow != null && (topFullscreenOpaqueWindow == windowContainer || topFullscreenOpaqueWindow.isDescendantOf(windowContainer))) {
                    this.mFrozen.add(windowContainer);
                    return true;
                }
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_TRANSITIONS, 2699903406935781477L, 0, null, java.lang.String.valueOf(windowContainer.toString()), java.lang.String.valueOf(rect.toString()));
            android.graphics.Rect rect2 = new android.graphics.Rect(rect);
            rect2.offsetTo(0, 0);
            boolean z = windowContainer.asDisplayContent() != null && windowContainer.asDisplayContent().isRotationChanging();
            android.window.ScreenCapture.ScreenshotHardwareBuffer captureLayers = android.window.ScreenCapture.captureLayers(new android.window.ScreenCapture.LayerCaptureArgs.Builder(windowContainer.getSurfaceControl()).setSourceCrop(rect2).setCaptureSecureLayers(true).setAllowProtected(true).setHintForSeamlessTransition(z).build());
            android.hardware.HardwareBuffer hardwareBuffer = captureLayers == null ? null : captureLayers.getHardwareBuffer();
            if (hardwareBuffer == null || hardwareBuffer.getWidth() <= 1 || hardwareBuffer.getHeight() <= 1) {
                android.util.Slog.w(com.android.server.wm.Transition.TAG, "Failed to capture screenshot for " + windowContainer);
                return false;
            }
            android.view.SurfaceControl build = windowContainer.makeAnimationLeash().setName(z ? "RotationLayer" : "transition snapshot: " + windowContainer).setOpaque(windowContainer.fillsParent()).setParent(windowContainer.getSurfaceControl()).setSecure(captureLayers.containsSecureLayers()).setCallsite("Transition.ScreenshotSync").setBLASTLayer().build();
            this.mFrozen.add(windowContainer);
            com.android.server.wm.Transition.ChangeInfo changeInfo = com.android.server.wm.Transition.this.mChanges.get(windowContainer);
            java.util.Objects.requireNonNull(changeInfo);
            changeInfo.mSnapshot = build;
            if (changeInfo.mRotation != windowContainer.mDisplayContent.getRotation()) {
                changeInfo.mSnapshotLuma = com.android.internal.policy.TransitionAnimation.getBorderLuma(hardwareBuffer, captureLayers.getColorSpace());
            }
            android.view.SurfaceControl.Transaction transaction = windowContainer.mWmService.mTransactionFactory.get();
            com.android.internal.policy.TransitionAnimation.configureScreenshotLayer(transaction, build, captureLayers);
            transaction.show(build);
            transaction.setLayer(build, Integer.MAX_VALUE);
            transaction.apply();
            transaction.close();
            hardwareBuffer.close();
            windowContainer.getSyncTransaction().reparent(build, null);
            return true;
        }

        @Override // com.android.server.wm.Transition.IContainerFreezer
        public void cleanUp(android.view.SurfaceControl.Transaction transaction) {
            for (int i = 0; i < this.mFrozen.size(); i++) {
                com.android.server.wm.Transition.ChangeInfo changeInfo = com.android.server.wm.Transition.this.mChanges.get(this.mFrozen.valueAt(i));
                java.util.Objects.requireNonNull(changeInfo);
                android.view.SurfaceControl surfaceControl = changeInfo.mSnapshot;
                if (surfaceControl != null) {
                    transaction.reparent(surfaceControl, null);
                }
            }
        }
    }

    private static class Token extends android.os.Binder {
        final java.lang.ref.WeakReference<com.android.server.wm.Transition> mTransition;

        Token(com.android.server.wm.Transition transition) {
            this.mTransition = new java.lang.ref.WeakReference<>(transition);
        }

        public java.lang.String toString() {
            return "Token{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.mTransition.get() + "}";
        }
    }
}
