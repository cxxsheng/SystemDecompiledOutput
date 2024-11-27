package com.android.server.wm.utils;

/* loaded from: classes3.dex */
public class CoordinateTransforms {
    private CoordinateTransforms() {
    }

    public static void transformPhysicalToLogicalCoordinates(int i, int i2, int i3, android.graphics.Matrix matrix) {
        switch (i) {
            case 0:
                matrix.reset();
                return;
            case 1:
                matrix.setRotate(270.0f);
                matrix.postTranslate(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, i2);
                return;
            case 2:
                matrix.setRotate(180.0f);
                matrix.postTranslate(i2, i3);
                return;
            case 3:
                matrix.setRotate(90.0f);
                matrix.postTranslate(i3, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Unknown rotation: " + i);
        }
    }

    public static void transformLogicalToPhysicalCoordinates(int i, int i2, int i3, android.graphics.Matrix matrix) {
        switch (i) {
            case 0:
                matrix.reset();
                return;
            case 1:
                matrix.setRotate(90.0f);
                matrix.preTranslate(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, -i2);
                return;
            case 2:
                matrix.setRotate(180.0f);
                matrix.preTranslate(-i2, -i3);
                return;
            case 3:
                matrix.setRotate(270.0f);
                matrix.preTranslate(-i3, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Unknown rotation: " + i);
        }
    }

    public static void transformToRotation(int i, int i2, android.view.DisplayInfo displayInfo, android.graphics.Matrix matrix) {
        boolean z = true;
        if (displayInfo.rotation != 1 && displayInfo.rotation != 3) {
            z = false;
        }
        int i3 = z ? displayInfo.logicalWidth : displayInfo.logicalHeight;
        int i4 = z ? displayInfo.logicalHeight : displayInfo.logicalWidth;
        android.graphics.Matrix matrix2 = new android.graphics.Matrix();
        transformLogicalToPhysicalCoordinates(i, i4, i3, matrix);
        transformPhysicalToLogicalCoordinates(i2, i4, i3, matrix2);
        matrix.postConcat(matrix2);
    }

    public static void transformToRotation(int i, int i2, int i3, int i4, android.graphics.Matrix matrix) {
        boolean z = true;
        if (i2 != 1 && i2 != 3) {
            z = false;
        }
        int i5 = z ? i3 : i4;
        if (z) {
            i3 = i4;
        }
        android.graphics.Matrix matrix2 = new android.graphics.Matrix();
        transformLogicalToPhysicalCoordinates(i, i3, i5, matrix);
        transformPhysicalToLogicalCoordinates(i2, i3, i5, matrix2);
        matrix.postConcat(matrix2);
    }

    public static void computeRotationMatrix(int i, int i2, int i3, android.graphics.Matrix matrix) {
        switch (i) {
            case 0:
                matrix.reset();
                break;
            case 1:
                matrix.setRotate(90.0f);
                matrix.postTranslate(i3, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                break;
            case 2:
                matrix.setRotate(180.0f);
                matrix.postTranslate(i2, i3);
                break;
            case 3:
                matrix.setRotate(270.0f);
                matrix.postTranslate(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, i2);
                break;
        }
    }
}
