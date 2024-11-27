package com.android.server.accessibility.gestures;

/* loaded from: classes.dex */
public final class GestureUtils {
    public static int MM_PER_CM = 10;
    public static float CM_PER_INCH = 2.54f;

    private GestureUtils() {
    }

    public static boolean isMultiTap(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i, int i2) {
        if (motionEvent == null || motionEvent2 == null) {
            return false;
        }
        return eventsWithinTimeAndDistanceSlop(motionEvent, motionEvent2, i, i2);
    }

    private static boolean eventsWithinTimeAndDistanceSlop(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i, int i2) {
        return !isTimedOut(motionEvent, motionEvent2, i) && distance(motionEvent, motionEvent2) < ((double) i2);
    }

    public static double distance(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2) {
        return android.util.MathUtils.dist(motionEvent.getX(), motionEvent.getY(), motionEvent2.getX(), motionEvent2.getY());
    }

    public static double distanceClosestPointerToPoint(android.graphics.PointF pointF, android.view.MotionEvent motionEvent) {
        float f = Float.MAX_VALUE;
        for (int i = 0; i < motionEvent.getPointerCount(); i++) {
            float dist = android.util.MathUtils.dist(pointF.x, pointF.y, motionEvent.getX(i), motionEvent.getY(i));
            if (f > dist) {
                f = dist;
            }
        }
        return f;
    }

    public static boolean isTimedOut(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, int i) {
        return motionEvent2.getEventTime() - motionEvent.getEventTime() >= ((long) i);
    }

    public static boolean isDraggingGesture(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        float f10 = f5 - f;
        float f11 = f6 - f2;
        if (f10 == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f11 == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return true;
        }
        float hypot = (float) java.lang.Math.hypot(f10, f11);
        if (hypot > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            f10 /= hypot;
        }
        if (hypot > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            f11 /= hypot;
        }
        float f12 = f7 - f3;
        float f13 = f8 - f4;
        if (f12 == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f13 == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return true;
        }
        float hypot2 = (float) java.lang.Math.hypot(f12, f13);
        if (hypot2 > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            f12 /= hypot2;
        }
        if (hypot2 > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            f13 /= hypot2;
        }
        return (f10 * f12) + (f11 * f13) >= f9;
    }

    public static int getActionIndex(android.view.MotionEvent motionEvent) {
        return (motionEvent.getAction() & com.android.server.job.JobPackageTracker.EVENT_STOP_REASON_MASK) >> 8;
    }
}
