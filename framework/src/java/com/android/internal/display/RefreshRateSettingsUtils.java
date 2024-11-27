package com.android.internal.display;

/* loaded from: classes4.dex */
public class RefreshRateSettingsUtils {
    public static final float DEFAULT_REFRESH_RATE = 60.0f;
    private static final java.lang.String TAG = "RefreshRateSettingsUtils";

    public static float findHighestRefreshRateForDefaultDisplay(android.content.Context context) {
        android.view.Display display = ((android.hardware.display.DisplayManager) context.getSystemService(android.hardware.display.DisplayManager.class)).getDisplay(0);
        float f = 60.0f;
        if (display == null) {
            android.util.Log.w(TAG, "No valid default display device");
            return 60.0f;
        }
        for (android.view.Display.Mode mode : display.getSupportedModes()) {
            if (mode.getRefreshRate() > f) {
                f = mode.getRefreshRate();
            }
        }
        return f;
    }

    public static float findHighestRefreshRateAmongAllDisplays(android.content.Context context) {
        android.view.Display[] displays = ((android.hardware.display.DisplayManager) context.getSystemService(android.hardware.display.DisplayManager.class)).getDisplays(android.hardware.display.DisplayManager.DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED);
        float f = 60.0f;
        if (displays.length == 0) {
            android.util.Log.w(TAG, "No valid display devices");
            return 60.0f;
        }
        for (android.view.Display display : displays) {
            for (android.view.Display.Mode mode : display.getSupportedModes()) {
                if (mode.getRefreshRate() > f) {
                    f = mode.getRefreshRate();
                }
            }
        }
        return f;
    }
}
