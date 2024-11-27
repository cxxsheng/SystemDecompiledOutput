package com.android.server.wm;

/* loaded from: classes3.dex */
class ActivitySnapshotController extends com.android.server.wm.AbsAppSnapshotController<com.android.server.wm.ActivityRecord, com.android.server.wm.ActivitySnapshotCache> {
    private static final boolean DEBUG = false;
    private static final int MAX_PERSIST_SNAPSHOT_COUNT = 20;
    static final java.lang.String SNAPSHOTS_DIRNAME = "activity_snapshots";
    private static final java.lang.String TAG = "WindowManager";
    private final android.util.ArraySet<com.android.server.wm.ActivityRecord> mOnBackPressedActivities;

    @com.android.internal.annotations.VisibleForTesting
    final android.util.ArraySet<com.android.server.wm.ActivityRecord> mPendingDeleteActivity;

    @com.android.internal.annotations.VisibleForTesting
    final android.util.ArraySet<com.android.server.wm.ActivityRecord> mPendingLoadActivity;

    @com.android.internal.annotations.VisibleForTesting
    final android.util.ArraySet<com.android.server.wm.ActivityRecord> mPendingRemoveActivity;
    private final com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider mPersistInfoProvider;
    private final com.android.server.wm.TaskSnapshotPersister mPersister;
    private final java.util.ArrayList<com.android.server.wm.ActivitySnapshotController.UserSavedFile> mSavedFilesInOrder;
    private final com.android.server.wm.AppSnapshotLoader mSnapshotLoader;
    private final com.android.server.wm.SnapshotPersistQueue mSnapshotPersistQueue;
    private final java.util.ArrayList<com.android.server.wm.ActivityRecord> mTmpBelowActivities;
    private final java.util.ArrayList<com.android.server.wm.WindowContainer> mTmpTransitionParticipants;
    private final android.util.SparseArray<android.util.SparseArray<com.android.server.wm.ActivitySnapshotController.UserSavedFile>> mUserSavedFiles;

    ActivitySnapshotController(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.SnapshotPersistQueue snapshotPersistQueue) {
        super(windowManagerService);
        this.mPendingRemoveActivity = new android.util.ArraySet<>();
        this.mPendingDeleteActivity = new android.util.ArraySet<>();
        this.mPendingLoadActivity = new android.util.ArraySet<>();
        this.mOnBackPressedActivities = new android.util.ArraySet<>();
        this.mTmpBelowActivities = new java.util.ArrayList<>();
        this.mTmpTransitionParticipants = new java.util.ArrayList<>();
        this.mUserSavedFiles = new android.util.SparseArray<>();
        this.mSavedFilesInOrder = new java.util.ArrayList<>();
        this.mSnapshotPersistQueue = snapshotPersistQueue;
        this.mPersistInfoProvider = createPersistInfoProvider(windowManagerService, new com.android.server.wm.ActivitySnapshotController$$ExternalSyntheticLambda1());
        this.mPersister = new com.android.server.wm.TaskSnapshotPersister(snapshotPersistQueue, this.mPersistInfoProvider);
        this.mSnapshotLoader = new com.android.server.wm.AppSnapshotLoader(this.mPersistInfoProvider);
        initialize(new com.android.server.wm.ActivitySnapshotCache());
        setSnapshotEnabled((windowManagerService.mContext.getResources().getBoolean(android.R.bool.config_disableMenuKeyInLockScreen) || !isSnapshotEnabled() || android.app.ActivityManager.isLowRamDeviceStatic()) ? false : true);
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    protected float initSnapshotScale() {
        return java.lang.Math.max(java.lang.Math.min(this.mService.mContext.getResources().getFloat(android.R.dimen.config_minScalingTouchMajor), 1.0f), 0.1f);
    }

    static boolean isSnapshotEnabled() {
        return android.os.SystemProperties.getInt("persist.wm.debug.activity_screenshot", 0) != 0 || com.android.window.flags.Flags.activitySnapshotByDefault();
    }

    static com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider createPersistInfoProvider(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.BaseAppSnapshotPersister.DirectoryResolver directoryResolver) {
        return new com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider(directoryResolver, SNAPSHOTS_DIRNAME, false, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, windowManagerService.mContext.getResources().getBoolean(android.R.bool.config_unfoldTransitionHingeAngle));
    }

    @android.annotation.Nullable
    android.window.TaskSnapshot getSnapshot(@android.annotation.NonNull com.android.server.wm.ActivityRecord[] activityRecordArr) {
        com.android.server.wm.ActivitySnapshotController.UserSavedFile findSavedFile;
        if (activityRecordArr.length == 0 || (findSavedFile = findSavedFile(activityRecordArr[0])) == null || findSavedFile.mActivityIds.size() != activityRecordArr.length) {
            return null;
        }
        int i = 0;
        for (int length = activityRecordArr.length - 1; length >= 0; length--) {
            i ^= getSystemHashCode(activityRecordArr[length]);
        }
        if (findSavedFile.mFileId == i) {
            return ((com.android.server.wm.ActivitySnapshotCache) this.mCache).getSnapshot(java.lang.Integer.valueOf(findSavedFile.mActivityIds.get(0)));
        }
        return null;
    }

    private void cleanUpUserFiles(int i) {
        synchronized (this.mSnapshotPersistQueue.getLock()) {
            this.mSnapshotPersistQueue.sendToQueueLocked(new com.android.server.wm.SnapshotPersistQueue.WriteQueueItem(this.mPersistInfoProvider, i) { // from class: com.android.server.wm.ActivitySnapshotController.1
                @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
                void write() {
                    java.io.File[] listFiles;
                    android.os.Trace.traceBegin(32L, "cleanUpUserFiles");
                    java.io.File directory = this.mPersistInfoProvider.getDirectory(this.mUserId);
                    if (directory.exists() && (listFiles = directory.listFiles()) != null) {
                        for (int length = listFiles.length - 1; length >= 0; length--) {
                            listFiles[length].delete();
                        }
                    }
                    android.os.Trace.traceEnd(32L);
                }
            });
        }
    }

    void addOnBackPressedActivity(com.android.server.wm.ActivityRecord activityRecord) {
        if (shouldDisableSnapshots()) {
            return;
        }
        this.mOnBackPressedActivities.add(activityRecord);
    }

    void clearOnBackPressedActivities() {
        if (shouldDisableSnapshots()) {
            return;
        }
        this.mOnBackPressedActivities.clear();
    }

    void beginSnapshotProcess() {
        if (shouldDisableSnapshots()) {
            return;
        }
        resetTmpFields();
    }

    void endSnapshotProcess() {
        if (shouldDisableSnapshots()) {
            return;
        }
        for (int size = this.mOnBackPressedActivities.size() - 1; size >= 0; size--) {
            handleActivityTransition(this.mOnBackPressedActivities.valueAt(size));
        }
        this.mOnBackPressedActivities.clear();
        this.mTmpTransitionParticipants.clear();
        postProcess();
    }

    @com.android.internal.annotations.VisibleForTesting
    void resetTmpFields() {
        this.mPendingRemoveActivity.clear();
        this.mPendingDeleteActivity.clear();
        this.mPendingLoadActivity.clear();
    }

    private void postProcess() {
        loadActivitySnapshot();
        for (int size = this.mPendingRemoveActivity.size() - 1; size >= 0; size--) {
            removeCachedFiles(this.mPendingRemoveActivity.valueAt(size));
        }
        for (int size2 = this.mPendingDeleteActivity.size() - 1; size2 >= 0; size2--) {
            removeIfUserSavedFileExist(this.mPendingDeleteActivity.valueAt(size2));
        }
        resetTmpFields();
    }

    class LoadActivitySnapshotItem extends com.android.server.wm.SnapshotPersistQueue.WriteQueueItem {
        private final com.android.server.wm.ActivityRecord[] mActivities;
        private final int mCode;

        LoadActivitySnapshotItem(@android.annotation.NonNull com.android.server.wm.ActivityRecord[] activityRecordArr, int i, int i2, @android.annotation.NonNull com.android.server.wm.BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
            super(persistInfoProvider, i2);
            this.mActivities = activityRecordArr;
            this.mCode = i;
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        void write() {
            try {
                android.os.Trace.traceBegin(32L, "load_activity_snapshot");
                android.window.TaskSnapshot loadTask = com.android.server.wm.ActivitySnapshotController.this.mSnapshotLoader.loadTask(this.mCode, this.mUserId, false);
                if (loadTask == null) {
                    return;
                }
                synchronized (com.android.server.wm.ActivitySnapshotController.this.mService.getWindowManagerLock()) {
                    if (com.android.server.wm.ActivitySnapshotController.this.hasRecord(this.mActivities[0])) {
                        for (com.android.server.wm.ActivityRecord activityRecord : this.mActivities) {
                            ((com.android.server.wm.ActivitySnapshotCache) com.android.server.wm.ActivitySnapshotController.this.mCache).putSnapshot(activityRecord, loadTask);
                        }
                    }
                }
            } finally {
                android.os.Trace.traceEnd(32L);
            }
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.server.wm.ActivitySnapshotController.LoadActivitySnapshotItem loadActivitySnapshotItem = (com.android.server.wm.ActivitySnapshotController.LoadActivitySnapshotItem) obj;
            return this.mCode == loadActivitySnapshotItem.mCode && this.mUserId == loadActivitySnapshotItem.mUserId && this.mPersistInfoProvider == loadActivitySnapshotItem.mPersistInfoProvider;
        }

        public java.lang.String toString() {
            return "LoadActivitySnapshotItem{code=" + this.mCode + ", UserId=" + this.mUserId + "}";
        }
    }

    void loadActivitySnapshot() {
        if (this.mPendingLoadActivity.isEmpty()) {
            return;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (int size = this.mPendingLoadActivity.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivitySnapshotController.UserSavedFile findSavedFile = findSavedFile(this.mPendingLoadActivity.valueAt(size));
            if (findSavedFile != null) {
                arraySet.add(findSavedFile);
            }
        }
        for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
            com.android.server.wm.ActivitySnapshotController.UserSavedFile userSavedFile = (com.android.server.wm.ActivitySnapshotController.UserSavedFile) arraySet.valueAt(size2);
            com.android.server.wm.ActivityRecord[] filterExistActivities = userSavedFile.filterExistActivities(this.mPendingLoadActivity);
            if (filterExistActivities != null && getSnapshot(filterExistActivities) == null) {
                loadSnapshotInner(filterExistActivities, userSavedFile);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void loadSnapshotInner(com.android.server.wm.ActivityRecord[] activityRecordArr, com.android.server.wm.ActivitySnapshotController.UserSavedFile userSavedFile) {
        synchronized (this.mSnapshotPersistQueue.getLock()) {
            this.mSnapshotPersistQueue.insertQueueAtFirstLocked(new com.android.server.wm.ActivitySnapshotController.LoadActivitySnapshotItem(activityRecordArr, userSavedFile.mFileId, userSavedFile.mUserId, this.mPersistInfoProvider));
        }
    }

    void recordSnapshot(@android.annotation.NonNull java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList) {
        if (shouldDisableSnapshots() || arrayList.isEmpty()) {
            return;
        }
        int size = arrayList.size();
        int[] iArr = new int[size];
        if (size == 1) {
            com.android.server.wm.ActivityRecord activityRecord = arrayList.get(0);
            android.window.TaskSnapshot recordSnapshotInner = recordSnapshotInner(activityRecord);
            if (recordSnapshotInner != null) {
                iArr[0] = getSystemHashCode(activityRecord);
                addUserSavedFile(activityRecord.mUserId, recordSnapshotInner, iArr);
                return;
            }
            return;
        }
        com.android.server.wm.Task task = arrayList.get(0).getTask();
        android.window.TaskSnapshot snapshot = this.mService.mTaskSnapshotController.snapshot(task, this.mHighResSnapshotScale);
        if (snapshot == null) {
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            com.android.server.wm.ActivityRecord activityRecord2 = arrayList.get(i);
            ((com.android.server.wm.ActivitySnapshotCache) this.mCache).putSnapshot(activityRecord2, snapshot);
            iArr[i] = getSystemHashCode(activityRecord2);
        }
        addUserSavedFile(task.mUserId, snapshot, iArr);
    }

    void notifyAppVisibilityChanged(com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        if (!shouldDisableSnapshots() && activityRecord.getTask() != null && !z) {
            resetTmpFields();
            addBelowActivityIfExist(activityRecord, this.mPendingRemoveActivity, false, "remove-snapshot");
            postProcess();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static int getSystemHashCode(com.android.server.wm.ActivityRecord activityRecord) {
        return java.lang.System.identityHashCode(activityRecord);
    }

    @com.android.internal.annotations.VisibleForTesting
    void handleTransitionFinish(@android.annotation.NonNull java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList) {
        this.mTmpTransitionParticipants.clear();
        this.mTmpTransitionParticipants.addAll(arrayList);
        for (int size = this.mTmpTransitionParticipants.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer windowContainer = this.mTmpTransitionParticipants.get(size);
            if (windowContainer.asTask() != null) {
                handleTaskTransition(windowContainer.asTask());
            } else if (windowContainer.asTaskFragment() != null) {
                com.android.server.wm.ActivityRecord topMostActivity = windowContainer.asTaskFragment().getTopMostActivity();
                if (topMostActivity != null) {
                    handleActivityTransition(topMostActivity);
                }
            } else if (windowContainer.asActivityRecord() != null) {
                handleActivityTransition(windowContainer.asActivityRecord());
            }
        }
    }

    private void handleActivityTransition(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        if (shouldDisableSnapshots()) {
            return;
        }
        if (activityRecord.isVisibleRequested()) {
            this.mPendingDeleteActivity.add(activityRecord);
            addBelowActivityIfExist(activityRecord, this.mPendingLoadActivity, false, "load-snapshot");
        } else {
            addBelowActivityIfExist(activityRecord, this.mPendingRemoveActivity, true, "remove-snapshot");
        }
    }

    private void handleTaskTransition(com.android.server.wm.Task task) {
        com.android.server.wm.ActivityRecord topMostActivity;
        if (shouldDisableSnapshots() || (topMostActivity = task.getTopMostActivity()) == null) {
            return;
        }
        if (task.isVisibleRequested()) {
            addBelowActivityIfExist(topMostActivity, this.mPendingLoadActivity, true, "load-snapshot");
            adjustSavedFileOrder(task);
        } else {
            addBelowActivityIfExist(topMostActivity, this.mPendingRemoveActivity, true, "remove-snapshot");
        }
    }

    private void addBelowActivityIfExist(com.android.server.wm.ActivityRecord activityRecord, android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet, boolean z, java.lang.String str) {
        getActivityBelow(activityRecord, z, this.mTmpBelowActivities);
        for (int size = this.mTmpBelowActivities.size() - 1; size >= 0; size--) {
            arraySet.add(this.mTmpBelowActivities.get(size));
        }
        this.mTmpBelowActivities.clear();
    }

    private void getActivityBelow(com.android.server.wm.ActivityRecord activityRecord, boolean z, java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList) {
        com.android.server.wm.ActivityRecord activityBelow;
        int indexOf;
        com.android.server.wm.Task task = activityRecord.getTask();
        if (task == null || (activityBelow = task.getActivityBelow(activityRecord)) == null) {
            return;
        }
        java.lang.Object taskFragment = activityRecord.getTaskFragment();
        com.android.server.wm.TaskFragment taskFragment2 = activityBelow.getTaskFragment();
        com.android.server.wm.TaskFragment adjacentTaskFragment = taskFragment2 != null ? taskFragment2.getAdjacentTaskFragment() : null;
        if ((taskFragment == taskFragment2 && taskFragment != null) || adjacentTaskFragment == null) {
            if (!z || isInParticipant(activityBelow, this.mTmpTransitionParticipants)) {
                arrayList.add(activityBelow);
                return;
            }
            return;
        }
        if (adjacentTaskFragment == taskFragment) {
            getActivityBelow(activityBelow, z, arrayList);
            return;
        }
        com.android.server.wm.Task task2 = adjacentTaskFragment.getTask();
        if (task2 == task) {
            if (taskFragment != null) {
                indexOf = task.mChildren.indexOf(taskFragment);
            } else {
                indexOf = task.mChildren.indexOf(activityRecord);
            }
            if (task2.mChildren.indexOf(adjacentTaskFragment) > indexOf) {
                return;
            }
        }
        if (!z || isInParticipant(activityBelow, this.mTmpTransitionParticipants)) {
            arrayList.add(activityBelow);
        }
        com.android.server.wm.ActivityRecord topMostActivity = adjacentTaskFragment.getTopMostActivity();
        if (topMostActivity != null) {
            if (!z || isInParticipant(topMostActivity, this.mTmpTransitionParticipants)) {
                arrayList.add(topMostActivity);
            }
        }
    }

    static boolean isInParticipant(com.android.server.wm.ActivityRecord activityRecord, java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer windowContainer = arrayList.get(size);
            if (activityRecord == windowContainer || activityRecord.isDescendantOf(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    private void adjustSavedFileOrder(com.android.server.wm.Task task) {
        task.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.ActivitySnapshotController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.ActivitySnapshotController.this.lambda$adjustSavedFileOrder$0((com.android.server.wm.ActivityRecord) obj);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$adjustSavedFileOrder$0(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.ActivitySnapshotController.UserSavedFile findSavedFile = findSavedFile(activityRecord);
        if (findSavedFile != null) {
            this.mSavedFilesInOrder.remove(findSavedFile);
            this.mSavedFilesInOrder.add(findSavedFile);
        }
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    void onAppRemoved(com.android.server.wm.ActivityRecord activityRecord) {
        if (shouldDisableSnapshots()) {
            return;
        }
        removeIfUserSavedFileExist(activityRecord);
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    void onAppDied(com.android.server.wm.ActivityRecord activityRecord) {
        if (shouldDisableSnapshots()) {
            return;
        }
        removeIfUserSavedFileExist(activityRecord);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.AbsAppSnapshotController
    public com.android.server.wm.ActivityRecord getTopActivity(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.AbsAppSnapshotController
    public com.android.server.wm.ActivityRecord getTopFullscreenActivity(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.WindowState findMainWindow = activityRecord.findMainWindow();
        if (findMainWindow == null || !findMainWindow.mAttrs.isFullscreen()) {
            return null;
        }
        return activityRecord;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.wm.AbsAppSnapshotController
    public android.app.ActivityManager.TaskDescription getTaskDescription(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.taskDescription;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.wm.AbsAppSnapshotController
    public com.android.server.wm.ActivityRecord findAppTokenForSnapshot(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord != null && activityRecord.canCaptureSnapshot()) {
            return activityRecord;
        }
        return null;
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    protected boolean use16BitFormat() {
        return this.mPersistInfoProvider.use16BitFormat();
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    protected android.graphics.Rect getLetterboxInsets(com.android.server.wm.ActivityRecord activityRecord) {
        return com.android.server.wm.Letterbox.EMPTY_RECT;
    }

    @android.annotation.NonNull
    private android.util.SparseArray<com.android.server.wm.ActivitySnapshotController.UserSavedFile> getUserFiles(int i) {
        if (this.mUserSavedFiles.get(i) == null) {
            this.mUserSavedFiles.put(i, new android.util.SparseArray<>());
            cleanUpUserFiles(i);
        }
        return this.mUserSavedFiles.get(i);
    }

    com.android.server.wm.ActivitySnapshotController.UserSavedFile findSavedFile(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        return findSavedFile(activityRecord.mUserId, getSystemHashCode(activityRecord));
    }

    com.android.server.wm.ActivitySnapshotController.UserSavedFile findSavedFile(int i, int i2) {
        return getUserFiles(i).get(i2);
    }

    private void removeCachedFiles(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.ActivitySnapshotController.UserSavedFile findSavedFile = findSavedFile(activityRecord);
        if (findSavedFile != null) {
            for (int size = findSavedFile.mActivityIds.size() - 1; size >= 0; size--) {
                ((com.android.server.wm.ActivitySnapshotCache) this.mCache).onIdRemoved(java.lang.Integer.valueOf(findSavedFile.mActivityIds.get(size)));
            }
        }
    }

    private void removeIfUserSavedFileExist(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.ActivitySnapshotController.UserSavedFile findSavedFile = findSavedFile(activityRecord);
        if (findSavedFile != null) {
            android.util.SparseArray<com.android.server.wm.ActivitySnapshotController.UserSavedFile> userFiles = getUserFiles(activityRecord.mUserId);
            for (int size = findSavedFile.mActivityIds.size() - 1; size >= 0; size--) {
                int i = findSavedFile.mActivityIds.get(size);
                findSavedFile.remove(i);
                ((com.android.server.wm.ActivitySnapshotCache) this.mCache).onIdRemoved(java.lang.Integer.valueOf(i));
                userFiles.remove(i);
            }
            this.mSavedFilesInOrder.remove(findSavedFile);
            this.mPersister.removeSnapshot(findSavedFile.mFileId, activityRecord.mUserId);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean hasRecord(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        return findSavedFile(activityRecord) != null;
    }

    @com.android.internal.annotations.VisibleForTesting
    void addUserSavedFile(int i, android.window.TaskSnapshot taskSnapshot, @android.annotation.NonNull int[] iArr) {
        int i2 = 0;
        com.android.server.wm.ActivitySnapshotController.UserSavedFile findSavedFile = findSavedFile(i, iArr[0]);
        if (findSavedFile != null) {
            android.util.Slog.w(TAG, "Duplicate request for recording activity snapshot " + findSavedFile);
            return;
        }
        for (int length = iArr.length - 1; length >= 0; length--) {
            i2 ^= iArr[length];
        }
        com.android.server.wm.ActivitySnapshotController.UserSavedFile userSavedFile = new com.android.server.wm.ActivitySnapshotController.UserSavedFile(i2, i);
        android.util.SparseArray<com.android.server.wm.ActivitySnapshotController.UserSavedFile> userFiles = getUserFiles(i);
        for (int length2 = iArr.length - 1; length2 >= 0; length2--) {
            userFiles.put(iArr[length2], userSavedFile);
        }
        userSavedFile.mActivityIds.addAll(iArr);
        this.mSavedFilesInOrder.add(userSavedFile);
        this.mPersister.persistSnapshot(i2, i, taskSnapshot);
        if (this.mSavedFilesInOrder.size() > 40) {
            purgeSavedFile();
        }
    }

    private void purgeSavedFile() {
        int size = this.mSavedFilesInOrder.size() - 20;
        if (size < 1) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = size - 1; i >= 0; i--) {
            com.android.server.wm.ActivitySnapshotController.UserSavedFile remove = this.mSavedFilesInOrder.remove(i);
            android.util.SparseArray<com.android.server.wm.ActivitySnapshotController.UserSavedFile> sparseArray = this.mUserSavedFiles.get(remove.mUserId);
            for (int size2 = remove.mActivityIds.size() - 1; size2 >= 0; size2--) {
                ((com.android.server.wm.ActivitySnapshotCache) this.mCache).removeRunningEntry(java.lang.Integer.valueOf(remove.mActivityIds.get(size2)));
                sparseArray.remove(remove.mActivityIds.get(size2));
            }
            arrayList.add(remove);
        }
        for (int size3 = arrayList.size() - 1; size3 >= 0; size3--) {
            com.android.server.wm.ActivitySnapshotController.UserSavedFile userSavedFile = (com.android.server.wm.ActivitySnapshotController.UserSavedFile) arrayList.get(size3);
            this.mPersister.removeSnapshot(userSavedFile.mFileId, userSavedFile.mUserId);
        }
    }

    @Override // com.android.server.wm.AbsAppSnapshotController
    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        super.dump(printWriter, str);
        java.lang.String str2 = str + "  ";
        java.lang.String str3 = str2 + "  ";
        for (int size = this.mUserSavedFiles.size() - 1; size >= 0; size--) {
            android.util.SparseArray<com.android.server.wm.ActivitySnapshotController.UserSavedFile> valueAt = this.mUserSavedFiles.valueAt(size);
            printWriter.println(str2 + "UserSavedFile userId=" + this.mUserSavedFiles.keyAt(size));
            android.util.ArraySet arraySet = new android.util.ArraySet();
            for (int size2 = valueAt.size() + (-1); size2 >= 0; size2--) {
                arraySet.add(valueAt.valueAt(size2));
            }
            for (int size3 = arraySet.size() - 1; size3 >= 0; size3 += -1) {
                printWriter.println(str3 + "SavedFile=" + arraySet.valueAt(size3));
            }
        }
    }

    static class UserSavedFile {
        final android.util.IntArray mActivityIds = new android.util.IntArray();
        final int mFileId;
        final int mUserId;

        UserSavedFile(int i, int i2) {
            this.mFileId = i;
            this.mUserId = i2;
        }

        boolean contains(int i) {
            return this.mActivityIds.contains(i);
        }

        void remove(int i) {
            int indexOf = this.mActivityIds.indexOf(i);
            if (indexOf >= 0) {
                this.mActivityIds.remove(indexOf);
            }
        }

        com.android.server.wm.ActivityRecord[] filterExistActivities(@android.annotation.NonNull android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet) {
            java.util.ArrayList arrayList = null;
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                com.android.server.wm.ActivityRecord valueAt = arraySet.valueAt(size);
                if (contains(com.android.server.wm.ActivitySnapshotController.getSystemHashCode(valueAt))) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(valueAt);
                }
            }
            if (arrayList == null || arrayList.size() != this.mActivityIds.size()) {
                return null;
            }
            return (com.android.server.wm.ActivityRecord[]) arrayList.toArray(new com.android.server.wm.ActivityRecord[0]);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("UserSavedFile{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(" fileId=");
            sb.append(java.lang.Integer.toHexString(this.mFileId));
            sb.append(", activityIds=[");
            for (int size = this.mActivityIds.size() - 1; size >= 0; size--) {
                sb.append(java.lang.Integer.toHexString(this.mActivityIds.get(size)));
                if (size > 0) {
                    sb.append(',');
                }
            }
            sb.append("]");
            sb.append("}");
            return sb.toString();
        }
    }
}
