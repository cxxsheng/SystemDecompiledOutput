package com.android.server.wm;

/* loaded from: classes3.dex */
class SnapshotController {
    final com.android.server.wm.ActivitySnapshotController mActivitySnapshotController;
    private final com.android.server.wm.SnapshotPersistQueue mSnapshotPersistQueue = new com.android.server.wm.SnapshotPersistQueue();
    final com.android.server.wm.TaskSnapshotController mTaskSnapshotController;

    SnapshotController(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mTaskSnapshotController = new com.android.server.wm.TaskSnapshotController(windowManagerService, this.mSnapshotPersistQueue);
        this.mActivitySnapshotController = new com.android.server.wm.ActivitySnapshotController(windowManagerService, this.mSnapshotPersistQueue);
    }

    void systemReady() {
        this.mSnapshotPersistQueue.systemReady();
    }

    void setPause(boolean z) {
        this.mSnapshotPersistQueue.setPaused(z);
    }

    void onAppRemoved(com.android.server.wm.ActivityRecord activityRecord) {
        this.mTaskSnapshotController.onAppRemoved(activityRecord);
        this.mActivitySnapshotController.onAppRemoved(activityRecord);
    }

    void onAppDied(com.android.server.wm.ActivityRecord activityRecord) {
        this.mTaskSnapshotController.onAppDied(activityRecord);
        this.mActivitySnapshotController.onAppDied(activityRecord);
    }

    void notifyAppVisibilityChanged(com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        this.mActivitySnapshotController.notifyAppVisibilityChanged(activityRecord, z);
    }

    void onTransitionStarting(com.android.server.wm.DisplayContent displayContent) {
        this.mTaskSnapshotController.handleClosingApps(displayContent.mClosingApps);
    }

    void onTransactionReady(int i, java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList) {
        boolean isTransitionOpen = isTransitionOpen(i);
        boolean isTransitionClose = isTransitionClose(i);
        if (!isTransitionOpen && !isTransitionClose && i < 1000) {
            return;
        }
        com.android.server.wm.SnapshotController.ActivitiesByTask activitiesByTask = null;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.wm.Transition.ChangeInfo changeInfo = arrayList.get(size);
            if (changeInfo.mWindowingMode != 2 && !changeInfo.mContainer.isActivityTypeHome()) {
                com.android.server.wm.Task asTask = changeInfo.mContainer.asTask();
                if (asTask != null && !asTask.mCreatedByOrganizer && !asTask.isVisibleRequested()) {
                    this.mTaskSnapshotController.recordSnapshot(asTask, changeInfo);
                }
                if (!isTransitionClose && (changeInfo.mContainer.asActivityRecord() != null || changeInfo.mContainer.asTaskFragment() != null)) {
                    com.android.server.wm.TaskFragment asTaskFragment = changeInfo.mContainer.asTaskFragment();
                    com.android.server.wm.ActivityRecord topMostActivity = asTaskFragment != null ? asTaskFragment.getTopMostActivity() : changeInfo.mContainer.asActivityRecord();
                    if (topMostActivity != null && topMostActivity.getTask().isVisibleRequested()) {
                        if (activitiesByTask == null) {
                            activitiesByTask = new com.android.server.wm.SnapshotController.ActivitiesByTask();
                        }
                        activitiesByTask.put(topMostActivity);
                    }
                }
            }
        }
        if (activitiesByTask != null) {
            activitiesByTask.recordSnapshot(this.mActivitySnapshotController);
        }
    }

    private static class ActivitiesByTask {
        final android.util.ArrayMap<com.android.server.wm.Task, com.android.server.wm.SnapshotController.ActivitiesByTask.OpenCloseActivities> mActivitiesMap;

        private ActivitiesByTask() {
            this.mActivitiesMap = new android.util.ArrayMap<>();
        }

        void put(com.android.server.wm.ActivityRecord activityRecord) {
            com.android.server.wm.SnapshotController.ActivitiesByTask.OpenCloseActivities openCloseActivities = this.mActivitiesMap.get(activityRecord.getTask());
            if (openCloseActivities == null) {
                openCloseActivities = new com.android.server.wm.SnapshotController.ActivitiesByTask.OpenCloseActivities();
                this.mActivitiesMap.put(activityRecord.getTask(), openCloseActivities);
            }
            openCloseActivities.add(activityRecord);
        }

        void recordSnapshot(com.android.server.wm.ActivitySnapshotController activitySnapshotController) {
            for (int size = this.mActivitiesMap.size() - 1; size >= 0; size--) {
                this.mActivitiesMap.valueAt(size).recordSnapshot(activitySnapshotController);
            }
        }

        static class OpenCloseActivities {
            final java.util.ArrayList<com.android.server.wm.ActivityRecord> mOpenActivities = new java.util.ArrayList<>();
            final java.util.ArrayList<com.android.server.wm.ActivityRecord> mCloseActivities = new java.util.ArrayList<>();

            OpenCloseActivities() {
            }

            void add(com.android.server.wm.ActivityRecord activityRecord) {
                if (activityRecord.isVisibleRequested()) {
                    this.mOpenActivities.add(activityRecord);
                } else {
                    this.mCloseActivities.add(activityRecord);
                }
            }

            boolean allOpensOptInOnBackInvoked() {
                if (this.mOpenActivities.isEmpty()) {
                    return false;
                }
                for (int size = this.mOpenActivities.size() - 1; size >= 0; size--) {
                    if (!this.mOpenActivities.get(size).mOptInOnBackInvoked) {
                        return false;
                    }
                }
                return true;
            }

            void recordSnapshot(com.android.server.wm.ActivitySnapshotController activitySnapshotController) {
                if (!allOpensOptInOnBackInvoked() || this.mCloseActivities.isEmpty()) {
                    return;
                }
                activitySnapshotController.recordSnapshot(this.mCloseActivities);
            }
        }
    }

    void onTransitionFinish(int i, java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList) {
        boolean isTransitionOpen = isTransitionOpen(i);
        boolean isTransitionClose = isTransitionClose(i);
        if ((!isTransitionOpen && !isTransitionClose && i < 1000) || arrayList.isEmpty()) {
            return;
        }
        android.os.Trace.traceBegin(32L, "SnapshotController_analysis");
        this.mActivitySnapshotController.beginSnapshotProcess();
        java.util.ArrayList<com.android.server.wm.WindowContainer> arrayList2 = new java.util.ArrayList<>();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer windowContainer = arrayList.get(size).mContainer;
            if (windowContainer.asTask() != null || windowContainer.asTaskFragment() != null || windowContainer.asActivityRecord() != null) {
                arrayList2.add(windowContainer);
            }
        }
        this.mActivitySnapshotController.handleTransitionFinish(arrayList2);
        this.mActivitySnapshotController.endSnapshotProcess();
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            com.android.server.wm.WindowContainer windowContainer2 = arrayList.get(size2).mContainer;
            com.android.server.wm.Task asTask = windowContainer2.asTask();
            if (asTask != null && windowContainer2.isVisibleRequested() && this.mTaskSnapshotController.getSnapshot(asTask.mTaskId, asTask.mUserId, false, false) != null) {
                this.mTaskSnapshotController.removeAndDeleteSnapshot(asTask.mTaskId, asTask.mUserId);
            }
        }
        android.os.Trace.traceEnd(32L);
    }

    private static boolean isTransitionOpen(int i) {
        return i == 1 || i == 3;
    }

    private static boolean isTransitionClose(int i) {
        return i == 2 || i == 4;
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        this.mTaskSnapshotController.dump(printWriter, str);
        this.mActivitySnapshotController.dump(printWriter, str);
        this.mSnapshotPersistQueue.dump(printWriter, str);
    }
}
