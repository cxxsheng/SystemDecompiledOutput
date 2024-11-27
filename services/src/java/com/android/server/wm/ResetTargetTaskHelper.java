package com.android.server.wm;

/* loaded from: classes3.dex */
class ResetTargetTaskHelper implements java.util.function.Consumer<com.android.server.wm.Task>, java.util.function.Predicate<com.android.server.wm.ActivityRecord> {
    private int mActivityReparentPosition;
    private boolean mCanMoveOptions;
    private boolean mForceReset;
    private boolean mIsTargetTask;
    private com.android.server.wm.ActivityRecord mRoot;
    private com.android.server.wm.Task mTargetRootTask;
    private com.android.server.wm.Task mTargetTask;
    private boolean mTargetTaskFound;
    private com.android.server.wm.Task mTask;
    private android.app.ActivityOptions mTopOptions;
    private java.util.ArrayList<com.android.server.wm.ActivityRecord> mResultActivities = new java.util.ArrayList<>();
    private java.util.ArrayList<com.android.server.wm.ActivityRecord> mAllActivities = new java.util.ArrayList<>();
    private java.util.ArrayList<com.android.server.wm.ActivityRecord> mPendingReparentActivities = new java.util.ArrayList<>();

    ResetTargetTaskHelper() {
    }

    private void reset(com.android.server.wm.Task task) {
        this.mTask = task;
        this.mRoot = null;
        this.mCanMoveOptions = true;
        this.mTopOptions = null;
        this.mResultActivities.clear();
        this.mAllActivities.clear();
    }

    android.app.ActivityOptions process(com.android.server.wm.Task task, boolean z) {
        this.mForceReset = z;
        this.mTargetTask = task;
        this.mTargetTaskFound = false;
        this.mTargetRootTask = task.getRootTask();
        this.mActivityReparentPosition = -1;
        task.mWmService.mRoot.forAllLeafTasks(this, true);
        processPendingReparentActivities();
        reset(null);
        return this.mTopOptions;
    }

    @Override // java.util.function.Consumer
    public void accept(com.android.server.wm.Task task) {
        reset(task);
        this.mRoot = task.getRootActivity(true);
        if (this.mRoot == null) {
            return;
        }
        this.mIsTargetTask = task == this.mTargetTask;
        if (this.mIsTargetTask) {
            this.mTargetTaskFound = true;
        }
        task.forAllActivities((java.util.function.Predicate<com.android.server.wm.ActivityRecord>) this);
    }

    @Override // java.util.function.Predicate
    public boolean test(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.ActivityRecord activityBelow;
        if (activityRecord == this.mRoot) {
            return true;
        }
        this.mAllActivities.add(activityRecord);
        int i = activityRecord.info.flags;
        boolean z = (i & 2) != 0;
        boolean z2 = (i & 64) != 0;
        boolean z3 = (activityRecord.intent.getFlags() & 524288) != 0;
        if (this.mIsTargetTask) {
            if (!z && !z3) {
                if (activityRecord.resultTo != null) {
                    this.mResultActivities.add(activityRecord);
                    return false;
                }
                if (z2 && activityRecord.taskAffinity != null && !activityRecord.taskAffinity.equals(this.mTask.affinity)) {
                    this.mPendingReparentActivities.add(activityRecord);
                    return false;
                }
            }
            if (this.mForceReset || z || z3) {
                if (z3) {
                    finishActivities(this.mAllActivities, "clearWhenTaskReset");
                } else {
                    this.mResultActivities.add(activityRecord);
                    finishActivities(this.mResultActivities, "reset-task");
                }
                this.mResultActivities.clear();
                return false;
            }
            this.mResultActivities.clear();
            return false;
        }
        if (activityRecord.resultTo != null) {
            this.mResultActivities.add(activityRecord);
            return false;
        }
        if (this.mTargetTaskFound && z2 && this.mTargetTask.affinity != null && this.mTargetTask.affinity.equals(activityRecord.taskAffinity)) {
            this.mResultActivities.add(activityRecord);
            if (this.mForceReset || z) {
                finishActivities(this.mResultActivities, "move-affinity");
                return false;
            }
            if (this.mActivityReparentPosition == -1) {
                this.mActivityReparentPosition = this.mTargetTask.getChildCount();
            }
            processResultActivities(activityRecord, this.mTargetTask, this.mActivityReparentPosition, false, false);
            if (activityRecord.info.launchMode == 1 && (activityBelow = this.mTargetTask.getActivityBelow(activityRecord)) != null && activityBelow.intent.getComponent().equals(activityRecord.intent.getComponent())) {
                activityBelow.finishIfPossible("replace", false);
            }
        }
        return false;
    }

    private void finishActivities(java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList, java.lang.String str) {
        boolean z = this.mCanMoveOptions;
        while (!arrayList.isEmpty()) {
            com.android.server.wm.ActivityRecord remove = arrayList.remove(0);
            if (!remove.finishing) {
                z = takeOption(remove, z);
                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, -4617490621756721600L, 0, null, java.lang.String.valueOf(remove));
                remove.finishIfPossible(str, false);
            }
        }
    }

    private void processResultActivities(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.Task task, int i, boolean z, boolean z2) {
        boolean z3 = this.mCanMoveOptions;
        while (!this.mResultActivities.isEmpty()) {
            com.android.server.wm.ActivityRecord remove = this.mResultActivities.remove(0);
            if (!z || !remove.finishing) {
                if (z2) {
                    z3 = takeOption(remove, z3);
                }
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 3361857745281957526L, 0, null, java.lang.String.valueOf(remove), java.lang.String.valueOf(this.mTask), java.lang.String.valueOf(task), java.lang.String.valueOf(android.os.Debug.getCallers(4)));
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 3958829063955690349L, 0, null, java.lang.String.valueOf(remove), java.lang.String.valueOf(activityRecord));
                remove.reparent(task, i, "resetTargetTaskIfNeeded");
            }
        }
    }

    private void processPendingReparentActivities() {
        com.android.server.wm.Task bottomMostTask;
        if (this.mPendingReparentActivities.isEmpty()) {
            return;
        }
        com.android.server.wm.ActivityTaskManagerService activityTaskManagerService = this.mTargetRootTask.mAtmService;
        com.android.server.wm.TaskDisplayArea displayArea = this.mTargetRootTask.getDisplayArea();
        int windowingMode = this.mTargetRootTask.getWindowingMode();
        int activityType = this.mTargetRootTask.getActivityType();
        while (!this.mPendingReparentActivities.isEmpty()) {
            com.android.server.wm.ActivityRecord remove = this.mPendingReparentActivities.remove(0);
            boolean alwaysCreateRootTask = com.android.server.wm.DisplayContent.alwaysCreateRootTask(windowingMode, activityType);
            if (!alwaysCreateRootTask) {
                bottomMostTask = this.mTargetRootTask.getBottomMostTask();
            } else {
                bottomMostTask = displayArea.getBottomMostTask();
            }
            if (bottomMostTask != null && remove.taskAffinity.equals(bottomMostTask.affinity)) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TASKS, 1730793580703791926L, 0, null, java.lang.String.valueOf(remove), java.lang.String.valueOf(bottomMostTask));
            } else {
                bottomMostTask = null;
            }
            if (bottomMostTask == null) {
                if (!alwaysCreateRootTask) {
                    bottomMostTask = this.mTargetRootTask.reuseOrCreateTask(remove.info, null, false);
                } else {
                    bottomMostTask = displayArea.getOrCreateRootTask(windowingMode, activityType, false);
                }
                bottomMostTask.affinityIntent = remove.intent;
            }
            remove.reparent(bottomMostTask, 0, "resetTargetTaskIfNeeded");
            activityTaskManagerService.mTaskSupervisor.mRecentTasks.add(bottomMostTask);
        }
    }

    private boolean takeOption(com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        this.mCanMoveOptions = false;
        if (z && this.mTopOptions == null) {
            this.mTopOptions = activityRecord.getOptions();
            if (this.mTopOptions != null) {
                activityRecord.clearOptionsAnimation();
                return false;
            }
            return z;
        }
        return z;
    }
}
