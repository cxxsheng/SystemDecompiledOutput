package com.android.server.display.brightness;

/* loaded from: classes.dex */
public final class BrightnessUtils {
    public static boolean isValidBrightnessValue(float f) {
        return !java.lang.Float.isNaN(f) && f >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f <= 1.0f;
    }

    public static float clampAbsoluteBrightness(float f) {
        return android.util.MathUtils.constrain(f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f);
    }

    public static float clampBrightnessAdjustment(float f) {
        return android.util.MathUtils.constrain(f, -1.0f, 1.0f);
    }

    public static com.android.server.display.DisplayBrightnessState constructDisplayBrightnessState(int i, float f, float f2, java.lang.String str) {
        return constructDisplayBrightnessState(i, f, f2, str, false);
    }

    public static com.android.server.display.DisplayBrightnessState constructDisplayBrightnessState(int i, float f, float f2, java.lang.String str, boolean z) {
        com.android.server.display.brightness.BrightnessReason brightnessReason = new com.android.server.display.brightness.BrightnessReason();
        brightnessReason.setReason(i);
        return new com.android.server.display.DisplayBrightnessState.Builder().setBrightness(f).setSdrBrightness(f2).setBrightnessReason(brightnessReason).setDisplayBrightnessStrategyName(str).setIsSlowChange(z).build();
    }
}
