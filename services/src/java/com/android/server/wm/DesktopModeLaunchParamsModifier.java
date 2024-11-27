package com.android.server.wm;

/* loaded from: classes3.dex */
public class DesktopModeLaunchParamsModifier implements com.android.server.wm.LaunchParamsController.LaunchParamsModifier {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private java.lang.StringBuilder mLogBuilder;
    private static final boolean DESKTOP_MODE_PROTO2_SUPPORTED = android.os.SystemProperties.getBoolean("persist.wm.debug.desktop_mode_2", false);
    public static final float DESKTOP_MODE_INITIAL_BOUNDS_SCALE = android.os.SystemProperties.getInt("persist.wm.debug.desktop_mode_initial_bounds_scale", 75) / 100.0f;

    @Override // com.android.server.wm.LaunchParamsController.LaunchParamsModifier
    public int onCalculate(@android.annotation.Nullable com.android.server.wm.Task task, @android.annotation.Nullable android.content.pm.ActivityInfo.WindowLayout windowLayout, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord2, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.ActivityStarter.Request request, int i, com.android.server.wm.LaunchParamsController.LaunchParams launchParams, com.android.server.wm.LaunchParamsController.LaunchParams launchParams2) {
        initLogBuilder(task, activityRecord);
        int calculate = calculate(task, windowLayout, activityRecord, activityRecord2, activityOptions, request, i, launchParams, launchParams2);
        outputLog();
        return calculate;
    }

    private int calculate(@android.annotation.Nullable com.android.server.wm.Task task, @android.annotation.Nullable android.content.pm.ActivityInfo.WindowLayout windowLayout, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord2, @android.annotation.Nullable android.app.ActivityOptions activityOptions, @android.annotation.Nullable com.android.server.wm.ActivityStarter.Request request, int i, com.android.server.wm.LaunchParamsController.LaunchParams launchParams, com.android.server.wm.LaunchParamsController.LaunchParams launchParams2) {
        if (!isDesktopModeEnabled()) {
            appendLog("desktop mode is not enabled, continuing", new java.lang.Object[0]);
            return 2;
        }
        if (task == null) {
            appendLog("task null, skipping", new java.lang.Object[0]);
            return 0;
        }
        if (!task.isActivityTypeStandardOrUndefined()) {
            appendLog("not standard or undefined activity type, skipping", new java.lang.Object[0]);
            return 0;
        }
        if (i < 1) {
            appendLog("not in windowing mode or bounds phase, skipping", new java.lang.Object[0]);
            return 0;
        }
        launchParams2.set(launchParams);
        if (isDesktopModeEnabled() && activityRecord2 != null && activityRecord2.getTask() != null) {
            launchParams2.mWindowingMode = activityRecord2.getTask().getWindowingMode();
            appendLog("inherit-from-source=" + launchParams2.mWindowingMode, new java.lang.Object[0]);
        }
        if (i == 1) {
            return 1;
        }
        if (!launchParams.mBounds.isEmpty()) {
            appendLog("currentParams has bounds set, not overriding", new java.lang.Object[0]);
            return 0;
        }
        calculateAndCentreInitialBounds(task, launchParams2);
        appendLog("setting desktop mode task bounds to %s", launchParams2.mBounds);
        return 1;
    }

    private void calculateAndCentreInitialBounds(com.android.server.wm.Task task, com.android.server.wm.LaunchParamsController.LaunchParams launchParams) {
        android.graphics.Rect bounds = task.getDisplayArea().getBounds();
        int width = (int) (bounds.width() * DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        int height = (int) (bounds.height() * DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        launchParams.mBounds.right = width;
        launchParams.mBounds.bottom = height;
        launchParams.mBounds.offset(bounds.centerX() - launchParams.mBounds.centerX(), bounds.centerY() - launchParams.mBounds.centerY());
    }

    private void initLogBuilder(com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord) {
    }

    private void appendLog(java.lang.String str, java.lang.Object... objArr) {
    }

    private void outputLog() {
    }

    static boolean isDesktopModeEnabled() {
        return com.android.window.flags.Flags.enableDesktopWindowingMode();
    }
}
