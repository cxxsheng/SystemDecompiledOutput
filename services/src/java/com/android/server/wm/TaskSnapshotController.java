package com.android.server.wm;

/* loaded from: classes3.dex */
class TaskSnapshotController extends com.android.server.wm.AbsAppSnapshotController<com.android.server.wm.Task, com.android.server.wm.TaskSnapshotCache> {
    static final java.lang.String SNAPSHOTS_DIRNAME = "snapshots";
    private final android.os.Handler mHandler;
    private final com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider mPersistInfoProvider;
    private final com.android.server.wm.TaskSnapshotPersister mPersister;
    private final android.util.IntArray mSkipClosingAppSnapshotTasks;
    private final android.util.ArraySet<com.android.server.wm.Task> mTmpTasks;

    TaskSnapshotController(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.SnapshotPersistQueue snapshotPersistQueue) {
        super(windowManagerService);
        this.mSkipClosingAppSnapshotTasks = new android.util.IntArray();
        this.mTmpTasks = new android.util.ArraySet<>();
        this.mHandler = new android.os.Handler();
        this.mPersistInfoProvider = createPersistInfoProvider(windowManagerService, new com.android.server.wm.ActivitySnapshotController$$ExternalSyntheticLambda1());
        this.mPersister = new com.android.server.wm.TaskSnapshotPersister(snapshotPersistQueue, this.mPersistInfoProvider);
        initialize(new com.android.server.wm.TaskSnapshotCache(new com.android.server.wm.AppSnapshotLoader(this.mPersistInfoProvider)));
        setSnapshotEnabled(!windowManagerService.mContext.getResources().getBoolean(android.R.bool.config_disableMenuKeyInLockScreen));
    }

    static com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider createPersistInfoProvider(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.BaseAppSnapshotPersister.DirectoryResolver directoryResolver) {
        boolean z;
        float f;
        float f2 = windowManagerService.mContext.getResources().getFloat(android.R.dimen.config_dialogCornerRadius);
        float f3 = windowManagerService.mContext.getResources().getFloat(android.R.dimen.config_inCallNotificationVolume);
        if (f3 < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || 1.0f <= f3) {
            throw new java.lang.RuntimeException("Low-res scale must be between 0 and 1");
        }
        if (f2 <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || 1.0f < f2) {
            throw new java.lang.RuntimeException("High-res scale must be between 0 and 1");
        }
        if (f2 <= f3) {
            throw new java.lang.RuntimeException("High-res scale must be greater than low-res scale");
        }
        if (f3 > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            z = true;
            f = f3 / f2;
        } else {
            z = false;
            f = 0.0f;
        }
        return new com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider(directoryResolver, SNAPSHOTS_DIRNAME, z, f, windowManagerService.mContext.getResources().getBoolean(android.R.bool.config_unfoldTransitionHingeAngle));
    }

    void handleClosingApps(android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet) {
        com.android.server.wm.Task task;
        if (shouldDisableSnapshots()) {
            return;
        }
        this.mTmpTasks.clear();
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityRecord valueAt = arraySet.valueAt(size);
            if (!valueAt.isActivityTypeHome() && (task = valueAt.getTask()) != null) {
                getClosingTasksInner(task, this.mTmpTasks);
            }
        }
        snapshotTasks(this.mTmpTasks);
        this.mTmpTasks.clear();
        this.mSkipClosingAppSnapshotTasks.clear();
    }

    @com.android.internal.annotations.VisibleForTesting
    void addSkipClosingAppSnapshotTasks(java.util.Set<com.android.server.wm.Task> set) {
        if (shouldDisableSnapshots()) {
            return;
        }
        java.util.Iterator<com.android.server.wm.Task> it = set.iterator();
        while (it.hasNext()) {
            this.mSkipClosingAppSnapshotTasks.add(it.next().mTaskId);
        }
    }

    void snapshotTasks(android.util.ArraySet<com.android.server.wm.Task> arraySet) {
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            recordSnapshot(arraySet.valueAt(size));
        }
    }

    void recordSnapshot(com.android.server.wm.Task task, com.android.server.wm.Transition.ChangeInfo changeInfo) {
        this.mCurrentChangeInfo = changeInfo;
        try {
            recordSnapshot(task);
        } finally {
            this.mCurrentChangeInfo = null;
        }
    }

    android.window.TaskSnapshot recordSnapshot(com.android.server.wm.Task task) {
        android.window.TaskSnapshot recordSnapshotInner = recordSnapshotInner(task);
        if (recordSnapshotInner != null && !task.isActivityTypeHome()) {
            this.mPersister.persistSnapshot(task.mTaskId, task.mUserId, recordSnapshotInner);
            task.onSnapshotChanged(recordSnapshotInner);
        }
        return recordSnapshotInner;
    }

    @android.annotation.Nullable
    android.window.TaskSnapshot getSnapshot(int i, int i2, boolean z, boolean z2) {
        return ((com.android.server.wm.TaskSnapshotCache) this.mCache).getSnapshot(i, i2, z, z2 && this.mPersistInfoProvider.enableLowResSnapshots());
    }

    long getSnapshotCaptureTime(int i) {
        android.window.TaskSnapshot snapshot = ((com.android.server.wm.TaskSnapshotCache) this.mCache).getSnapshot(java.lang.Integer.valueOf(i));
        if (snapshot != null) {
            return snapshot.getCaptureTime();
        }
        return -1L;
    }

    public void clearSnapshotCache() {
        ((com.android.server.wm.TaskSnapshotCache) this.mCache).clearRunningCache();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.wm.AbsAppSnapshotController
    @android.annotation.Nullable
    public com.android.server.wm.ActivityRecord findAppTokenForSnapshot(com.android.server.wm.Task task) {
        return task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.TaskSnapshotController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return ((com.android.server.wm.ActivityRecord) obj).canCaptureSnapshot();
            }
        });
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    protected boolean use16BitFormat() {
        return this.mPersistInfoProvider.use16BitFormat();
    }

    @android.annotation.Nullable
    private android.window.ScreenCapture.ScreenshotHardwareBuffer createImeSnapshot(@android.annotation.NonNull com.android.server.wm.Task task, int i) {
        com.android.server.wm.WindowState windowState;
        if (task.getSurfaceControl() == null || (windowState = task.getDisplayContent().mInputMethodWindow) == null || !windowState.isVisible()) {
            return null;
        }
        android.graphics.Rect parentFrame = windowState.getParentFrame();
        parentFrame.offsetTo(0, 0);
        return android.window.ScreenCapture.captureLayersExcluding(windowState.getSurfaceControl(), parentFrame, 1.0f, i, (android.view.SurfaceControl[]) null);
    }

    @android.annotation.Nullable
    android.window.ScreenCapture.ScreenshotHardwareBuffer snapshotImeFromAttachedTask(@android.annotation.NonNull com.android.server.wm.Task task) {
        int i;
        if (checkIfReadyToSnapshot(task) == null) {
            return null;
        }
        if (this.mPersistInfoProvider.use16BitFormat()) {
            i = 4;
        } else {
            i = 1;
        }
        return createImeSnapshot(task, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.AbsAppSnapshotController
    public com.android.server.wm.ActivityRecord getTopActivity(com.android.server.wm.Task task) {
        return task.getTopMostActivity();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.AbsAppSnapshotController
    public com.android.server.wm.ActivityRecord getTopFullscreenActivity(com.android.server.wm.Task task) {
        return task.getTopFullscreenActivity();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.AbsAppSnapshotController
    public android.app.ActivityManager.TaskDescription getTaskDescription(com.android.server.wm.Task task) {
        return task.getTaskDescription();
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    protected android.graphics.Rect getLetterboxInsets(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.getLetterboxInsets();
    }

    void getClosingTasksInner(com.android.server.wm.Task task, android.util.ArraySet<com.android.server.wm.Task> arraySet) {
        if (isAnimatingByRecents(task)) {
            this.mSkipClosingAppSnapshotTasks.add(task.mTaskId);
        }
        if (!task.isVisible() && this.mSkipClosingAppSnapshotTasks.indexOf(task.mTaskId) < 0) {
            arraySet.add(task);
        }
    }

    void removeAndDeleteSnapshot(int i, int i2) {
        ((com.android.server.wm.TaskSnapshotCache) this.mCache).onIdRemoved(java.lang.Integer.valueOf(i));
        this.mPersister.removeSnapshot(i, i2);
    }

    void removeSnapshotCache(int i) {
        ((com.android.server.wm.TaskSnapshotCache) this.mCache).removeRunningEntry(java.lang.Integer.valueOf(i));
    }

    void removeObsoleteTaskFiles(android.util.ArraySet<java.lang.Integer> arraySet, int[] iArr) {
        this.mPersister.removeObsoleteFiles(arraySet, iArr);
    }

    void screenTurningOff(final int i, final com.android.server.policy.WindowManagerPolicy.ScreenOffListener screenOffListener) {
        if (shouldDisableSnapshots()) {
            screenOffListener.onScreenOff();
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.TaskSnapshotController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.TaskSnapshotController.this.lambda$screenTurningOff$0(i, screenOffListener);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$screenTurningOff$0(int i, com.android.server.policy.WindowManagerPolicy.ScreenOffListener screenOffListener) {
        try {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    snapshotForSleeping(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        } finally {
            screenOffListener.onScreenOff();
        }
    }

    void snapshotForSleeping(int i) {
        com.android.server.wm.DisplayContent displayContent;
        if (shouldDisableSnapshots() || !this.mService.mDisplayEnabled || (displayContent = this.mService.mRoot.getDisplayContent(i)) == null) {
            return;
        }
        final boolean z = i == 0 && this.mService.mPolicy.isKeyguardSecure(this.mService.mCurrentUserId);
        this.mTmpTasks.clear();
        displayContent.forAllLeafTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.TaskSnapshotController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.TaskSnapshotController.this.lambda$snapshotForSleeping$1(z, (com.android.server.wm.Task) obj);
            }
        }, true);
        snapshotTasks(this.mTmpTasks);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$snapshotForSleeping$1(boolean z, com.android.server.wm.Task task) {
        if ((z || !task.isActivityTypeHome()) && task.isVisible() && !isAnimatingByRecents(task)) {
            this.mTmpTasks.add(task);
        }
    }
}
