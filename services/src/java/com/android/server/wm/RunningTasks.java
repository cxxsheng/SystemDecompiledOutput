package com.android.server.wm;

/* loaded from: classes3.dex */
class RunningTasks implements java.util.function.Consumer<com.android.server.wm.Task> {
    static final int FLAG_ALLOWED = 2;
    static final int FLAG_CROSS_USERS = 4;
    static final int FLAG_FILTER_ONLY_VISIBLE_RECENTS = 1;
    static final int FLAG_KEEP_INTENT_EXTRA = 8;
    private boolean mAllowed;
    private int mCallingUid;
    private boolean mCrossUser;
    private boolean mFilterOnlyVisibleRecents;
    private boolean mKeepIntentExtra;
    private android.util.ArraySet<java.lang.Integer> mProfileIds;
    private com.android.server.wm.RecentTasks mRecentTasks;
    private int mUserId;
    private final java.util.ArrayList<com.android.server.wm.Task> mTmpSortedTasks = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.Task> mTmpVisibleTasks = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.Task> mTmpInvisibleTasks = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.Task> mTmpFocusedTasks = new java.util.ArrayList<>();

    RunningTasks() {
    }

    void getTasks(int i, java.util.List<android.app.ActivityManager.RunningTaskInfo> list, int i2, com.android.server.wm.RecentTasks recentTasks, com.android.server.wm.WindowContainer<?> windowContainer, int i3, android.util.ArraySet<java.lang.Integer> arraySet) {
        if (i <= 0) {
            return;
        }
        this.mCallingUid = i3;
        this.mUserId = android.os.UserHandle.getUserId(i3);
        int i4 = 0;
        this.mCrossUser = (i2 & 4) == 4;
        this.mProfileIds = arraySet;
        this.mAllowed = (i2 & 2) == 2;
        this.mFilterOnlyVisibleRecents = (i2 & 1) == 1;
        this.mRecentTasks = recentTasks;
        this.mKeepIntentExtra = (i2 & 8) == 8;
        if (windowContainer instanceof com.android.server.wm.RootWindowContainer) {
            ((com.android.server.wm.RootWindowContainer) windowContainer).forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.RunningTasks$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.RunningTasks.this.lambda$getTasks$0((com.android.server.wm.DisplayContent) obj);
                }
            });
        } else {
            com.android.server.wm.DisplayContent displayContent = windowContainer.getDisplayContent();
            com.android.server.wm.Task task = null;
            if (displayContent != null && displayContent.mFocusedApp != null) {
                task = displayContent.mFocusedApp.getTask();
            }
            if (task != null && task.isDescendantOf(windowContainer)) {
                this.mTmpFocusedTasks.add(task);
            }
            processTaskInWindowContainer(windowContainer);
        }
        int size = this.mTmpVisibleTasks.size();
        for (int i5 = 0; i5 < this.mTmpFocusedTasks.size(); i5++) {
            com.android.server.wm.Task task2 = this.mTmpFocusedTasks.get(i5);
            if (this.mTmpVisibleTasks.remove(task2)) {
                this.mTmpSortedTasks.add(task2);
            }
        }
        if (!this.mTmpVisibleTasks.isEmpty()) {
            this.mTmpSortedTasks.addAll(this.mTmpVisibleTasks);
        }
        if (!this.mTmpInvisibleTasks.isEmpty()) {
            this.mTmpSortedTasks.addAll(this.mTmpInvisibleTasks);
        }
        int min = java.lang.Math.min(i, this.mTmpSortedTasks.size());
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        while (i4 < min) {
            list.add(createRunningTaskInfo(this.mTmpSortedTasks.get(i4), i4 < size ? (min + elapsedRealtime) - i4 : -1L));
            i4++;
        }
        this.mTmpFocusedTasks.clear();
        this.mTmpVisibleTasks.clear();
        this.mTmpInvisibleTasks.clear();
        this.mTmpSortedTasks.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getTasks$0(com.android.server.wm.DisplayContent displayContent) {
        com.android.server.wm.Task task = displayContent.mFocusedApp != null ? displayContent.mFocusedApp.getTask() : null;
        if (task != null) {
            this.mTmpFocusedTasks.add(task);
        }
        processTaskInWindowContainer(displayContent);
    }

    private void processTaskInWindowContainer(com.android.server.wm.WindowContainer windowContainer) {
        windowContainer.forAllLeafTasks(this, true);
    }

    @Override // java.util.function.Consumer
    public void accept(com.android.server.wm.Task task) {
        if (task.getTopNonFinishingActivity() == null) {
            return;
        }
        if (task.effectiveUid != this.mCallingUid && ((task.mUserId != this.mUserId && !this.mCrossUser && !this.mProfileIds.contains(java.lang.Integer.valueOf(task.mUserId))) || !this.mAllowed)) {
            return;
        }
        if (this.mFilterOnlyVisibleRecents && task.getActivityType() != 2 && task.getActivityType() != 3 && !this.mRecentTasks.isVisibleRecentTask(task)) {
            return;
        }
        if (task.isVisible()) {
            this.mTmpVisibleTasks.add(task);
        } else {
            this.mTmpInvisibleTasks.add(task);
        }
    }

    private android.app.ActivityManager.RunningTaskInfo createRunningTaskInfo(com.android.server.wm.Task task, long j) {
        android.app.ActivityManager.RunningTaskInfo runningTaskInfo = new android.app.ActivityManager.RunningTaskInfo();
        task.fillTaskInfo(runningTaskInfo, !this.mKeepIntentExtra);
        if (j > 0) {
            runningTaskInfo.lastActiveTime = j;
        }
        runningTaskInfo.id = runningTaskInfo.taskId;
        if (!this.mAllowed) {
            com.android.server.wm.Task.trimIneffectiveInfo(task, runningTaskInfo);
        }
        return runningTaskInfo;
    }
}
