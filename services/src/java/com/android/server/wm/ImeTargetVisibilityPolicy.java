package com.android.server.wm;

/* loaded from: classes3.dex */
public abstract class ImeTargetVisibilityPolicy {
    public abstract boolean removeImeScreenshot(int i);

    public abstract boolean showImeScreenshot(@android.annotation.NonNull android.os.IBinder iBinder, int i);

    public static boolean canComputeImeParent(@android.annotation.Nullable com.android.server.wm.WindowState windowState, @android.annotation.Nullable com.android.server.wm.InputTarget inputTarget) {
        boolean z = false;
        if (windowState == null) {
            return false;
        }
        if (shouldComputeImeParentForEmbeddedActivity(windowState, inputTarget)) {
            return true;
        }
        boolean z2 = android.view.WindowManager.LayoutParams.mayUseInputMethod(windowState.mAttrs.flags) || windowState.mAttrs.type == 3;
        boolean z3 = inputTarget == null || windowState.mActivityRecord != inputTarget.getActivityRecord();
        if (z2 && z3) {
            z = true;
        }
        return !z;
    }

    private static boolean shouldComputeImeParentForEmbeddedActivity(@android.annotation.Nullable com.android.server.wm.WindowState windowState, @android.annotation.Nullable com.android.server.wm.InputTarget inputTarget) {
        com.android.server.wm.WindowState windowState2;
        if (inputTarget == null || windowState == null || (windowState2 = inputTarget.getWindowState()) == null || !windowState.isAttached() || !windowState2.isAttached()) {
            return false;
        }
        com.android.server.wm.ActivityRecord activityRecord = inputTarget.getActivityRecord();
        com.android.server.wm.ActivityRecord activityRecord2 = windowState.getActivityRecord();
        return activityRecord != null && activityRecord2 != null && activityRecord != activityRecord2 && activityRecord.getTask() == activityRecord2.getTask() && activityRecord.isEmbedded() && activityRecord2.isEmbedded() && windowState.compareTo((com.android.server.wm.WindowContainer) windowState2) > 0;
    }
}
