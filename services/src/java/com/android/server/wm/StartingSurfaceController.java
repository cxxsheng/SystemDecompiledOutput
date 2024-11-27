package com.android.server.wm;

/* loaded from: classes3.dex */
public class StartingSurfaceController {
    private static final long ALLOW_COPY_SOLID_COLOR_VIEW = 205907456;
    private static final java.lang.String TAG = "WindowManager";
    private final java.util.ArrayList<com.android.server.wm.StartingSurfaceController.DeferringStartingWindowRecord> mDeferringAddStartActivities = new java.util.ArrayList<>();
    private boolean mDeferringAddStartingWindow;
    boolean mInitNewTask;
    boolean mInitProcessRunning;
    boolean mInitTaskSwitch;
    private final com.android.server.wm.WindowManagerService mService;
    private final com.android.server.wm.SplashScreenExceptionList mSplashScreenExceptionsList;

    public StartingSurfaceController(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mSplashScreenExceptionsList = new com.android.server.wm.SplashScreenExceptionList(windowManagerService.mContext.getMainExecutor());
    }

    com.android.server.wm.StartingSurfaceController.StartingSurface createSplashScreenStartingSurface(com.android.server.wm.ActivityRecord activityRecord, int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task task = activityRecord.getTask();
                com.android.server.wm.TaskOrganizerController taskOrganizerController = this.mService.mAtmService.mTaskOrganizerController;
                if (task == null || !taskOrganizerController.addStartingWindow(task, activityRecord, i, null)) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                com.android.server.wm.StartingSurfaceController.StartingSurface startingSurface = new com.android.server.wm.StartingSurfaceController.StartingSurface(task, taskOrganizerController.getTaskOrganizer());
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return startingSurface;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    boolean isExceptionApp(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable java.util.function.Supplier<android.content.pm.ApplicationInfo> supplier) {
        return this.mSplashScreenExceptionsList.isException(str, i, supplier);
    }

    static int makeStartingWindowTypeParameter(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i, boolean z9, java.lang.String str, int i2) {
        int i3;
        if (!z) {
            i3 = 0;
        } else {
            i3 = 1;
        }
        if (z2) {
            i3 |= 2;
        }
        if (z3) {
            i3 |= 4;
        }
        if (z4) {
            i3 |= 8;
        }
        if (z5 || i == 1) {
            i3 |= 16;
        }
        if (z6) {
            i3 |= 32;
        }
        if (z7) {
            i3 |= Integer.MIN_VALUE;
        }
        if (z8) {
            i3 |= 64;
        }
        if (i == 2 && android.app.compat.CompatChanges.isChangeEnabled(ALLOW_COPY_SOLID_COLOR_VIEW, str, android.os.UserHandle.of(i2))) {
            i3 |= 128;
        }
        if (z9) {
            return i3 | 512;
        }
        return i3;
    }

    com.android.server.wm.StartingSurfaceController.StartingSurface createTaskSnapshotSurface(com.android.server.wm.ActivityRecord activityRecord, android.window.TaskSnapshot taskSnapshot) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.Task task = activityRecord.getTask();
                if (task == null) {
                    android.util.Slog.w(TAG, "TaskSnapshotSurface.create: Failed to find task for activity=" + activityRecord);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                com.android.server.wm.ActivityRecord topFullscreenActivity = activityRecord.getTask().getTopFullscreenActivity();
                if (topFullscreenActivity == null) {
                    android.util.Slog.w(TAG, "TaskSnapshotSurface.create: Failed to find top fullscreen for task=" + task);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                if (topFullscreenActivity.getTopFullscreenOpaqueWindow() == null) {
                    android.util.Slog.w(TAG, "TaskSnapshotSurface.create: no opaque window in " + topFullscreenActivity);
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                if (activityRecord.mDisplayContent.getRotation() != taskSnapshot.getRotation()) {
                    activityRecord.mDisplayContent.handleTopActivityLaunchingInDifferentOrientation(activityRecord, false);
                }
                com.android.server.wm.TaskOrganizerController taskOrganizerController = this.mService.mAtmService.mTaskOrganizerController;
                if (!taskOrganizerController.addStartingWindow(task, activityRecord, 0, taskSnapshot)) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                com.android.server.wm.StartingSurfaceController.StartingSurface startingSurface = new com.android.server.wm.StartingSurfaceController.StartingSurface(task, taskOrganizerController.getTaskOrganizer());
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return startingSurface;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private static final class DeferringStartingWindowRecord {
        final com.android.server.wm.ActivityRecord mDeferring;
        final com.android.server.wm.ActivityRecord mPrev;
        final com.android.server.wm.ActivityRecord mSource;

        DeferringStartingWindowRecord(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, com.android.server.wm.ActivityRecord activityRecord3) {
            this.mDeferring = activityRecord;
            this.mPrev = activityRecord2;
            this.mSource = activityRecord3;
        }
    }

    void showStartingWindow(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, boolean z, boolean z2, com.android.server.wm.ActivityRecord activityRecord3) {
        if (this.mDeferringAddStartingWindow) {
            addDeferringRecord(activityRecord, activityRecord2, z, z2, activityRecord3);
        } else {
            activityRecord.showStartingWindow(activityRecord2, z, z2, true, activityRecord3);
        }
    }

    private void addDeferringRecord(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, boolean z, boolean z2, com.android.server.wm.ActivityRecord activityRecord3) {
        if (this.mDeferringAddStartActivities.isEmpty()) {
            this.mInitProcessRunning = activityRecord.isProcessRunning();
            this.mInitNewTask = z;
            this.mInitTaskSwitch = z2;
        }
        this.mDeferringAddStartActivities.add(new com.android.server.wm.StartingSurfaceController.DeferringStartingWindowRecord(activityRecord, activityRecord2, activityRecord3));
    }

    private void showStartingWindowFromDeferringActivities(android.app.ActivityOptions activityOptions) {
        for (int size = this.mDeferringAddStartActivities.size() - 1; size >= 0; size--) {
            com.android.server.wm.StartingSurfaceController.DeferringStartingWindowRecord deferringStartingWindowRecord = this.mDeferringAddStartActivities.get(size);
            if (deferringStartingWindowRecord.mDeferring.getTask() == null) {
                android.util.Slog.e(TAG, "No task exists: " + deferringStartingWindowRecord.mDeferring.shortComponentName + " parent: " + deferringStartingWindowRecord.mDeferring.getParent());
            } else {
                deferringStartingWindowRecord.mDeferring.showStartingWindow(deferringStartingWindowRecord.mPrev, this.mInitNewTask, this.mInitTaskSwitch, this.mInitProcessRunning, true, deferringStartingWindowRecord.mSource, activityOptions);
                if (deferringStartingWindowRecord.mDeferring.mStartingData != null) {
                    break;
                }
            }
        }
        this.mDeferringAddStartActivities.clear();
    }

    void beginDeferAddStartingWindow() {
        this.mDeferringAddStartingWindow = true;
    }

    void endDeferAddStartingWindow(android.app.ActivityOptions activityOptions) {
        this.mDeferringAddStartingWindow = false;
        showStartingWindowFromDeferringActivities(activityOptions);
    }

    final class StartingSurface {
        private final com.android.server.wm.Task mTask;
        final android.window.ITaskOrganizer mTaskOrganizer;

        StartingSurface(com.android.server.wm.Task task, android.window.ITaskOrganizer iTaskOrganizer) {
            this.mTask = task;
            this.mTaskOrganizer = iTaskOrganizer;
        }

        public void remove(boolean z, boolean z2) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.StartingSurfaceController.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.StartingSurfaceController.this.mService.mAtmService.mTaskOrganizerController.removeStartingWindow(this.mTask, this.mTaskOrganizer, z, z2);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    }
}
