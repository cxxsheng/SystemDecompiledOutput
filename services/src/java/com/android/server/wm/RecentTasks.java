package com.android.server.wm;

/* loaded from: classes3.dex */
class RecentTasks {
    private static final int DEFAULT_INITIAL_CAPACITY = 5;
    private static final int MAX_HIDDEN_TASK_SIZE = 10;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private static final java.lang.String TAG_RECENTS = "ActivityTaskManager";
    private static final java.lang.String TAG_TASKS = "ActivityTaskManager";
    private long mActiveTasksSessionDurationMs;
    private final java.util.ArrayList<com.android.server.wm.RecentTasks.Callbacks> mCallbacks;
    private boolean mCheckTrimmableTasksOnIdle;

    @android.annotation.Nullable
    private java.lang.String mFeatureId;
    private boolean mFreezeTaskListReordering;
    private long mFreezeTaskListTimeoutMs;
    private int mGlobalMaxNumTasks;
    private boolean mHasVisibleRecentTasks;
    private final java.util.ArrayList<com.android.server.wm.Task> mHiddenTasks;
    private final android.view.WindowManagerPolicyConstants.PointerEventListener mListener;
    private int mMaxNumVisibleTasks;
    private int mMinNumVisibleTasks;
    private final android.util.SparseArray<android.util.SparseBooleanArray> mPersistedTaskIds;
    private android.content.ComponentName mRecentsComponent;
    private int mRecentsUid;
    private final java.lang.Runnable mResetFreezeTaskListOnTimeoutRunnable;
    private final com.android.server.wm.ActivityTaskManagerService mService;
    private final com.android.server.wm.ActivityTaskSupervisor mSupervisor;
    private com.android.server.wm.TaskChangeNotificationController mTaskNotificationController;
    private final com.android.server.wm.TaskPersister mTaskPersister;
    private final java.util.ArrayList<com.android.server.wm.Task> mTasks;
    private final java.util.HashMap<android.content.ComponentName, android.content.pm.ActivityInfo> mTmpAvailActCache;
    private final java.util.HashMap<java.lang.String, android.content.pm.ApplicationInfo> mTmpAvailAppCache;
    private final android.util.SparseBooleanArray mTmpQuietProfileUserIds;
    private final java.util.ArrayList<com.android.server.wm.Task> mTmpRecents;
    private final android.util.SparseArray<java.util.concurrent.atomic.AtomicBoolean> mUsersWithRecentsLoaded;
    private static final long FREEZE_TASK_LIST_TIMEOUT_MS = java.util.concurrent.TimeUnit.SECONDS.toMillis(5);
    private static final java.util.Comparator<com.android.server.wm.Task> TASK_ID_COMPARATOR = new java.util.Comparator() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            int lambda$static$0;
            lambda$static$0 = com.android.server.wm.RecentTasks.lambda$static$0((com.android.server.wm.Task) obj, (com.android.server.wm.Task) obj2);
            return lambda$static$0;
        }
    };
    private static final android.content.pm.ActivityInfo NO_ACTIVITY_INFO_TOKEN = new android.content.pm.ActivityInfo();
    private static final android.content.pm.ApplicationInfo NO_APPLICATION_INFO_TOKEN = new android.content.pm.ApplicationInfo();

    interface Callbacks {
        void onRecentTaskAdded(com.android.server.wm.Task task);

        void onRecentTaskRemoved(com.android.server.wm.Task task, boolean z, boolean z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$static$0(com.android.server.wm.Task task, com.android.server.wm.Task task2) {
        return task2.mTaskId - task.mTaskId;
    }

    /* renamed from: com.android.server.wm.RecentTasks$1, reason: invalid class name */
    class AnonymousClass1 implements android.view.WindowManagerPolicyConstants.PointerEventListener {
        AnonymousClass1() {
        }

        public void onPointerEvent(android.view.MotionEvent motionEvent) {
            if (!com.android.server.wm.RecentTasks.this.mFreezeTaskListReordering || motionEvent.getAction() != 0 || motionEvent.getClassification() == 4) {
                return;
            }
            final int displayId = motionEvent.getDisplayId();
            final int x = (int) motionEvent.getX();
            final int y = (int) motionEvent.getY();
            com.android.server.wm.RecentTasks.this.mService.mH.post(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.Consumer() { // from class: com.android.server.wm.RecentTasks$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.RecentTasks.AnonymousClass1.this.lambda$onPointerEvent$0(displayId, x, y, obj);
                }
            }, (java.lang.Object) null).recycleOnUse());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPointerEvent$0(int i, int i2, int i3, java.lang.Object obj) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.RecentTasks.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowState touchableWinAtPointLocked = com.android.server.wm.RecentTasks.this.mService.mRootWindowContainer.getDisplayContent(i).mDisplayContent.getTouchableWinAtPointLocked(i2, i3);
                    if (touchableWinAtPointLocked == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    boolean z = true;
                    if (1 > touchableWinAtPointLocked.mAttrs.type || touchableWinAtPointLocked.mAttrs.type > 99) {
                        z = false;
                    }
                    if (z) {
                        com.android.server.wm.RecentTasks.this.mService.mH.removeCallbacks(com.android.server.wm.RecentTasks.this.mResetFreezeTaskListOnTimeoutRunnable);
                        com.android.server.wm.RecentTasks.this.mService.mH.postDelayed(com.android.server.wm.RecentTasks.this.mResetFreezeTaskListOnTimeoutRunnable, 500L);
                    }
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    RecentTasks(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.TaskPersister taskPersister) {
        this.mRecentsUid = -1;
        this.mRecentsComponent = null;
        this.mUsersWithRecentsLoaded = new android.util.SparseArray<>(5);
        this.mPersistedTaskIds = new android.util.SparseArray<>(5);
        this.mTasks = new java.util.ArrayList<>();
        this.mCallbacks = new java.util.ArrayList<>();
        this.mHiddenTasks = new java.util.ArrayList<>();
        this.mFreezeTaskListTimeoutMs = FREEZE_TASK_LIST_TIMEOUT_MS;
        this.mTmpRecents = new java.util.ArrayList<>();
        this.mTmpAvailActCache = new java.util.HashMap<>();
        this.mTmpAvailAppCache = new java.util.HashMap<>();
        this.mTmpQuietProfileUserIds = new android.util.SparseBooleanArray();
        this.mListener = new com.android.server.wm.RecentTasks.AnonymousClass1();
        this.mResetFreezeTaskListOnTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.RecentTasks.this.resetFreezeTaskListReorderingOnTimeout();
            }
        };
        this.mService = activityTaskManagerService;
        this.mSupervisor = this.mService.mTaskSupervisor;
        this.mTaskPersister = taskPersister;
        this.mGlobalMaxNumTasks = android.app.ActivityTaskManager.getMaxRecentTasksStatic();
        this.mHasVisibleRecentTasks = true;
        this.mTaskNotificationController = activityTaskManagerService.getTaskChangeNotificationController();
    }

    RecentTasks(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) {
        this.mRecentsUid = -1;
        this.mRecentsComponent = null;
        this.mUsersWithRecentsLoaded = new android.util.SparseArray<>(5);
        this.mPersistedTaskIds = new android.util.SparseArray<>(5);
        this.mTasks = new java.util.ArrayList<>();
        this.mCallbacks = new java.util.ArrayList<>();
        this.mHiddenTasks = new java.util.ArrayList<>();
        this.mFreezeTaskListTimeoutMs = FREEZE_TASK_LIST_TIMEOUT_MS;
        this.mTmpRecents = new java.util.ArrayList<>();
        this.mTmpAvailActCache = new java.util.HashMap<>();
        this.mTmpAvailAppCache = new java.util.HashMap<>();
        this.mTmpQuietProfileUserIds = new android.util.SparseBooleanArray();
        this.mListener = new com.android.server.wm.RecentTasks.AnonymousClass1();
        this.mResetFreezeTaskListOnTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.RecentTasks$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.RecentTasks.this.resetFreezeTaskListReorderingOnTimeout();
            }
        };
        java.io.File dataSystemDirectory = android.os.Environment.getDataSystemDirectory();
        android.content.res.Resources resources = activityTaskManagerService.mContext.getResources();
        this.mService = activityTaskManagerService;
        this.mSupervisor = this.mService.mTaskSupervisor;
        this.mTaskPersister = new com.android.server.wm.TaskPersister(dataSystemDirectory, activityTaskSupervisor, activityTaskManagerService, this, activityTaskSupervisor.mPersisterQueue);
        this.mGlobalMaxNumTasks = android.app.ActivityTaskManager.getMaxRecentTasksStatic();
        this.mTaskNotificationController = activityTaskManagerService.getTaskChangeNotificationController();
        this.mHasVisibleRecentTasks = resources.getBoolean(android.R.bool.config_guestUserEphemeral);
        loadParametersFromResources(resources);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setParameters(int i, int i2, long j) {
        this.mMinNumVisibleTasks = i;
        this.mMaxNumVisibleTasks = i2;
        this.mActiveTasksSessionDurationMs = j;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setGlobalMaxNumTasks(int i) {
        this.mGlobalMaxNumTasks = i;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setFreezeTaskListTimeout(long j) {
        this.mFreezeTaskListTimeoutMs = j;
    }

    android.view.WindowManagerPolicyConstants.PointerEventListener getInputListener() {
        return this.mListener;
    }

    void setFreezeTaskListReordering() {
        if (!this.mFreezeTaskListReordering) {
            this.mTaskNotificationController.notifyTaskListFrozen(true);
            this.mFreezeTaskListReordering = true;
        }
        this.mService.mH.removeCallbacks(this.mResetFreezeTaskListOnTimeoutRunnable);
        this.mService.mH.postDelayed(this.mResetFreezeTaskListOnTimeoutRunnable, this.mFreezeTaskListTimeoutMs);
    }

    void resetFreezeTaskListReordering(com.android.server.wm.Task task) {
        if (!this.mFreezeTaskListReordering) {
            return;
        }
        this.mFreezeTaskListReordering = false;
        this.mService.mH.removeCallbacks(this.mResetFreezeTaskListOnTimeoutRunnable);
        if (task != null) {
            this.mTasks.remove(task);
            this.mTasks.add(0, task);
        }
        trimInactiveRecentTasks();
        this.mTaskNotificationController.notifyTaskStackChanged();
        this.mTaskNotificationController.notifyTaskListFrozen(false);
    }

    @com.android.internal.annotations.VisibleForTesting
    void resetFreezeTaskListReorderingOnTimeout() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task topDisplayFocusedRootTask = this.mService.getTopDisplayFocusedRootTask();
                com.android.server.wm.Task task = null;
                com.android.server.wm.Task topMostTask = topDisplayFocusedRootTask != null ? topDisplayFocusedRootTask.getTopMostTask() : null;
                if (topMostTask != null && topMostTask.hasChild()) {
                    task = topMostTask;
                }
                resetFreezeTaskListReordering(task);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isFreezeTaskListReorderingSet() {
        return this.mFreezeTaskListReordering;
    }

    @com.android.internal.annotations.VisibleForTesting
    void loadParametersFromResources(android.content.res.Resources resources) {
        long j;
        if (android.app.ActivityManager.isLowRamDeviceStatic()) {
            this.mMinNumVisibleTasks = resources.getInteger(android.R.integer.config_minDreamOverlayDurationMs);
            this.mMaxNumVisibleTasks = resources.getInteger(android.R.integer.config_lowMemoryKillerMinFreeKbytesAdjust);
        } else if (android.os.SystemProperties.getBoolean("ro.recents.grid", false)) {
            this.mMinNumVisibleTasks = resources.getInteger(android.R.integer.config_metrics_pull_cooldown_millis);
            this.mMaxNumVisibleTasks = resources.getInteger(android.R.integer.config_lowMemoryKillerMinFreeKbytesAbsolute);
        } else {
            this.mMinNumVisibleTasks = resources.getInteger(android.R.integer.config_mediaRouter_builtInSpeakerSuitability);
            this.mMaxNumVisibleTasks = resources.getInteger(android.R.integer.config_lowBatteryWarningLevel);
        }
        int integer = resources.getInteger(android.R.integer.config_activeTaskDurationHours);
        if (integer > 0) {
            j = java.util.concurrent.TimeUnit.HOURS.toMillis(integer);
        } else {
            j = -1;
        }
        this.mActiveTasksSessionDurationMs = j;
    }

    void loadRecentsComponent(android.content.res.Resources resources) {
        android.content.ComponentName unflattenFromString;
        java.lang.String string = resources.getString(android.R.string.config_platformVpnConfirmDialogComponent);
        if (!android.text.TextUtils.isEmpty(string) && (unflattenFromString = android.content.ComponentName.unflattenFromString(string)) != null) {
            try {
                android.content.pm.ApplicationInfo applicationInfo = android.app.AppGlobals.getPackageManager().getApplicationInfo(unflattenFromString.getPackageName(), 8704L, this.mService.mContext.getUserId());
                if (applicationInfo != null) {
                    this.mRecentsUid = applicationInfo.uid;
                    this.mRecentsComponent = unflattenFromString;
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.w("ActivityTaskManager", "Could not load application info for recents component: " + unflattenFromString);
            }
        }
    }

    boolean isCallerRecents(int i) {
        return android.os.UserHandle.isSameApp(i, this.mRecentsUid);
    }

    boolean isRecentsComponent(android.content.ComponentName componentName, int i) {
        return componentName.equals(this.mRecentsComponent) && android.os.UserHandle.isSameApp(i, this.mRecentsUid);
    }

    boolean isRecentsComponentHomeActivity(int i) {
        android.content.ComponentName defaultHomeActivity = this.mService.getPackageManagerInternalLocked().getDefaultHomeActivity(i);
        return (defaultHomeActivity == null || this.mRecentsComponent == null || !defaultHomeActivity.getPackageName().equals(this.mRecentsComponent.getPackageName())) ? false : true;
    }

    android.content.ComponentName getRecentsComponent() {
        return this.mRecentsComponent;
    }

    @android.annotation.Nullable
    java.lang.String getRecentsComponentFeatureId() {
        return this.mFeatureId;
    }

    int getRecentsComponentUid() {
        return this.mRecentsUid;
    }

    void registerCallback(com.android.server.wm.RecentTasks.Callbacks callbacks) {
        this.mCallbacks.add(callbacks);
    }

    void unregisterCallback(com.android.server.wm.RecentTasks.Callbacks callbacks) {
        this.mCallbacks.remove(callbacks);
    }

    private void notifyTaskAdded(com.android.server.wm.Task task) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            this.mCallbacks.get(i).onRecentTaskAdded(task);
        }
        this.mTaskNotificationController.notifyTaskListUpdated();
    }

    private void notifyTaskRemoved(com.android.server.wm.Task task, boolean z, boolean z2) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            this.mCallbacks.get(i).onRecentTaskRemoved(task, z, z2);
        }
        this.mTaskNotificationController.notifyTaskListUpdated();
    }

    void loadRecentTasksIfNeeded(int i) {
        java.util.concurrent.atomic.AtomicBoolean atomicBoolean;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                atomicBoolean = this.mUsersWithRecentsLoaded.get(i);
                if (atomicBoolean == null) {
                    android.util.SparseArray<java.util.concurrent.atomic.AtomicBoolean> sparseArray = this.mUsersWithRecentsLoaded;
                    java.util.concurrent.atomic.AtomicBoolean atomicBoolean2 = new java.util.concurrent.atomic.AtomicBoolean();
                    sparseArray.append(i, atomicBoolean2);
                    atomicBoolean = atomicBoolean2;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        synchronized (atomicBoolean) {
            try {
                if (atomicBoolean.get()) {
                    return;
                }
                android.util.SparseBooleanArray readPersistedTaskIdsFromFileForUser = this.mTaskPersister.readPersistedTaskIdsFromFileForUser(i);
                com.android.server.wm.TaskPersister.RecentTaskFiles loadTasksForUser = com.android.server.wm.TaskPersister.loadTasksForUser(i);
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock2) {
                    try {
                        restoreRecentTasksLocked(i, readPersistedTaskIdsFromFileForUser, loadTasksForUser);
                    } finally {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                atomicBoolean.set(true);
            } finally {
            }
        }
    }

    private void restoreRecentTasksLocked(int i, android.util.SparseBooleanArray sparseBooleanArray, com.android.server.wm.TaskPersister.RecentTaskFiles recentTaskFiles) {
        this.mTaskPersister.setPersistedTaskIds(i, sparseBooleanArray);
        this.mPersistedTaskIds.put(i, sparseBooleanArray.clone());
        android.util.IntArray intArray = new android.util.IntArray();
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            com.android.server.wm.Task task = this.mTasks.get(size);
            if (task.mUserId == i && shouldPersistTaskLocked(task)) {
                intArray.add(task.mTaskId);
            }
        }
        android.util.Slog.i("ActivityTaskManager", "Restoring recents for user " + i);
        java.util.ArrayList<com.android.server.wm.Task> restoreTasksForUserLocked = this.mTaskPersister.restoreTasksForUserLocked(i, recentTaskFiles, intArray);
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        for (int i2 = 0; i2 < restoreTasksForUserLocked.size(); i2++) {
            restoreTasksForUserLocked.get(i2).lastActiveTime = elapsedRealtime - i2;
        }
        this.mTasks.addAll(restoreTasksForUserLocked);
        cleanupLocked(i);
        if (intArray.size() > 0) {
            syncPersistentTaskIdsLocked();
        }
    }

    private boolean isRecentTasksLoaded(int i) {
        java.util.concurrent.atomic.AtomicBoolean atomicBoolean = this.mUsersWithRecentsLoaded.get(i);
        return atomicBoolean != null && atomicBoolean.get();
    }

    boolean containsTaskId(int i, int i2) {
        android.util.SparseBooleanArray sparseBooleanArray = this.mPersistedTaskIds.get(i2);
        return sparseBooleanArray != null && sparseBooleanArray.get(i);
    }

    android.util.SparseBooleanArray getTaskIdsForLoadedUser(int i) {
        android.util.SparseBooleanArray sparseBooleanArray = this.mPersistedTaskIds.get(i);
        if (sparseBooleanArray == null) {
            android.util.Slog.wtf("ActivityTaskManager", "Loaded user without loaded tasks, userId=" + i);
            return new android.util.SparseBooleanArray();
        }
        return sparseBooleanArray;
    }

    void notifyTaskPersisterLocked(com.android.server.wm.Task task, boolean z) {
        com.android.server.wm.Task rootTask = task != null ? task.getRootTask() : null;
        if (rootTask != null && rootTask.isActivityTypeHomeOrRecents()) {
            return;
        }
        syncPersistentTaskIdsLocked();
        this.mTaskPersister.wakeup(task, z);
    }

    private void syncPersistentTaskIdsLocked() {
        for (int size = this.mPersistedTaskIds.size() - 1; size >= 0; size--) {
            if (isRecentTasksLoaded(this.mPersistedTaskIds.keyAt(size))) {
                this.mPersistedTaskIds.valueAt(size).clear();
            }
        }
        for (int size2 = this.mTasks.size() - 1; size2 >= 0; size2--) {
            com.android.server.wm.Task task = this.mTasks.get(size2);
            if (shouldPersistTaskLocked(task)) {
                if (this.mPersistedTaskIds.get(task.mUserId) == null) {
                    android.util.Slog.wtf("ActivityTaskManager", "No task ids found for userId " + task.mUserId + ". task=" + task + " mPersistedTaskIds=" + this.mPersistedTaskIds);
                    this.mPersistedTaskIds.put(task.mUserId, new android.util.SparseBooleanArray());
                }
                this.mPersistedTaskIds.get(task.mUserId).put(task.mTaskId, true);
            }
        }
    }

    private static boolean shouldPersistTaskLocked(com.android.server.wm.Task task) {
        com.android.server.wm.Task rootTask = task.getRootTask();
        return task.isPersistable && (rootTask == null || !rootTask.isActivityTypeHomeOrRecents());
    }

    void onSystemReadyLocked() {
        loadRecentsComponent(this.mService.mContext.getResources());
        this.mTasks.clear();
    }

    android.graphics.Bitmap getTaskDescriptionIcon(java.lang.String str) {
        return this.mTaskPersister.getTaskDescriptionIcon(str);
    }

    void saveImage(android.graphics.Bitmap bitmap, java.lang.String str) {
        this.mTaskPersister.saveImage(bitmap, str);
    }

    void flush() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                syncPersistentTaskIdsLocked();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        this.mTaskPersister.flush();
    }

    int[] usersWithRecentsLoadedLocked() {
        int size = this.mUsersWithRecentsLoaded.size();
        int[] iArr = new int[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = this.mUsersWithRecentsLoaded.keyAt(i2);
            if (this.mUsersWithRecentsLoaded.valueAt(i2).get()) {
                iArr[i] = keyAt;
                i++;
            }
        }
        if (i < size) {
            return java.util.Arrays.copyOf(iArr, i);
        }
        return iArr;
    }

    void unloadUserDataFromMemoryLocked(int i) {
        if (isRecentTasksLoaded(i)) {
            android.util.Slog.i("ActivityTaskManager", "Unloading recents for user " + i + " from memory.");
            this.mUsersWithRecentsLoaded.delete(i);
            removeTasksForUserLocked(i);
        }
        this.mPersistedTaskIds.delete(i);
        this.mTaskPersister.unloadUserDataFromMemory(i);
    }

    private void removeTasksForUserLocked(int i) {
        if (i <= 0) {
            android.util.Slog.i("ActivityTaskManager", "Can't remove recent task on user " + i);
            return;
        }
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            com.android.server.wm.Task task = this.mTasks.get(size);
            if (task.mUserId == i) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 3308140128142966415L, 4, null, java.lang.String.valueOf(task), java.lang.Long.valueOf(i));
                remove(task);
            }
        }
    }

    void onPackagesSuspendedChanged(java.lang.String[] strArr, boolean z, int i) {
        java.util.HashSet newHashSet = com.google.android.collect.Sets.newHashSet(strArr);
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            com.android.server.wm.Task task = this.mTasks.get(size);
            if (task.realActivity != null && newHashSet.contains(task.realActivity.getPackageName()) && task.mUserId == i && task.realActivitySuspended != z) {
                task.realActivitySuspended = z;
                if (z) {
                    this.mSupervisor.removeTask(task, false, true, "suspended-package");
                }
                notifyTaskPersisterLocked(task, false);
            }
        }
    }

    void onLockTaskModeStateChanged(int i, int i2) {
        if (i == 1) {
            for (int size = this.mTasks.size() - 1; size >= 0; size--) {
                com.android.server.wm.Task task = this.mTasks.get(size);
                if (task.mUserId == i2) {
                    this.mService.getLockTaskController();
                    if (!com.android.server.wm.LockTaskController.isTaskAuthAllowlisted(task.mLockTaskAuth)) {
                        remove(task);
                    }
                }
            }
        }
    }

    void removeCompatibleRecentTask(com.android.server.wm.Task task) {
        int findRemoveIndexForTask;
        int indexOf = this.mTasks.indexOf(task);
        if (indexOf < 0 || (findRemoveIndexForTask = findRemoveIndexForTask(task, false)) == -1) {
            return;
        }
        if (indexOf <= findRemoveIndexForTask) {
            task = this.mTasks.get(findRemoveIndexForTask);
        }
        remove(task);
    }

    void removeTasksByPackageName(java.lang.String str, int i) {
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            com.android.server.wm.Task task = this.mTasks.get(size);
            if (task.mUserId == i && task.getBasePackageName().equals(str)) {
                this.mSupervisor.removeTask(task, true, true, "remove-package-task");
            }
        }
    }

    void removeAllVisibleTasks(int i) {
        java.util.Set<java.lang.Integer> profileIds = getProfileIds(i);
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            com.android.server.wm.Task task = this.mTasks.get(size);
            if (profileIds.contains(java.lang.Integer.valueOf(task.mUserId)) && isVisibleRecentTask(task)) {
                this.mTasks.remove(size);
                notifyTaskRemoved(task, true, true);
            }
        }
    }

    void cleanupDisabledPackageTasksLocked(java.lang.String str, java.util.Set<java.lang.String> set, int i) {
        for (int size = this.mTasks.size() - 1; size >= 0; size--) {
            com.android.server.wm.Task task = this.mTasks.get(size);
            if (i == -1 || task.mUserId == i) {
                android.content.ComponentName component = task.intent != null ? task.intent.getComponent() : null;
                if (component != null && component.getPackageName().equals(str) && (set == null || set.contains(component.getClassName()))) {
                    this.mSupervisor.removeTask(task, false, true, "disabled-package");
                }
            }
        }
    }

    void cleanupLocked(int i) {
        int i2;
        int size = this.mTasks.size();
        if (size == 0) {
            return;
        }
        this.mTmpAvailActCache.clear();
        this.mTmpAvailAppCache.clear();
        android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
        int i3 = size - 1;
        while (true) {
            i2 = 0;
            if (i3 < 0) {
                break;
            }
            com.android.server.wm.Task task = this.mTasks.get(i3);
            if (i == -1 || task.mUserId == i) {
                if (task.autoRemoveRecents && task.getTopNonFinishingActivity() == null) {
                    remove(task);
                    android.util.Slog.w("ActivityTaskManager", "Removing auto-remove without activity: " + task);
                } else if (task.realActivity != null) {
                    android.content.pm.ActivityInfo activityInfo = this.mTmpAvailActCache.get(task.realActivity);
                    if (activityInfo == null) {
                        try {
                            activityInfo = packageManager.getActivityInfo(task.realActivity, 268436480L, i);
                            if (activityInfo == null) {
                                activityInfo = NO_ACTIVITY_INFO_TOKEN;
                            }
                            this.mTmpAvailActCache.put(task.realActivity, activityInfo);
                        } catch (android.os.RemoteException e) {
                        }
                    }
                    if (activityInfo == NO_ACTIVITY_INFO_TOKEN) {
                        android.content.pm.ApplicationInfo applicationInfo = this.mTmpAvailAppCache.get(task.realActivity.getPackageName());
                        if (applicationInfo == null) {
                            try {
                                applicationInfo = packageManager.getApplicationInfo(task.realActivity.getPackageName(), 8192L, i);
                                if (applicationInfo == null) {
                                    applicationInfo = NO_APPLICATION_INFO_TOKEN;
                                }
                                this.mTmpAvailAppCache.put(task.realActivity.getPackageName(), applicationInfo);
                            } catch (android.os.RemoteException e2) {
                            }
                        }
                        if (applicationInfo == NO_APPLICATION_INFO_TOKEN || (applicationInfo.flags & 8388608) == 0) {
                            remove(task);
                            android.util.Slog.w("ActivityTaskManager", "Removing no longer valid recent: " + task);
                        } else {
                            task.isAvailable = false;
                        }
                    } else if (!activityInfo.enabled || !activityInfo.applicationInfo.enabled || (activityInfo.applicationInfo.flags & 8388608) == 0) {
                        task.isAvailable = false;
                    } else {
                        task.isAvailable = true;
                    }
                }
            }
            i3--;
        }
        int size2 = this.mTasks.size();
        while (i2 < size2) {
            i2 = processNextAffiliateChainLocked(i2);
        }
    }

    private boolean canAddTaskWithoutTrim(com.android.server.wm.Task task) {
        return findRemoveIndexForAddTask(task) == -1;
    }

    java.util.ArrayList<android.os.IBinder> getAppTasksList(int i, java.lang.String str) {
        java.util.ArrayList<android.os.IBinder> arrayList = new java.util.ArrayList<>();
        int size = this.mTasks.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.wm.Task task = this.mTasks.get(i2);
            if (task.effectiveUid == i && str.equals(task.getBasePackageName())) {
                arrayList.add(new com.android.server.wm.AppTaskImpl(this.mService, task.mTaskId, i).asBinder());
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.Set<java.lang.Integer> getProfileIds(int i) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (int i2 : this.mService.getUserManager().getProfileIds(i, false)) {
            arraySet.add(java.lang.Integer.valueOf(i2));
        }
        return arraySet;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.content.pm.UserInfo getUserInfo(int i) {
        return this.mService.getUserManager().getUserInfo(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    int[] getCurrentProfileIds() {
        return this.mService.mAmInternal.getCurrentProfileIds();
    }

    android.content.pm.ParceledListSlice<android.app.ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2, boolean z, int i3, int i4) {
        return new android.content.pm.ParceledListSlice<>(getRecentTasksImpl(i, i2, z, i3, i4));
    }

    private java.util.ArrayList<android.app.ActivityManager.RecentTaskInfo> getRecentTasksImpl(int i, int i2, boolean z, int i3, int i4) {
        boolean z2 = (i2 & 1) != 0;
        java.util.Set<java.lang.Integer> profileIds = getProfileIds(i3);
        profileIds.add(java.lang.Integer.valueOf(i3));
        java.util.ArrayList<android.app.ActivityManager.RecentTaskInfo> arrayList = new java.util.ArrayList<>();
        int size = this.mTasks.size();
        int i5 = 0;
        for (int i6 = 0; i6 < size; i6++) {
            com.android.server.wm.Task task = this.mTasks.get(i6);
            if (isVisibleRecentTask(task)) {
                i5++;
                if (isInVisibleRange(task, i6, i5, z2) && arrayList.size() < i && profileIds.contains(java.lang.Integer.valueOf(task.mUserId)) && !task.realActivitySuspended && ((z || task.isActivityTypeHome() || task.effectiveUid == i4) && ((!task.autoRemoveRecents || task.getTopNonFinishingActivity() != null) && ((i2 & 2) == 0 || task.isAvailable)))) {
                    if (task.mUserSetupComplete) {
                        arrayList.add(createRecentTaskInfo(task, true, z));
                    } else {
                        android.util.Slog.d("ActivityTaskManager", "Skipping, user setup not complete: " + task);
                    }
                }
            }
        }
        return arrayList;
    }

    void getPersistableTaskIds(android.util.ArraySet<java.lang.Integer> arraySet) {
        int size = this.mTasks.size();
        for (int i = 0; i < size; i++) {
            com.android.server.wm.Task task = this.mTasks.get(i);
            com.android.server.wm.Task rootTask = task.getRootTask();
            if ((task.isPersistable || task.inRecents) && (rootTask == null || !rootTask.isActivityTypeHomeOrRecents())) {
                arraySet.add(java.lang.Integer.valueOf(task.mTaskId));
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.ArrayList<com.android.server.wm.Task> getRawTasks() {
        return this.mTasks;
    }

    android.util.SparseBooleanArray getRecentTaskIds() {
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        int size = this.mTasks.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.wm.Task task = this.mTasks.get(i2);
            if (isVisibleRecentTask(task)) {
                i++;
                if (isInVisibleRange(task, i2, i, false)) {
                    sparseBooleanArray.put(task.mTaskId, true);
                }
            }
        }
        return sparseBooleanArray;
    }

    com.android.server.wm.Task getTask(int i) {
        int size = this.mTasks.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.wm.Task task = this.mTasks.get(i2);
            if (task.mTaskId == i) {
                return task;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void add(com.android.server.wm.Task task) {
        boolean z;
        int removeForAddTask;
        boolean z2 = (task.mAffiliatedTaskId == task.mTaskId && task.mNextAffiliateTaskId == -1 && task.mPrevAffiliateTaskId == -1) ? false : true;
        int size = this.mTasks.size();
        if (task.voiceSession != null) {
            return;
        }
        if (!z2 && size > 0 && this.mTasks.get(0) == task) {
            return;
        }
        if (z2 && size > 0 && task.inRecents && task.mAffiliatedTaskId == this.mTasks.get(0).mAffiliatedTaskId) {
            return;
        }
        if (task.inRecents) {
            int indexOf = this.mTasks.indexOf(task);
            if (indexOf >= 0) {
                if (!z2) {
                    if (!this.mFreezeTaskListReordering) {
                        int findIndexToAdd = findIndexToAdd(task);
                        this.mTasks.remove(indexOf);
                        this.mTasks.add(findIndexToAdd, task);
                        if (indexOf != 0) {
                            this.mTaskNotificationController.notifyTaskListUpdated();
                        }
                    }
                    notifyTaskPersisterLocked(task, false);
                    return;
                }
            } else {
                android.util.Slog.wtf("ActivityTaskManager", "Task with inRecent not in recents: " + task);
                z = true;
                removeForAddTask = removeForAddTask(task);
                task.inRecents = true;
                if (z2 || z) {
                    if (this.mFreezeTaskListReordering || removeForAddTask == -1) {
                        removeForAddTask = 0;
                    }
                    this.mTasks.add(removeForAddTask, task);
                    notifyTaskAdded(task);
                } else if (z2) {
                    com.android.server.wm.Task task2 = task.mNextAffiliate;
                    if (task2 == null) {
                        task2 = task.mPrevAffiliate;
                    }
                    if (task2 != null) {
                        int indexOf2 = this.mTasks.indexOf(task2);
                        if (indexOf2 >= 0) {
                            if (task2 == task.mNextAffiliate) {
                                indexOf2++;
                            }
                            this.mTasks.add(indexOf2, task);
                            notifyTaskAdded(task);
                            if (moveAffiliatedTasksToFront(task, indexOf2)) {
                                return;
                            }
                        }
                        z = true;
                    } else {
                        z = true;
                    }
                }
                if (z) {
                    cleanupLocked(task.mUserId);
                }
                this.mCheckTrimmableTasksOnIdle = true;
                notifyTaskPersisterLocked(task, false);
            }
        }
        z = false;
        removeForAddTask = removeForAddTask(task);
        task.inRecents = true;
        if (z2) {
        }
        if (this.mFreezeTaskListReordering) {
        }
        removeForAddTask = 0;
        this.mTasks.add(removeForAddTask, task);
        notifyTaskAdded(task);
        if (z) {
        }
        this.mCheckTrimmableTasksOnIdle = true;
        notifyTaskPersisterLocked(task, false);
    }

    private int findIndexToAdd(com.android.server.wm.Task task) {
        com.android.server.wm.Task task2;
        int i = 0;
        for (int i2 = 0; i2 < this.mTasks.size() && task != (task2 = this.mTasks.get(i2)) && task2.isAttached(); i2++) {
            if (!task2.inPinnedWindowingMode() && task2.topRunningActivity() != null) {
                if (task.compareTo((com.android.server.wm.WindowContainer) task2) > 0) {
                    break;
                }
                i = i2 + 1;
            }
        }
        return i;
    }

    boolean addToBottom(com.android.server.wm.Task task) {
        if (!canAddTaskWithoutTrim(task)) {
            return false;
        }
        add(task);
        return true;
    }

    void remove(com.android.server.wm.Task task) {
        this.mTasks.remove(task);
        notifyTaskRemoved(task, false, false);
    }

    void onActivityIdle(com.android.server.wm.ActivityRecord activityRecord) {
        if (!this.mHiddenTasks.isEmpty() && activityRecord.isActivityTypeHome() && activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED)) {
            removeUnreachableHiddenTasks(activityRecord.getWindowingMode());
        }
        if (this.mCheckTrimmableTasksOnIdle) {
            this.mCheckTrimmableTasksOnIdle = false;
            trimInactiveRecentTasks();
        }
    }

    private void trimInactiveRecentTasks() {
        if (this.mFreezeTaskListReordering) {
            return;
        }
        for (int size = this.mTasks.size(); size > this.mGlobalMaxNumTasks; size--) {
            notifyTaskRemoved(this.mTasks.remove(size - 1), true, false);
        }
        int[] currentProfileIds = getCurrentProfileIds();
        this.mTmpQuietProfileUserIds.clear();
        for (int i : currentProfileIds) {
            android.content.pm.UserInfo userInfo = getUserInfo(i);
            if (userInfo != null && userInfo.isManagedProfile() && userInfo.isQuietModeEnabled()) {
                this.mTmpQuietProfileUserIds.put(i, true);
            }
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.mTasks.size()) {
            com.android.server.wm.Task task = this.mTasks.get(i2);
            if (isActiveRecentTask(task, this.mTmpQuietProfileUserIds)) {
                if (!this.mHasVisibleRecentTasks) {
                    i2++;
                } else if (!isVisibleRecentTask(task)) {
                    i2++;
                } else {
                    i3++;
                    if (isInVisibleRange(task, i2, i3, false) || !isTrimmable(task)) {
                        i2++;
                    }
                }
            }
            this.mTasks.remove(task);
            notifyTaskRemoved(task, true, false);
            notifyTaskPersisterLocked(task, false);
        }
    }

    private boolean isActiveRecentTask(com.android.server.wm.Task task, android.util.SparseBooleanArray sparseBooleanArray) {
        com.android.server.wm.Task task2;
        if (sparseBooleanArray.get(task.mUserId)) {
            return false;
        }
        return task.mAffiliatedTaskId == -1 || task.mAffiliatedTaskId == task.mTaskId || (task2 = getTask(task.mAffiliatedTaskId)) == null || isActiveRecentTask(task2, sparseBooleanArray);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isVisibleRecentTask(com.android.server.wm.Task task) {
        switch (task.getActivityType()) {
            case 2:
            case 3:
            case 5:
                return false;
            case 4:
                if ((task.getBaseIntent().getFlags() & 8388608) == 8388608) {
                    return false;
                }
                break;
        }
        switch (task.getWindowingMode()) {
            case 2:
                return false;
            case 6:
                if (task.isAlwaysOnTopWhenVisible()) {
                    return false;
                }
                break;
        }
        if (task == this.mService.getLockTaskController().getRootTask()) {
            return false;
        }
        return task.getDisplayContent() == null || task.getDisplayContent().canShowTasksInHostDeviceRecents();
    }

    private boolean isInVisibleRange(com.android.server.wm.Task task, int i, int i2, boolean z) {
        if (!z) {
            if ((task.getBaseIntent().getFlags() & 8388608) == 8388608) {
                return task.isOnHomeDisplay() && i == 0;
            }
        }
        if ((this.mMinNumVisibleTasks < 0 || i2 > this.mMinNumVisibleTasks) && task.mChildPipActivity == null) {
            return this.mMaxNumVisibleTasks >= 0 ? i2 <= this.mMaxNumVisibleTasks : this.mActiveTasksSessionDurationMs > 0 && task.getInactiveDuration() <= this.mActiveTasksSessionDurationMs;
        }
        return true;
    }

    protected boolean isTrimmable(com.android.server.wm.Task task) {
        com.android.server.wm.Task rootHomeTask;
        if (task.isAttached()) {
            return task.isOnHomeDisplay() && (rootHomeTask = task.getDisplayArea().getRootHomeTask()) != null && task.compareTo((com.android.server.wm.WindowContainer) rootHomeTask) < 0;
        }
        return true;
    }

    private void removeUnreachableHiddenTasks(int i) {
        int size = this.mHiddenTasks.size();
        if (size <= 10) {
            return;
        }
        for (int i2 = size - 1; i2 >= 10; i2--) {
            com.android.server.wm.Task task = this.mHiddenTasks.get(i2);
            if (!task.hasChild() || task.inRecents) {
                this.mHiddenTasks.remove(i2);
            } else if (task.getWindowingMode() == i && task.getTopVisibleActivity() == null) {
                this.mHiddenTasks.remove(i2);
                this.mSupervisor.removeTask(task, false, false, "remove-hidden-task");
            }
        }
    }

    private int removeForAddTask(com.android.server.wm.Task task) {
        this.mHiddenTasks.remove(task);
        int findRemoveIndexForAddTask = findRemoveIndexForAddTask(task);
        if (findRemoveIndexForAddTask == -1) {
            return findRemoveIndexForAddTask;
        }
        com.android.server.wm.Task remove = this.mTasks.remove(findRemoveIndexForAddTask);
        if (remove != task) {
            if (remove.hasChild() && !remove.isActivityTypeHome()) {
                android.util.Slog.i("ActivityTaskManager", "Add " + remove + " to hidden list because adding " + task);
                this.mHiddenTasks.add(0, remove);
            }
            notifyTaskRemoved(remove, false, false);
        }
        notifyTaskPersisterLocked(remove, false);
        return findRemoveIndexForAddTask;
    }

    private int findRemoveIndexForAddTask(com.android.server.wm.Task task) {
        return findRemoveIndexForTask(task, true);
    }

    private int findRemoveIndexForTask(com.android.server.wm.Task task, boolean z) {
        boolean z2;
        int size = this.mTasks.size();
        android.content.Intent intent = task.intent;
        boolean z3 = intent != null && intent.isDocument();
        int i = task.maxRecents - 1;
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.wm.Task task2 = this.mTasks.get(i2);
            if (task != task2) {
                if (hasCompatibleActivityTypeAndWindowingMode(task, task2) && task.mUserId == task2.mUserId) {
                    android.content.Intent intent2 = task2.intent;
                    boolean z4 = task.affinity != null && task.affinity.equals(task2.affinity);
                    boolean z5 = intent != null && intent.filterEquals(intent2);
                    int flags = intent != null ? intent.getFlags() : 0;
                    if ((268959744 & flags) != 0 && (flags & 134217728) != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    boolean z6 = intent2 != null && intent2.isDocument();
                    boolean z7 = z3 && z6;
                    if (z4 || z5 || z7) {
                        if (z7) {
                            if ((task.realActivity == null || task2.realActivity == null || !task.realActivity.equals(task2.realActivity)) ? false : true) {
                                if (i > 0) {
                                    i--;
                                    if (z5 && !z2) {
                                    }
                                }
                                return i2;
                            }
                            continue;
                        } else if (!z3 && !z6 && !z2) {
                            return i2;
                        }
                    }
                }
            } else {
                if (z) {
                    return i2;
                }
            }
        }
        return -1;
    }

    private int processNextAffiliateChainLocked(int i) {
        int i2;
        com.android.server.wm.Task task = this.mTasks.get(i);
        int i3 = task.mAffiliatedTaskId;
        if (task.mTaskId == i3 && task.mPrevAffiliate == null && task.mNextAffiliate == null) {
            task.inRecents = true;
            return i + 1;
        }
        this.mTmpRecents.clear();
        for (int size = this.mTasks.size() - 1; size >= i; size--) {
            com.android.server.wm.Task task2 = this.mTasks.get(size);
            if (task2.mAffiliatedTaskId == i3) {
                this.mTasks.remove(size);
                this.mTmpRecents.add(task2);
            }
        }
        java.util.Collections.sort(this.mTmpRecents, TASK_ID_COMPARATOR);
        com.android.server.wm.Task task3 = this.mTmpRecents.get(0);
        task3.inRecents = true;
        if (task3.mNextAffiliate != null) {
            android.util.Slog.w("ActivityTaskManager", "Link error 1 first.next=" + task3.mNextAffiliate);
            task3.setNextAffiliate(null);
            notifyTaskPersisterLocked(task3, false);
        }
        int size2 = this.mTmpRecents.size();
        int i4 = 0;
        while (true) {
            i2 = size2 - 1;
            if (i4 >= i2) {
                break;
            }
            com.android.server.wm.Task task4 = this.mTmpRecents.get(i4);
            i4++;
            com.android.server.wm.Task task5 = this.mTmpRecents.get(i4);
            if (task4.mPrevAffiliate != task5) {
                android.util.Slog.w("ActivityTaskManager", "Link error 2 next=" + task4 + " prev=" + task4.mPrevAffiliate + " setting prev=" + task5);
                task4.setPrevAffiliate(task5);
                notifyTaskPersisterLocked(task4, false);
            }
            if (task5.mNextAffiliate != task4) {
                android.util.Slog.w("ActivityTaskManager", "Link error 3 prev=" + task5 + " next=" + task5.mNextAffiliate + " setting next=" + task4);
                task5.setNextAffiliate(task4);
                notifyTaskPersisterLocked(task5, false);
            }
            task5.inRecents = true;
        }
        com.android.server.wm.Task task6 = this.mTmpRecents.get(i2);
        if (task6.mPrevAffiliate != null) {
            android.util.Slog.w("ActivityTaskManager", "Link error 4 last.prev=" + task6.mPrevAffiliate);
            task6.setPrevAffiliate(null);
            notifyTaskPersisterLocked(task6, false);
        }
        this.mTasks.addAll(i, this.mTmpRecents);
        this.mTmpRecents.clear();
        return i + size2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x0128, code lost:
    
        android.util.Slog.wtf("ActivityTaskManager", "Bad chain @" + r7 + ": middle task " + r14 + " @" + r7 + " has bad next affiliate " + r14.mNextAffiliate + " id " + r14.mNextAffiliateTaskId + ", expected " + r10);
        r6 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0073 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean moveAffiliatedTasksToFront(com.android.server.wm.Task task, int i) {
        int size = this.mTasks.size();
        com.android.server.wm.Task task2 = task;
        int i2 = i;
        while (task2.mNextAffiliate != null && i2 > 0) {
            task2 = task2.mNextAffiliate;
            i2--;
        }
        boolean z = task2.mAffiliatedTaskId == task.mAffiliatedTaskId;
        com.android.server.wm.Task task3 = task2;
        int i3 = i2;
        while (true) {
            if (i3 >= size) {
                break;
            }
            com.android.server.wm.Task task4 = this.mTasks.get(i3);
            if (task4 == task2) {
                if (task4.mNextAffiliate != null || task4.mNextAffiliateTaskId != -1) {
                    break;
                }
                if (task4.mPrevAffiliateTaskId != -1) {
                    if (task4.mPrevAffiliate != null) {
                        android.util.Slog.wtf("ActivityTaskManager", "Bad chain @" + i3 + ": last task " + task4 + " has previous affiliate " + task4.mPrevAffiliate);
                        z = false;
                    }
                } else {
                    if (task4.mPrevAffiliate == null) {
                        android.util.Slog.wtf("ActivityTaskManager", "Bad chain @" + i3 + ": task " + task4 + " has previous affiliate " + task4.mPrevAffiliate + " but should be id " + task4.mPrevAffiliate);
                        z = false;
                        break;
                    }
                    if (task4.mAffiliatedTaskId != task.mAffiliatedTaskId) {
                        android.util.Slog.wtf("ActivityTaskManager", "Bad chain @" + i3 + ": task " + task4 + " has affiliated id " + task4.mAffiliatedTaskId + " but should be " + task.mAffiliatedTaskId);
                        z = false;
                        break;
                    }
                    i3++;
                    if (i3 >= size) {
                        android.util.Slog.wtf("ActivityTaskManager", "Bad chain ran off index " + i3 + ": last task " + task4);
                        z = false;
                        break;
                    }
                    task3 = task4;
                }
            } else {
                if (task4.mNextAffiliate != task3 || task4.mNextAffiliateTaskId != task3.mTaskId) {
                    break;
                }
                if (task4.mPrevAffiliateTaskId != -1) {
                }
            }
        }
        android.util.Slog.wtf("ActivityTaskManager", "Bad chain @" + i3 + ": first task has next affiliate: " + task3);
        z = false;
        if (z && i3 < i) {
            android.util.Slog.wtf("ActivityTaskManager", "Bad chain @" + i3 + ": did not extend to task " + task + " @" + i);
            z = false;
        }
        if (z) {
            for (int i4 = i2; i4 <= i3; i4++) {
                this.mTasks.add(i4 - i2, this.mTasks.remove(i4));
            }
            return true;
        }
        return false;
    }

    void dump(java.io.PrintWriter printWriter, boolean z, java.lang.String str) {
        int i;
        int i2;
        printWriter.println("ACTIVITY MANAGER RECENT TASKS (dumpsys activity recents)");
        printWriter.println("mRecentsUid=" + this.mRecentsUid);
        printWriter.println("mRecentsComponent=" + this.mRecentsComponent);
        printWriter.println("mFreezeTaskListReordering=" + this.mFreezeTaskListReordering);
        printWriter.println("mFreezeTaskListReorderingPendingTimeout=" + this.mService.mH.hasCallbacks(this.mResetFreezeTaskListOnTimeoutRunnable));
        if (!this.mHiddenTasks.isEmpty()) {
            printWriter.println("mHiddenTasks=" + this.mHiddenTasks);
        }
        if (this.mTasks.isEmpty()) {
            return;
        }
        int size = this.mTasks.size();
        boolean z2 = false;
        boolean z3 = false;
        for (0; i < size; i + 1) {
            com.android.server.wm.Task task = this.mTasks.get(i);
            if (str != null) {
                boolean z4 = (task.intent == null || task.intent.getComponent() == null || !str.equals(task.intent.getComponent().getPackageName())) ? false : true;
                if (!z4) {
                    z4 |= (task.affinityIntent == null || task.affinityIntent.getComponent() == null || !str.equals(task.affinityIntent.getComponent().getPackageName())) ? false : true;
                }
                if (!z4) {
                    z4 |= task.origActivity != null && str.equals(task.origActivity.getPackageName());
                }
                if (!z4) {
                    z4 |= task.realActivity != null && str.equals(task.realActivity.getPackageName());
                }
                if (!z4) {
                    z4 |= str.equals(task.mCallingPackage);
                }
                i = z4 ? 0 : i + 1;
            }
            if (!z2) {
                printWriter.println("  Recent tasks:");
                z2 = true;
                z3 = true;
            }
            printWriter.print("  * Recent #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(task);
            if (z) {
                task.dump(printWriter, "    ");
            }
        }
        if (this.mHasVisibleRecentTasks) {
            java.util.ArrayList<android.app.ActivityManager.RecentTaskInfo> recentTasksImpl = getRecentTasksImpl(Integer.MAX_VALUE, 0, true, this.mService.getCurrentUserId(), 1000);
            boolean z5 = false;
            for (0; i2 < recentTasksImpl.size(); i2 + 1) {
                android.app.ActivityManager.RecentTaskInfo recentTaskInfo = recentTasksImpl.get(i2);
                if (str != null) {
                    boolean z6 = (recentTaskInfo.baseIntent == null || recentTaskInfo.baseIntent.getComponent() == null || !str.equals(recentTaskInfo.baseIntent.getComponent().getPackageName())) ? false : true;
                    if (!z6) {
                        z6 |= recentTaskInfo.baseActivity != null && str.equals(recentTaskInfo.baseActivity.getPackageName());
                    }
                    if (!z6) {
                        z6 |= recentTaskInfo.topActivity != null && str.equals(recentTaskInfo.topActivity.getPackageName());
                    }
                    if (!z6) {
                        z6 |= recentTaskInfo.origActivity != null && str.equals(recentTaskInfo.origActivity.getPackageName());
                    }
                    if (!z6) {
                        z6 |= recentTaskInfo.realActivity != null && str.equals(recentTaskInfo.realActivity.getPackageName());
                    }
                    i2 = z6 ? 0 : i2 + 1;
                }
                if (!z5) {
                    if (z3) {
                        printWriter.println();
                    }
                    printWriter.println("  Visible recent tasks (most recent first):");
                    z5 = true;
                    z3 = true;
                }
                printWriter.print("  * RecentTaskInfo #");
                printWriter.print(i2);
                printWriter.print(": ");
                recentTaskInfo.dump(printWriter, "    ");
            }
        }
        if (!z3) {
            printWriter.println("  (nothing)");
        }
    }

    android.app.ActivityManager.RecentTaskInfo createRecentTaskInfo(com.android.server.wm.Task task, boolean z, boolean z2) {
        com.android.server.wm.TaskDisplayArea defaultTaskDisplayArea;
        android.app.ActivityManager.RecentTaskInfo recentTaskInfo = new android.app.ActivityManager.RecentTaskInfo();
        if (task.isAttached()) {
            defaultTaskDisplayArea = task.getDisplayArea();
        } else {
            defaultTaskDisplayArea = this.mService.mRootWindowContainer.getDefaultTaskDisplayArea();
        }
        task.fillTaskInfo(recentTaskInfo, z, defaultTaskDisplayArea);
        recentTaskInfo.id = recentTaskInfo.isRunning ? recentTaskInfo.taskId : -1;
        recentTaskInfo.persistentId = recentTaskInfo.taskId;
        recentTaskInfo.lastSnapshotData.set(task.mLastTaskSnapshotData);
        if (!z2) {
            com.android.server.wm.Task.trimIneffectiveInfo(task, recentTaskInfo);
        }
        if (task.mCreatedByOrganizer) {
            for (int childCount = task.getChildCount() - 1; childCount >= 0; childCount--) {
                com.android.server.wm.Task asTask = task.getChildAt(childCount).asTask();
                if (asTask != null && asTask.isOrganized()) {
                    android.app.ActivityManager.RecentTaskInfo recentTaskInfo2 = new android.app.ActivityManager.RecentTaskInfo();
                    asTask.fillTaskInfo(recentTaskInfo2, true, defaultTaskDisplayArea);
                    recentTaskInfo.childrenTaskInfos.add(recentTaskInfo2);
                }
            }
        }
        return recentTaskInfo;
    }

    private boolean hasCompatibleActivityTypeAndWindowingMode(com.android.server.wm.Task task, com.android.server.wm.Task task2) {
        int activityType = task.getActivityType();
        int windowingMode = task.getWindowingMode();
        boolean z = activityType == 0;
        boolean z2 = windowingMode == 0;
        int activityType2 = task2.getActivityType();
        int windowingMode2 = task2.getWindowingMode();
        return (activityType == activityType2 || z || (activityType2 == 0)) && (windowingMode == windowingMode2 || z2 || (windowingMode2 == 0));
    }
}
