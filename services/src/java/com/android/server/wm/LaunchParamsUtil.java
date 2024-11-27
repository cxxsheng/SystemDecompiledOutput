package com.android.server.wm;

/* loaded from: classes3.dex */
class LaunchParamsUtil {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_LANDSCAPE_FREEFORM_HEIGHT_DP = 600;
    private static final int DEFAULT_LANDSCAPE_FREEFORM_WIDTH_DP = 1064;
    static final int DEFAULT_PORTRAIT_FREEFORM_HEIGHT_DP = 732;
    static final int DEFAULT_PORTRAIT_FREEFORM_WIDTH_DP = 412;
    private static final int DISPLAY_EDGE_OFFSET_DP = 27;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private static final android.graphics.Rect TMP_STABLE_BOUNDS = new android.graphics.Rect();

    private LaunchParamsUtil() {
    }

    static void centerBounds(@android.annotation.NonNull com.android.server.wm.TaskDisplayArea taskDisplayArea, int i, int i2, @android.annotation.NonNull android.graphics.Rect rect) {
        if (rect.isEmpty()) {
            taskDisplayArea.getStableRect(rect);
        }
        int centerX = rect.centerX() - (i / 2);
        int centerY = rect.centerY() - (i2 / 2);
        rect.set(centerX, centerY, i + centerX, i2 + centerY);
    }

    static android.util.Size getDefaultFreeformSize(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull com.android.server.wm.TaskDisplayArea taskDisplayArea, @android.annotation.NonNull android.content.pm.ActivityInfo.WindowLayout windowLayout, int i, @android.annotation.NonNull android.graphics.Rect rect) {
        float f = taskDisplayArea.getConfiguration().densityDpi / 160.0f;
        int i2 = (int) (((i == 0 ? DEFAULT_LANDSCAPE_FREEFORM_WIDTH_DP : 412) * f) + 0.5f);
        int i3 = (int) (((i == 0 ? 600 : DEFAULT_PORTRAIT_FREEFORM_HEIGHT_DP) * f) + 0.5f);
        int i4 = windowLayout == null ? -1 : windowLayout.minWidth;
        int i5 = windowLayout != null ? windowLayout.minHeight : -1;
        int min = java.lang.Math.min(rect.width(), rect.height());
        int max = (min * min) / java.lang.Math.max(rect.width(), rect.height());
        int i6 = i == 0 ? min : max;
        if (i == 0) {
            min = max;
        }
        int min2 = java.lang.Math.min(i6, java.lang.Math.max(i2, i4));
        int min3 = java.lang.Math.min(min, java.lang.Math.max(i3, i5));
        float max2 = java.lang.Math.max(min2, min3) / java.lang.Math.min(min2, min3);
        float minAspectRatio = activityRecord.getMinAspectRatio();
        float maxAspectRatio = activityRecord.info.getMaxAspectRatio();
        if (minAspectRatio < 1.0f || max2 >= minAspectRatio) {
            if (maxAspectRatio >= 1.0f && max2 > maxAspectRatio) {
                if (i == 0) {
                    min3 = (int) ((min2 / maxAspectRatio) + 0.5f);
                } else {
                    min2 = (int) ((min3 / maxAspectRatio) + 0.5f);
                }
            }
        } else if (i == 0) {
            min3 = (int) ((min2 / minAspectRatio) + 0.5f);
        } else {
            min2 = (int) ((min3 / minAspectRatio) + 0.5f);
        }
        return new android.util.Size(min2, min3);
    }

    static void adjustBoundsToFitInDisplayArea(@android.annotation.NonNull com.android.server.wm.TaskDisplayArea taskDisplayArea, int i, @android.annotation.NonNull android.content.pm.ActivityInfo.WindowLayout windowLayout, @android.annotation.NonNull android.graphics.Rect rect) {
        int i2;
        int i3;
        android.graphics.Rect rect2 = TMP_STABLE_BOUNDS;
        taskDisplayArea.getStableRect(rect2);
        int i4 = (int) (((taskDisplayArea.getConfiguration().densityDpi / 160.0f) * 27.0f) + 0.5f);
        rect2.inset(i4, i4);
        if (rect2.width() < rect.width() || rect2.height() < rect.height()) {
            float min = java.lang.Math.min(rect2.width() / rect.width(), rect2.height() / rect.height());
            int i5 = windowLayout == null ? -1 : windowLayout.minWidth;
            int i6 = windowLayout != null ? windowLayout.minHeight : -1;
            int max = java.lang.Math.max(i5, (int) (rect.width() * min));
            int max2 = java.lang.Math.max(i6, (int) (rect.height() * min));
            if (rect2.width() < max || rect2.height() < max2) {
                if (i == 1) {
                    i2 = rect2.right - max;
                } else {
                    i2 = rect2.left;
                }
                rect.set(i2, rect2.top, max + i2, rect2.top + max2);
                return;
            }
            rect.set(rect.left, rect.top, rect.left + max, rect.top + max2);
        }
        int i7 = 0;
        if (rect.right > rect2.right) {
            i3 = rect2.right - rect.right;
        } else if (rect.left < rect2.left) {
            i3 = rect2.left - rect.left;
        } else {
            i3 = 0;
        }
        if (rect.top < rect2.top) {
            i7 = rect2.top - rect.top;
        } else if (rect.bottom > rect2.bottom) {
            i7 = rect2.bottom - rect.bottom;
        }
        rect.offset(i3, i7);
    }
}
