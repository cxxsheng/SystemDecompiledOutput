package com.android.server.wm;

/* loaded from: classes3.dex */
public class EventLogTags {
    public static final int IMF_REMOVE_IME_SCREENSHOT = 32005;
    public static final int IMF_SHOW_IME_SCREENSHOT = 32004;
    public static final int IMF_UPDATE_IME_PARENT = 32003;
    public static final int WM_ACTIVITY_LAUNCH_TIME = 30009;
    public static final int WM_ADD_TO_STOPPING = 30066;
    public static final int WM_BACK_NAVI_CANCELED = 31100;
    public static final int WM_BOOT_ANIMATION_DONE = 31007;
    public static final int WM_CREATE_ACTIVITY = 30005;
    public static final int WM_CREATE_TASK = 30004;
    public static final int WM_DESTROY_ACTIVITY = 30018;
    public static final int WM_ENTER_PIP = 38000;
    public static final int WM_FAILED_TO_PAUSE = 30012;
    public static final int WM_FINISH_ACTIVITY = 30001;
    public static final int WM_FOCUSED_ROOT_TASK = 30044;
    public static final int WM_NEW_INTENT = 30003;
    public static final int WM_NO_SURFACE_MEMORY = 31000;
    public static final int WM_PAUSE_ACTIVITY = 30013;
    public static final int WM_RELAUNCH_ACTIVITY = 30020;
    public static final int WM_RELAUNCH_RESUME_ACTIVITY = 30019;
    public static final int WM_RESTART_ACTIVITY = 30006;
    public static final int WM_RESUME_ACTIVITY = 30007;
    public static final int WM_SET_KEYGUARD_OCCLUDED = 31008;
    public static final int WM_SET_KEYGUARD_SHOWN = 30067;
    public static final int WM_SET_REQUESTED_ORIENTATION = 31006;
    public static final int WM_SET_RESUMED_ACTIVITY = 30043;
    public static final int WM_STOP_ACTIVITY = 30048;
    public static final int WM_TASK_CREATED = 31001;
    public static final int WM_TASK_MOVED = 31002;
    public static final int WM_TASK_REMOVED = 31003;
    public static final int WM_TASK_TO_FRONT = 30002;
    public static final int WM_WALLPAPER_SURFACE = 33001;

    private EventLogTags() {
    }

    public static void writeWmFinishActivity(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(WM_FINISH_ACTIVITY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str, str2);
    }

    public static void writeWmTaskToFront(int i, int i2, int i3) {
        android.util.EventLog.writeEvent(WM_TASK_TO_FRONT, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3));
    }

    public static void writeWmNewIntent(int i, int i2, int i3, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i4) {
        android.util.EventLog.writeEvent(WM_NEW_INTENT, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str, str2, str3, str4, java.lang.Integer.valueOf(i4));
    }

    public static void writeWmCreateTask(int i, int i2, int i3, int i4) {
        android.util.EventLog.writeEvent(WM_CREATE_TASK, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4));
    }

    public static void writeWmCreateActivity(int i, int i2, int i3, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i4) {
        android.util.EventLog.writeEvent(WM_CREATE_ACTIVITY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str, str2, str3, str4, java.lang.Integer.valueOf(i4));
    }

    public static void writeWmRestartActivity(int i, int i2, int i3, java.lang.String str) {
        android.util.EventLog.writeEvent(WM_RESTART_ACTIVITY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str);
    }

    public static void writeWmResumeActivity(int i, int i2, int i3, java.lang.String str) {
        android.util.EventLog.writeEvent(WM_RESUME_ACTIVITY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str);
    }

    public static void writeWmActivityLaunchTime(int i, int i2, java.lang.String str, long j) {
        android.util.EventLog.writeEvent(WM_ACTIVITY_LAUNCH_TIME, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, java.lang.Long.valueOf(j));
    }

    public static void writeWmFailedToPause(int i, int i2, java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(WM_FAILED_TO_PAUSE, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, str2);
    }

    public static void writeWmPauseActivity(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        android.util.EventLog.writeEvent(WM_PAUSE_ACTIVITY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, str2, str3);
    }

    public static void writeWmDestroyActivity(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(WM_DESTROY_ACTIVITY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str, str2);
    }

    public static void writeWmRelaunchResumeActivity(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(WM_RELAUNCH_RESUME_ACTIVITY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str, str2);
    }

    public static void writeWmRelaunchActivity(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(WM_RELAUNCH_ACTIVITY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str, str2);
    }

    public static void writeWmSetResumedActivity(int i, java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(WM_SET_RESUMED_ACTIVITY, java.lang.Integer.valueOf(i), str, str2);
    }

    public static void writeWmFocusedRootTask(int i, int i2, int i3, int i4, java.lang.String str) {
        android.util.EventLog.writeEvent(WM_FOCUSED_ROOT_TASK, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), str);
    }

    public static void writeWmStopActivity(int i, int i2, java.lang.String str) {
        android.util.EventLog.writeEvent(WM_STOP_ACTIVITY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str);
    }

    public static void writeWmAddToStopping(int i, int i2, java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(WM_ADD_TO_STOPPING, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, str2);
    }

    public static void writeWmSetKeyguardShown(int i, int i2, int i3, int i4, int i5, java.lang.String str) {
        android.util.EventLog.writeEvent(WM_SET_KEYGUARD_SHOWN, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5), str);
    }

    public static void writeWmNoSurfaceMemory(java.lang.String str, int i, java.lang.String str2) {
        android.util.EventLog.writeEvent(WM_NO_SURFACE_MEMORY, str, java.lang.Integer.valueOf(i), str2);
    }

    public static void writeWmTaskCreated(int i) {
        android.util.EventLog.writeEvent(WM_TASK_CREATED, i);
    }

    public static void writeWmTaskMoved(int i, int i2, int i3, int i4, int i5) {
        android.util.EventLog.writeEvent(WM_TASK_MOVED, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5));
    }

    public static void writeWmTaskRemoved(int i, int i2, int i3, java.lang.String str) {
        android.util.EventLog.writeEvent(WM_TASK_REMOVED, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str);
    }

    public static void writeWmSetRequestedOrientation(int i, java.lang.String str) {
        android.util.EventLog.writeEvent(WM_SET_REQUESTED_ORIENTATION, java.lang.Integer.valueOf(i), str);
    }

    public static void writeWmBootAnimationDone(long j) {
        android.util.EventLog.writeEvent(WM_BOOT_ANIMATION_DONE, j);
    }

    public static void writeWmSetKeyguardOccluded(int i, int i2, int i3, java.lang.String str) {
        android.util.EventLog.writeEvent(WM_SET_KEYGUARD_OCCLUDED, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str);
    }

    public static void writeWmBackNaviCanceled(java.lang.String str) {
        android.util.EventLog.writeEvent(WM_BACK_NAVI_CANCELED, str);
    }

    public static void writeImfUpdateImeParent(java.lang.String str) {
        android.util.EventLog.writeEvent(IMF_UPDATE_IME_PARENT, str);
    }

    public static void writeImfShowImeScreenshot(java.lang.String str, int i, java.lang.String str2) {
        android.util.EventLog.writeEvent(IMF_SHOW_IME_SCREENSHOT, str, java.lang.Integer.valueOf(i), str2);
    }

    public static void writeImfRemoveImeScreenshot(java.lang.String str) {
        android.util.EventLog.writeEvent(IMF_REMOVE_IME_SCREENSHOT, str);
    }

    public static void writeWmWallpaperSurface(int i, int i2, java.lang.String str) {
        android.util.EventLog.writeEvent(WM_WALLPAPER_SURFACE, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str);
    }

    public static void writeWmEnterPip(int i, int i2, java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(WM_ENTER_PIP, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), str, str2);
    }
}
