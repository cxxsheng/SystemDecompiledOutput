package com.android.server.wm;

/* loaded from: classes3.dex */
class LaunchParamsController {
    private final com.android.server.wm.LaunchParamsPersister mPersister;
    private final com.android.server.wm.ActivityTaskManagerService mService;
    private final java.util.List<com.android.server.wm.LaunchParamsController.LaunchParamsModifier> mModifiers = new java.util.ArrayList();
    private final com.android.server.wm.LaunchParamsController.LaunchParams mTmpParams = new com.android.server.wm.LaunchParamsController.LaunchParams();
    private final com.android.server.wm.LaunchParamsController.LaunchParams mTmpCurrent = new com.android.server.wm.LaunchParamsController.LaunchParams();
    private final com.android.server.wm.LaunchParamsController.LaunchParams mTmpResult = new com.android.server.wm.LaunchParamsController.LaunchParams();

    interface LaunchParamsModifier {
        public static final int PHASE_BOUNDS = 3;
        public static final int PHASE_DISPLAY = 0;
        public static final int PHASE_DISPLAY_AREA = 2;
        public static final int PHASE_WINDOWING_MODE = 1;
        public static final int RESULT_CONTINUE = 2;
        public static final int RESULT_DONE = 1;
        public static final int RESULT_SKIP = 0;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Phase {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Result {
        }

        int onCalculate(@android.annotation.Nullable com.android.server.wm.Task task, @android.annotation.Nullable android.content.pm.ActivityInfo.WindowLayout windowLayout, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord2, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.ActivityStarter.Request request, int i, com.android.server.wm.LaunchParamsController.LaunchParams launchParams, com.android.server.wm.LaunchParamsController.LaunchParams launchParams2);
    }

    LaunchParamsController(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService, com.android.server.wm.LaunchParamsPersister launchParamsPersister) {
        this.mService = activityTaskManagerService;
        this.mPersister = launchParamsPersister;
    }

    void registerDefaultModifiers(com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor) {
        registerModifier(new com.android.server.wm.TaskLaunchParamsModifier(activityTaskSupervisor));
        registerModifier(new com.android.server.wm.DesktopModeLaunchParamsModifier());
    }

    void calculate(com.android.server.wm.Task task, android.content.pm.ActivityInfo.WindowLayout windowLayout, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.ActivityStarter.Request request, int i, com.android.server.wm.LaunchParamsController.LaunchParams launchParams) {
        launchParams.reset();
        if (task != null || activityRecord != null) {
            this.mPersister.getLaunchParams(task, activityRecord, launchParams);
        }
        for (int size = this.mModifiers.size() - 1; size >= 0; size--) {
            this.mTmpCurrent.set(launchParams);
            this.mTmpResult.reset();
            switch (this.mModifiers.get(size).onCalculate(task, windowLayout, activityRecord, activityRecord2, activityOptions, request, i, this.mTmpCurrent, this.mTmpResult)) {
                case 1:
                    launchParams.set(this.mTmpResult);
                    return;
                case 2:
                    launchParams.set(this.mTmpResult);
                    break;
            }
        }
        if (activityRecord != null && activityRecord.requestedVrComponent != null) {
            launchParams.mPreferredTaskDisplayArea = this.mService.mRootWindowContainer.getDefaultTaskDisplayArea();
        } else if (this.mService.mVr2dDisplayId != -1) {
            launchParams.mPreferredTaskDisplayArea = this.mService.mRootWindowContainer.getDisplayContent(this.mService.mVr2dDisplayId).getDefaultTaskDisplayArea();
        }
    }

    boolean layoutTask(com.android.server.wm.Task task, android.content.pm.ActivityInfo.WindowLayout windowLayout) {
        return layoutTask(task, windowLayout, null, null, null);
    }

    boolean layoutTask(com.android.server.wm.Task task, android.content.pm.ActivityInfo.WindowLayout windowLayout, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, android.app.ActivityOptions activityOptions) {
        calculate(task, windowLayout, activityRecord, activityRecord2, activityOptions, null, 3, this.mTmpParams);
        if (this.mTmpParams.isEmpty()) {
            return false;
        }
        this.mService.deferWindowLayout();
        try {
            if (this.mTmpParams.mBounds.isEmpty()) {
                return false;
            }
            if (!task.getRootTask().inMultiWindowMode()) {
                task.setLastNonFullscreenBounds(this.mTmpParams.mBounds);
                return false;
            }
            task.setBounds(this.mTmpParams.mBounds);
            this.mService.continueWindowLayout();
            return true;
        } finally {
            this.mService.continueWindowLayout();
        }
    }

    void registerModifier(com.android.server.wm.LaunchParamsController.LaunchParamsModifier launchParamsModifier) {
        if (this.mModifiers.contains(launchParamsModifier)) {
            return;
        }
        this.mModifiers.add(launchParamsModifier);
    }

    static class LaunchParams {
        final android.graphics.Rect mBounds = new android.graphics.Rect();

        @android.annotation.Nullable
        com.android.server.wm.TaskDisplayArea mPreferredTaskDisplayArea;
        int mWindowingMode;

        LaunchParams() {
        }

        void reset() {
            this.mBounds.setEmpty();
            this.mPreferredTaskDisplayArea = null;
            this.mWindowingMode = 0;
        }

        void set(com.android.server.wm.LaunchParamsController.LaunchParams launchParams) {
            this.mBounds.set(launchParams.mBounds);
            this.mPreferredTaskDisplayArea = launchParams.mPreferredTaskDisplayArea;
            this.mWindowingMode = launchParams.mWindowingMode;
        }

        boolean isEmpty() {
            return this.mBounds.isEmpty() && this.mPreferredTaskDisplayArea == null && this.mWindowingMode == 0;
        }

        boolean hasWindowingMode() {
            return this.mWindowingMode != 0;
        }

        boolean hasPreferredTaskDisplayArea() {
            return this.mPreferredTaskDisplayArea != null;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.server.wm.LaunchParamsController.LaunchParams launchParams = (com.android.server.wm.LaunchParamsController.LaunchParams) obj;
            if (this.mPreferredTaskDisplayArea != launchParams.mPreferredTaskDisplayArea || this.mWindowingMode != launchParams.mWindowingMode) {
                return false;
            }
            if (this.mBounds != null) {
                return this.mBounds.equals(launchParams.mBounds);
            }
            if (launchParams.mBounds == null) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ((((this.mBounds != null ? this.mBounds.hashCode() : 0) * 31) + (this.mPreferredTaskDisplayArea != null ? this.mPreferredTaskDisplayArea.hashCode() : 0)) * 31) + this.mWindowingMode;
        }
    }
}
