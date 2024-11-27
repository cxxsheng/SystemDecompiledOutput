package com.android.server.wm.utils;

/* loaded from: classes3.dex */
public class InsetUtils {
    private InsetUtils() {
    }

    public static void rotateInsets(android.graphics.Rect rect, int i) {
        switch (i) {
            case 0:
                return;
            case 1:
                rect.set(rect.top, rect.right, rect.bottom, rect.left);
                return;
            case 2:
                rect.set(rect.right, rect.bottom, rect.left, rect.top);
                return;
            case 3:
                rect.set(rect.bottom, rect.left, rect.top, rect.right);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Unknown rotation: " + i);
        }
    }

    public static void addInsets(android.graphics.Rect rect, android.graphics.Rect rect2) {
        rect.left += rect2.left;
        rect.top += rect2.top;
        rect.right += rect2.right;
        rect.bottom += rect2.bottom;
    }

    public static void insetsBetweenFrames(@android.annotation.NonNull android.graphics.Rect rect, @android.annotation.Nullable android.graphics.Rect rect2, @android.annotation.NonNull android.graphics.Rect rect3) {
        if (rect2 == null) {
            rect3.setEmpty();
            return;
        }
        int width = rect.width();
        int height = rect.height();
        rect3.set(java.lang.Math.min(width, java.lang.Math.max(0, rect2.left - rect.left)), java.lang.Math.min(height, java.lang.Math.max(0, rect2.top - rect.top)), java.lang.Math.min(width, java.lang.Math.max(0, rect.right - rect2.right)), java.lang.Math.min(height, java.lang.Math.max(0, rect.bottom - rect2.bottom)));
    }
}
